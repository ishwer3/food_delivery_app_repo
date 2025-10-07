package com.example.deliveryapp.presentation.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class SnackbarType {
    ERROR,
    SUCCESS,
    WARNING,
    INFO
}

@Composable
fun CommonSnackbar(
    modifier: Modifier = Modifier,
    message: String,
    type: SnackbarType = SnackbarType.ERROR,
    duration: Long = 5000L,
    onDismiss: () -> Unit = {}
) {
    val backgroundColor = when (type) {
        SnackbarType.ERROR -> MaterialTheme.colorScheme.error
        SnackbarType.SUCCESS -> Color(0xFF4CAF50)
        SnackbarType.WARNING -> Color(0xFFFF9800)
        SnackbarType.INFO -> MaterialTheme.colorScheme.primary
    }

    val contentColor = when (type) {
        SnackbarType.ERROR -> MaterialTheme.colorScheme.onError
        SnackbarType.SUCCESS -> Color.White
        SnackbarType.WARNING -> Color.White
        SnackbarType.INFO -> MaterialTheme.colorScheme.onPrimary
    }

    /*LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            delay(duration)
            onDismiss()
        }
    }*/

    if (message.isNotEmpty()) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 30.dp),
            color = backgroundColor,
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = message,
                    color = contentColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f) // takes remaining space
                )
                TextButton(onClick = { onDismiss() }) {
                    Text("OK", color = contentColor)
                }
            }
        }
    }
}

@Composable
fun rememberSnackbarState(): SnackbarState {
    return remember { SnackbarState() }
}

class SnackbarState {
    private var _message by mutableStateOf("")
    private var _type by mutableStateOf(SnackbarType.ERROR)

    val message: String get() = _message
    val type: SnackbarType get() = _type

    fun showError(message: String) {
        _message = message
        _type = SnackbarType.ERROR
    }

    fun showSuccess(message: String) {
        _message = message
        _type = SnackbarType.SUCCESS
    }

    fun showWarning(message: String) {
        _message = message
        _type = SnackbarType.WARNING
    }

    fun showInfo(message: String) {
        _message = message
        _type = SnackbarType.INFO
    }

    fun dismiss() {
        _message = ""
    }
}