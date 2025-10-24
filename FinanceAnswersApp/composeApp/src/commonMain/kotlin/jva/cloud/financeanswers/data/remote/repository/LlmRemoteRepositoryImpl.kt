package jva.cloud.financeanswers.data.remote.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.isSuccess
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import jva.cloud.financeanswers.data.remote.dto.LlmRequestAnswersDto
import jva.cloud.financeanswers.domain.repository.LlmRemoteRepository
import jva.cloud.financeanswers.utils.ConstantApp.ENDPOINT_LLM_API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LlmRemoteRepositoryImpl(private val client: HttpClient) : LlmRemoteRepository {

    companion object {
        private const val ERROR_MESSAGE = "Error getting response from LLM"
        private const val SSE_DATA_PREFIX = "data:"
    }

    override fun getAnswersFromLlmStreams(question: LlmRequestAnswersDto): Flow<Result<String>> {
        return flow {
            val response = client.post(urlString = ENDPOINT_LLM_API) {
                setBody(question)
            }

            if (response.status.isSuccess()) {
                val channel: ByteReadChannel = response.bodyAsChannel()
                while (!channel.isClosedForRead) {
                    val line = channel.readUTF8Line() ?: break
                    val clean = line.removePrefix(SSE_DATA_PREFIX).trim()
                    if (clean.isNotBlank()) {
                        emit(Result.success(clean))
                    }
                }
            } else {
                emit(Result.failure(Exception("$ERROR_MESSAGE: ${response.status.value}")))
            }
        }.catch { e -> emit(Result.failure(e)) }
    }
}