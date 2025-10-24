package jva.cloud.financeanswers.data.remote.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import jva.cloud.financeanswers.data.remote.dto.LlmRequestAnswersDto
import jva.cloud.financeanswers.domain.repository.LlmRemoteRepository
import jva.cloud.financeanswers.utils.ConstantApp.ENDPOINT_LLM_API
import kotlinx.coroutines.flow.Flow

class LlmRemoteRepositoryImpl(private val client: HttpClient) : LlmRemoteRepository {

    companion object {
        private const val ERROR_MESSAGE = "Error getting response from LLM"
    }

    override suspend fun getAnswersFromLlmStreams(question: LlmRequestAnswersDto): Result<Flow<String>> {
        return try {
            val response = client.post(urlString = ENDPOINT_LLM_API) {
                setBody(question)
            }
            if (response.status.isSuccess()) {
                val responseBody: Flow<String> = response.body()
                Result.success(responseBody)
            } else {
                return Result.failure(Exception("$ERROR_MESSAGE: ${response.status.value}"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }

    }
}