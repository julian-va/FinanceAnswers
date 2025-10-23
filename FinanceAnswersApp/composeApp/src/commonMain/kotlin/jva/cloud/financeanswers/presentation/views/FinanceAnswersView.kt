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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.Launch
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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

@Composable
private fun FinanceAnswersScaffold(
    scope: CoroutineScope,
    text: String,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    showResponse: Boolean,
    onTextChange: (String) -> Unit
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
                        value = text,
                        readOnly = true,
                        onValueChange = onTextChange,
                        label = {
                            Text(
                                "Pregunta realizada al moodelo",
                                modifier = Modifier.padding(horizontal = 30.dp)
                            )
                        }
                    )

                    HorizontalDivider(thickness = 2.dp)

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
                OutlinedTextField(
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(40.dp),
                    value = text,
                    onValueChange = onTextChange,
                    label = { Text("Escribe algo") },
                    trailingIcon = {
                        IconButton(
                            onClick = {},
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Account Icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun FinanceAnswersDrawerContent() {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Drawer Title",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = { /* Handle icon button click */ }) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.AutoMirrored.Outlined.Launch,
                        contentDescription = "Question Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "New Answer",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.titleLarge,

                    )
            }
            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Drawer Item 1",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                selected = false,
                onClick = { /* Handle navigation item click */ }
            )


            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Drawer Item 2",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Drawer Item 3",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinanceAnswersPreview() {
    FinanceAnswersViewContent()
}
