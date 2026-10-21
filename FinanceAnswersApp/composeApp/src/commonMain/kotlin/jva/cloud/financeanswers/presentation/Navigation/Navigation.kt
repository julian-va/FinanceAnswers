package jva.cloud.financeanswers.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jva.cloud.financeanswers.presentation.views.FinanceAnswers
import jva.cloud.financeanswers.presentation.views.FinanceAnswersView

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = FinanceAnswers) {
        composable<FinanceAnswers> {
            FinanceAnswersView()
        }
    }
}
