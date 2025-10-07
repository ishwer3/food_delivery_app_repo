package com.example.deliveryapp.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpTextField(
    otpLength: Int = 4,
    onOtpComplete: (String) -> Unit,
    modifier: Modifier = Modifier,
    boxSize: Dp = 55.dp,
    curvedBgColor: Color = Color(0xffc37a4e),
) {
    val otpValues = remember { List(otpLength) { mutableStateOf(TextFieldValue("")) } }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        otpValues.forEachIndexed { index, textState ->
            BasicTextField(
                value = textState.value,
                onValueChange = { newValue ->
                    if (newValue.text.length <= 1) {
                        textState.value = newValue
                        if (newValue.text.isNotEmpty() && index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                        if (otpValues.all { it.value.text.isNotEmpty() }) {
                            onOtpComplete(otpValues.joinToString("") { it.value.text })
                            keyboardController?.hide()
                        }
                    }
                },
                modifier = Modifier
                    .size(boxSize)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (textState.value.text.isEmpty()) Color.White else curvedBgColor
                    )
                    .border(
                        width = if (textState.value.text.isEmpty()) 1.dp else 0.dp,
                        color = if (textState.value.text.isEmpty()) Color.Gray else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .focusRequester(focusRequesters[index]),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
                ),
                visualTransformation = VisualTransformation.None
            ) { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    innerTextField()
                }
            }
        }
    }
}
