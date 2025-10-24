package jva.cloud.financeanswers.domain.usecase.impl

import jva.cloud.financeanswers.data.remote.dto.LlmRequestAnswersDto
import jva.cloud.financeanswers.domain.mapper.toDto
import jva.cloud.financeanswers.domain.model.LlmRequestAnswers
import jva.cloud.financeanswers.domain.repository.LlmRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class RetrieverAnswersFromLlmStreamsUseCaseImplTest {

    @Test
    fun `getAnswersFromLlmStreams returns success flow when repository returns success`() =
        runTest {
            val sampleDomain = LlmRequestAnswers(systemMessage = "system", userMessage = "user")
            val sampleFlow: Flow<String> = flowOf("one", "two")

            var receivedDto: LlmRequestAnswersDto? = null
            val fakeRepo = object : LlmRemoteRepository {
                override suspend fun getAnswersFromLlmStreams(question: LlmRequestAnswersDto): Result<Flow<String>> {
                    receivedDto = question
                    return Result.success(sampleFlow)
                }
            }

            val useCase = RetrieverAnswersFromLlmStreamsUseCaseImpl(fakeRepo)

            val result = useCase.getAnswersFromLlmStreams(sampleDomain)

            assertTrue(result.isSuccess)
            val flow = result.getOrNull()
            assertNotNull(flow)
            val items = flow.toList()
            assertEquals(listOf("one", "two"), items)

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
            override suspend fun getAnswersFromLlmStreams(question: LlmRequestAnswersDto): Result<Flow<String>> {
                return Result.failure(ex)
            }
        }

        val useCase = RetrieverAnswersFromLlmStreamsUseCaseImpl(fakeRepo)

        val result = useCase.getAnswersFromLlmStreams(sampleDomain)

        assertTrue(result.isFailure)
        assertSame(ex, result.exceptionOrNull())
    }
}
