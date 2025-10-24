package jva.cloud.financeanswers.domain.usecase.impl

import jva.cloud.financeanswers.domain.mapper.toDto
import jva.cloud.financeanswers.domain.model.LlmRequestAnswers
import jva.cloud.financeanswers.domain.repository.LlmRemoteRepository
import jva.cloud.financeanswers.domain.usecase.RetrieverAnswersFromLlmStreamsUseCase
import kotlinx.coroutines.flow.Flow

class RetrieverAnswersFromLlmStreamsUseCaseImpl(private val llmRepository: LlmRemoteRepository) :
    RetrieverAnswersFromLlmStreamsUseCase {
    override suspend fun getAnswersFromLlmStreams(question: LlmRequestAnswers): Result<Flow<String>> {
        return llmRepository.getAnswersFromLlmStreams(question = question.toDto())
    }
}