package jva.cloud.financeanswers.domain.mapper

import jva.cloud.financeanswers.data.remote.dto.LlmRequestAnswersDto
import jva.cloud.financeanswers.domain.model.LlmRequestAnswers

fun LlmRequestAnswersDto.toDomain(): LlmRequestAnswers = LlmRequestAnswers(
    systemMessage = systemMessage,
    userMessage = userMessage
)

fun LlmRequestAnswers.toDto(): LlmRequestAnswersDto = LlmRequestAnswersDto(
    systemMessage = systemMessage,
    userMessage = userMessage
)
