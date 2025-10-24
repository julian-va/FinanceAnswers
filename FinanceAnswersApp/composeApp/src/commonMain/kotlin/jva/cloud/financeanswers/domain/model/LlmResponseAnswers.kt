package jva.cloud.financeanswers.domain.model

data class LlmResponseAnswers(
    val fullResponse: String,
    val durationMs: Long,
    val llmRequestAnswers: LlmRequestAnswers
)
