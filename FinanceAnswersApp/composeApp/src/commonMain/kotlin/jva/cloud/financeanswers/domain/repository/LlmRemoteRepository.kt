package jva.cloud.financeanswers.domain.repository

import jva.cloud.financeanswers.data.remote.dto.LlmRequestAnswersDto
import kotlinx.coroutines.flow.Flow

interface LlmRemoteRepository {
    suspend fun getAnswersFromLlmStreams(question: LlmRequestAnswersDto): Result<Flow<String>>
}