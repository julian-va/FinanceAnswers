@file:OptIn(ExperimentalMaterial3Api::class)

package jva.cloud.financeanswers.presentation.views.financeAnswers

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import jva.cloud.financeanswers.presentation.viewmodel.financeAnswers.FinanceAnswersViewModel
import jva.cloud.financeanswers.presentation.viewmodel.financeAnswers.FinanceAnswersViewModelState
import jva.cloud.financeanswers.presentation.views.financeAnswers.components.FinanceAnswersDrawerContent
import jva.cloud.financeanswers.presentation.views.financeAnswers.components.FinanceAnswersScaffold
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object FinanceAnswers

@Composable
fun FinanceAnswersView(viewModel: FinanceAnswersViewModel = koinViewModel()) {
    val state = viewModel.state
    FinanceAnswersViewContent(state = state)
}

@Composable
private fun FinanceAnswersViewContent(state: FinanceAnswersViewModelState) {
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showResponse by remember { mutableStateOf(true) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            FinanceAnswersDrawerContent()
        }) {
        FinanceAnswersScaffold(
            scope = scope,
            question = state.question,
            scrollBehavior = scrollBehavior,
            drawerState = drawerState,
            showResponse = showResponse,
            onTextChange = { },
            onSend = { },
            answer = state.answer
        )
    }
}

/**@Preview(showBackground = true)
@Composable
fun FinanceAnswersPreview() {
FinanceAnswersViewContent(state = FinanceAnswersViewModelState())
}*/
