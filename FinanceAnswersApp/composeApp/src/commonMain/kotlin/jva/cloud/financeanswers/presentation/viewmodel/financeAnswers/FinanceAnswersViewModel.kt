package jva.cloud.financeanswers.presentation.viewmodel.financeAnswers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jva.cloud.financeanswers.domain.model.LlmRequestAnswers
import jva.cloud.financeanswers.domain.usecase.RetrieverAnswersFromLlmStreamsUseCase
import jva.cloud.financeanswers.utils.ConstantApp.SYSTEM_MESSAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FinanceAnswersViewModel(private val retrieverAnswersFromLlmStreamsUseCase: RetrieverAnswersFromLlmStreamsUseCase) :
    ViewModel() {
    var state by mutableStateOf(FinanceAnswersViewModelState())
        private set

    fun sentAskedQuestion() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val flowResult: Flow<Result<String>> =
                retrieverAnswersFromLlmStreamsUseCase.getAnswersFromLlmStreams(
                    question = LlmRequestAnswers(
                        systemMessage = SYSTEM_MESSAGE,
                        userMessage = state.question
                    )
                )
            flowResult.collect { result ->
                result.onSuccess { answer ->
                    state = state.copy(
                        answer = state.answer + answer,
                        showResponse = true,
                        isLoading = false,
                        errorMessage = null
                    )
                }.onFailure { exception ->
                    state = state.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
            }
        }
    }
}
