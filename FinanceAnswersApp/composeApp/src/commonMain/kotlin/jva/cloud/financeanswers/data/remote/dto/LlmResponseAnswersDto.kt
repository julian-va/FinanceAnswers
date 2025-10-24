package jva.cloud.financeanswers.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LlmResponseAnswersDto(
    @SerialName("full_response")
    val fullResponse: String,
    @SerialName("duration_ms")
    val durationMs: Long,
    @SerialName("message_suggestion")
    val llmRequestAnswers: LlmRequestAnswersDto
)