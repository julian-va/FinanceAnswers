package jva.cloud.financeanswers.presentation.views.FinanceAnswers.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
internal fun ChatInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onSend: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(40.dp),
        value = text,
        onValueChange = onTextChange,
        label = { Text("Escribe algo") },
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send
        ),
        keyboardActions = KeyboardActions(
            onSend = {
                if (text.isNotBlank()) {
                    onSend(text)
                    focusManager.clearFocus()
                }
            },
            onDone = { focusManager.clearFocus() }
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    if (text.isNotBlank()) {
                        onSend(text)
                        focusManager.clearFocus()
                    }
                },
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
