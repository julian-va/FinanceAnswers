@file:OptIn(ExperimentalMaterial3Api::class)

package jva.cloud.financeanswers.presentation.views.financeAnswers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun FinanceAnswersScaffold(
    scope: CoroutineScope,
    question: String,
    answer: String,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    showResponse: Boolean,
    onTextChange: (String) -> Unit,
    onSend: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Finance Answers") },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Menu Icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(10.dp),
            propagateMinConstraints = false
        ) {


            if (showResponse) {
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .padding(20.dp),
                    text = "Finance Answers View",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium
                )
            } else {
                Column(
                    modifier = Modifier.align(Alignment.TopEnd),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    OutlinedTextField(
                        modifier = Modifier
                            .align(Alignment.End)
                            .size(200.dp, 50.dp),
                        shape = RoundedCornerShape(40.dp),
                        value = question,
                        readOnly = true,
                        onValueChange = {},
                        label = {
                            Text(
                                "Pregunta realizada al moodelo",
                                modifier = Modifier.padding(horizontal = 30.dp)
                            )
                        }
                    )

                    HorizontalDivider(thickness = 2.dp)

                    Text(text = answer)
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(40.dp),
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = "Account Icon",
                    tint = MaterialTheme.colorScheme.primary
                )

                // Usa el ChatInput que está en el mismo paquete de components
                ChatInput(
                    modifier = Modifier.weight(2f),
                    question = question,
                    onTextChange = onTextChange,
                    onSend = onSend
                )
            }
        }
    }
}
