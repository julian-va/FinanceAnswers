package jva.cloud.financeanswers.domain.mapper

import jva.cloud.financeanswers.data.remote.dto.LlmResponseAnswersDto
import jva.cloud.financeanswers.domain.model.LlmResponseAnswers

// Convierte DTO -> Domain
fun LlmResponseAnswersDto.toDomain(): LlmResponseAnswers = LlmResponseAnswers(
    fullResponse = fullResponse,
    durationMs = durationMs,
    llmRequestAnswers = llmRequestAnswers.toDomain()
)

// Convierte Domain -> DTO
fun LlmResponseAnswers.toDto(): LlmResponseAnswersDto = LlmResponseAnswersDto(
    fullResponse = fullResponse,
    durationMs = durationMs,
    llmRequestAnswers = llmRequestAnswers.toDto()
)

