package jva.cloud.financeanswers.domain.usecase.impl

import jva.cloud.financeanswers.data.remote.dto.LlmRequestAnswersDto
import jva.cloud.financeanswers.domain.mapper.toDto
import jva.cloud.financeanswers.domain.model.LlmRequestAnswers
import jva.cloud.financeanswers.domain.repository.LlmRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RetrieverAnswersFromLlmStreamsUseCaseImplTest {

    @Test
    fun `getAnswersFromLlmStreams returns success flow when repository returns success`() =
        runTest {
            val sampleDomain = LlmRequestAnswers(systemMessage = "system", userMessage = "user")

            var receivedDto: LlmRequestAnswersDto? = null
            val sampleFlow: Flow<Result<String>> = flowOf("one", "two").map { Result.success(it) }

            val fakeRepo = object : LlmRemoteRepository {
                override fun getAnswersFromLlmStreams(question: LlmRequestAnswersDto): Flow<Result<String>> {
                    receivedDto = question
                    return sampleFlow
                }
            }

            val useCase = RetrieverAnswersFromLlmStreamsUseCaseImpl(fakeRepo)

            val flow = useCase.getAnswersFromLlmStreams(sampleDomain)
            val results = flow.toList()

            assertTrue(results.size == 2)
            assertTrue(results[0].isSuccess)
            assertTrue(results[1].isSuccess)
            assertEquals("one", results[0].getOrNull())
            assertEquals("two", results[1].getOrNull())

            val expectedDto = sampleDomain.toDto()
            assertNotNull(receivedDto)
            assertEquals(expectedDto.systemMessage, receivedDto.systemMessage)
            assertEquals(expectedDto.userMessage, receivedDto.userMessage)
        }

    @Test
    fun `getAnswersFromLlmStreams returns failure when repository fails`() = runTest {
        val sampleDomain = LlmRequestAnswers(systemMessage = "s", userMessage = "u")
        val ex = RuntimeException("repo-error")

        val fakeRepo = object : LlmRemoteRepository {
            override fun getAnswersFromLlmStreams(question: LlmRequestAnswersDto): Flow<Result<String>> {
                return flowOf(Result.failure(ex))
            }
        }

        val useCase = RetrieverAnswersFromLlmStreamsUseCaseImpl(fakeRepo)

        val results = useCase.getAnswersFromLlmStreams(sampleDomain).toList()

        assertTrue(results.isNotEmpty())
        assertTrue(results[0].isFailure)
        val thrown = results[0].exceptionOrNull()
        assertNotNull(thrown)
        assertTrue(thrown is RuntimeException)
    }
}
