package jva.cloud.financeanswers.presentation.viewmodel.financeAnswers

data class FinanceAnswersViewModelState(
    val question: String = "",
    val answer: String = "",
    val showResponse: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
