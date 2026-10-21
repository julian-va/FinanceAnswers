@file:OptIn(ExperimentalMaterial3Api::class)

package jva.cloud.financeanswers.presentation.views

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
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
    var text by remember { mutableStateOf("") }
    var laxy by remember { mutableStateOf(true) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues).padding(10.dp),
            propagateMinConstraints = false
        ) {

            Text(
                modifier = Modifier.align(alignment = Alignment.Center).padding(20.dp),
                text = "Finance Answers View",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            if (laxy) {
                Column(modifier = Modifier.align(Alignment.TopEnd).padding(10.dp)) {

                    Text(text = "Cargando lista...", modifier = Modifier.align(Alignment.End))
                    OutlinedTextField(
                        modifier = Modifier.align(Alignment.End).size(200.dp, 50.dp),
                        shape = RoundedCornerShape(40.dp),
                        value = text,
                        readOnly = true,
                        onValueChange = { text = it },
                        label = { Text("Escribe algo", modifier = Modifier.padding(horizontal = 30.dp)) }
                    )
                    Text(
                        text = "Cargando lista..dddddddddddd" +
                                "dddddddddddddddd" +
                                "dddddddddd" +
                                "dddddddd" +
                                "dddddddd" +
                                "ddddd" +
                                "ddddddd."
                    )
                }

            }

            Row(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically).size(40.dp),
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = "Account Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                OutlinedTextField(
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(40.dp),
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Escribe algo") }
                )
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun FinanceAnswersPreview() {
    FinanceAnswersViewContent()
}

