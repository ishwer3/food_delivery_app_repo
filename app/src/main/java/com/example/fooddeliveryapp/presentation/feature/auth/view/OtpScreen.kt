package com.example.fooddeliveryapp.presentation.feature.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import com.example.fooddeliveryapp.presentation.common.component.TopCurvedView
import com.example.fooddeliveryapp.ui.theme.whiteStyle

@Composable
fun OtpScreen(
    onContinueClick: () -> Unit
) {
    var otpCode by remember { mutableStateOf("") }
    val maxOtpLength = 4

    val numberPadItems = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9",
        "", "0", "⌫"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopCurvedView {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Verification",
                    style = whiteStyle.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold)
                )

                Text(
                    text = "Lorem ipsum dolor sit amet!",
                    style = whiteStyle.copy(fontSize = 16.sp, fontWeight = FontWeight.Normal)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // OTP Input Display
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(maxOtpLength) { index ->
                    OtpDigitBox(
                        digit = if (index < otpCode.length) otpCode[index].toString() else "",
                        isFilled = index < otpCode.length
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Number Pad
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 40.dp)
            ) {
                items(numberPadItems) { item ->
                    when (item) {
                        "" -> {
                            // Empty space
                            Box(modifier = Modifier.size(60.dp))
                        }
                        "⌫" -> {
                            // Backspace button
                            NumberPadButton(
                                onClick = {
                                    if (otpCode.isNotEmpty()) {
                                        otpCode = otpCode.dropLast(1)
                                    }
                                }
                            ) {
                                Text(
                                    text = "⌫",
                                    fontSize = 20.sp,
                                    color = Color(0xFF666666)
                                )
                            }
                        }
                        else -> {
                            // Number button
                            NumberPadButton(
                                onClick = {
                                    if (otpCode.length < maxOtpLength) {
                                        otpCode += item
                                        if (otpCode.length == maxOtpLength) {
                                            // Auto-continue when OTP is complete
                                            onContinueClick()
                                        }
                                    }
                                }
                            ) {
                                Text(
                                    text = item,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF333333)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OtpDigitBox(
    digit: String,
    isFilled: Boolean
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(
                color = if (isFilled) Color(0xFFFFA726) else Color(0xFFF5F5F5),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = if (isFilled) Color(0xFFFF9800) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = digit,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (isFilled) Color.White else Color(0xFF666666)
        )
    }
}

@Composable
private fun NumberPadButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable { onClick() },
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}