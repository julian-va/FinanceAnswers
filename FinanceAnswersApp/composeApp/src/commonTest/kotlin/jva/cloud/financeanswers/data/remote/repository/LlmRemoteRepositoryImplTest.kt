package jva.cloud.financeanswers.data.remote.repository

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import jva.cloud.financeanswers.data.remote.dto.LlmRequestAnswersDto
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LlmRemoteRepositoryImplTest {

    private val sampleDto = LlmRequestAnswersDto(
        systemMessage = "system",
        userMessage = "user"
    )

    @Test
    fun `getAnswersFromLlm returns success with Flow when response is 200`() = runTest {
        // Mock an SSE stream: two events and a [DONE] sentinel
        val ssePayload = buildString {
            append("data: first part\n")
            append("data: continued\n\n") // end of first event -> "first part\ncontinued"
            append("data: another event\n\n")
            append("data: [DONE]\n\n")
        }

        val engine = MockEngine { _ ->
            respond(
                content = ssePayload,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.parse("text/event-stream").toString()
                )
            )
        }

        val client = HttpClient(engine) {
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true; isLenient = true })
            }
        }

        val repo = LlmRemoteRepositoryImpl(client)
        val flow = repo.getAnswersFromLlmStreams(sampleDto)
        val results = flow.toList()

        // Expect three emitted events (each `data:` line is emitted separately by la impl actual)
        assertTrue(results.size >= 3, "Expected at least 3 results but got: $results")
        assertEquals("first part", results[0].getOrNull())
        assertEquals("continued", results[1].getOrNull())
        assertEquals("another event", results[2].getOrNull())
    }

    @Test
    fun `getAnswersFromLlm returns failure when response is error`() = runTest {
        val engine = MockEngine { _ ->
            respond(
                content = "{\"error\":\"failure\"}",
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val client = HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val repo = LlmRemoteRepositoryImpl(client)
        val flow = repo.getAnswersFromLlmStreams(sampleDto)
        val results = flow.toList()
        assertTrue(
            results.isNotEmpty(),
            "Expected at least one result for error response but got empty list"
        )
        assertTrue(
            results.first().isFailure,
            "Expected failure result for error response but got: ${results.first()}"
        )
    }

    @Test
    fun `getAnswersFromLlm returns failure when client throws`() = runTest {
        val engine = MockEngine { throw RuntimeException("network") }
        val client = HttpClient(engine) {
            install(ContentNegotiation) { json() }
        }

        val repo = LlmRemoteRepositoryImpl(client)
        val flow = repo.getAnswersFromLlmStreams(sampleDto)
        val results = try {
            flow.toList()
        } catch (e: Exception) {
            listOf(Result.failure<String>(e))
        }

        assertTrue(
            results.isNotEmpty(),
            "Expected at least one result when client throws but got empty list"
        )
        val first = results.first()
        assertTrue(first.isFailure, "Expected first result to be failure but was: $first")
        val ex = first.exceptionOrNull() ?: error("Expected exception")
        assertTrue(ex is RuntimeException, "Expected RuntimeException but got: ${ex::class}")
    }
}
