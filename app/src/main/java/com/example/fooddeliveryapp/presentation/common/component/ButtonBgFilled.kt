package com.example.fooddeliveryapp.presentation.common.component

import android.R.attr.enabled
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddeliveryapp.ui.theme.ButtonColor
import com.example.fooddeliveryapp.ui.theme.blackStyle

@Composable
fun ButtonBgFilled(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textStyle: TextStyle = blackStyle.copy(fontSize = fontSize, fontWeight = fontWeight),
    bgColor: Color = ButtonColor,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
){
    Button(
        modifier = modifier.fillMaxWidth().height(60.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        enabled = enabled && !isLoading,
        onClick = {
            onClick()
        }
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = textStyle
            )
        }

    }
}