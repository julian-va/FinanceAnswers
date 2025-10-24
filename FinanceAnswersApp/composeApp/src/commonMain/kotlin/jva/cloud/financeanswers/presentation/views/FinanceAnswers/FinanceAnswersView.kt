@file:OptIn(ExperimentalMaterial3Api::class)

package jva.cloud.financeanswers.presentation.views.FinanceAnswers

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
import jva.cloud.financeanswers.presentation.views.FinanceAnswers.components.FinanceAnswersDrawerContent
import jva.cloud.financeanswers.presentation.views.FinanceAnswers.components.FinanceAnswersScaffold
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object FinanceAnswers

@Composable
fun FinanceAnswersView() {
    FinanceAnswersViewContent()
}

@Composable
private fun FinanceAnswersViewContent() {
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var showResponse by remember { mutableStateOf(true) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            FinanceAnswersDrawerContent()
        }) {
        FinanceAnswersScaffold(
            scope = scope,
            text = text,
            scrollBehavior = scrollBehavior,
            drawerState = drawerState,
            showResponse = showResponse,
            onTextChange = { text = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FinanceAnswersPreview() {
    FinanceAnswersViewContent()
}
