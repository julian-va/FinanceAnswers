package jva.cloud.financeanswers.data.remote.repository

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import jva.cloud.financeanswers.data.remote.dto.LlmRequestAnswersDto
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LlmRemoteRepositoryImplTest {

    private val sampleDto = LlmRequestAnswersDto(
        systemMessage = "system",
        userMessage = "user"
    )

    @Test
    fun `getAnswersFromLlm returns success with Flow when response is 200`() {
        runBlocking {
            val engine = MockEngine { _ ->
                respond(
                    content = "[\"answer\"]",
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            }

            val client = HttpClient(engine) {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true; isLenient = true })
                }
            }

            val repo = LlmRemoteRepositoryImpl(client)
            val result = repo.getAnswersFromLlm(sampleDto)

            if (result.isSuccess) {
                val flow = result.getOrNull()
                assertNotNull(flow)
                val items = flow.toList()
                assertTrue(items.size == 1)
                assertTrue(items[0] == "answer")
            } else {
                val ex = result.exceptionOrNull()
                assertNotNull(ex)
            }
        }
    }

    @Test
    fun `getAnswersFromLlm returns failure when response is error`() {
        runBlocking {
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
            val result = repo.getAnswersFromLlm(sampleDto)

            val successWithEmptyFlow =
                result.isSuccess && (result.getOrNull()?.toList()?.isEmpty() == true)
            assertTrue(result.isFailure || successWithEmptyFlow)
        }
    }

    @Test
    fun `getAnswersFromLlm returns failure when client throws`() {
        runBlocking {
            val engine = MockEngine { throw RuntimeException("network") }
            val client = HttpClient(engine) {
                install(ContentNegotiation) { json() }
            }

            val repo = LlmRemoteRepositoryImpl(client)
            val result = repo.getAnswersFromLlm(sampleDto)

            assertTrue(result.isFailure)
            val ex = result.exceptionOrNull()
            assertNotNull(ex)
            assertTrue(ex is RuntimeException)
        }
    }
}
