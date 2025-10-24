package jva.cloud.financeanswers.presentation.views.FinanceAnswers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Launch
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun FinanceAnswersDrawerContent() {
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

