package jva.cloud.financeanswers.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LlmRequestAnswersDto(
    @SerialName("system_message")
    val systemMessage: String,
    @SerialName("user_message")
    val userMessage: String
)