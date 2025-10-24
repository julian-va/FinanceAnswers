package jva.cloud.financeanswers.domain.usecase

import jva.cloud.financeanswers.domain.model.LlmRequestAnswers
import kotlinx.coroutines.flow.Flow

interface RetrieverAnswersFromLlmStreamsUseCase {
    suspend fun getAnswersFromLlmStreams(question: LlmRequestAnswers): Result<Flow<String>>
}