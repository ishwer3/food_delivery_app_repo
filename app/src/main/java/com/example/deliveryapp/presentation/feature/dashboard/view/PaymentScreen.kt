package com.example.deliveryapp.presentation.feature.dashboard.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import com.example.deliveryapp.presentation.common.component.CommonSnackbar
import com.example.deliveryapp.presentation.common.component.rememberSnackbarState
import kotlinx.coroutines.delay

enum class PaymentState {
    IDLE, LOADING, SUCCESS
}

@Composable
fun PaymentCardScreen(
    onPaymentSuccess: () -> Unit = {}
) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var paymentState by remember { mutableStateOf(PaymentState.IDLE) }
    val snackbarState = rememberSnackbarState()

    // Handle payment success animation and navigation
    LaunchedEffect(paymentState) {
        Log.d("PaymentScreen", "Payment state changed to: $paymentState")
        if (paymentState == PaymentState.LOADING) {
            Log.d("PaymentScreen", "Starting payment processing...")
            delay(2000) // Simulate payment processing
            Log.d("PaymentScreen", "Payment processing complete, showing success")
            paymentState = PaymentState.SUCCESS
        } else if (paymentState == PaymentState.SUCCESS) {
            Log.d("PaymentScreen", "Showing success animation...")
            delay(2000) // Show success animation
            Log.d("PaymentScreen", "Navigating to delivery tracking")
            onPaymentSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Card Container
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE67E22)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Lorem Ipsum",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Holder ID",
                            color = Color.Black.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color.White
                        ) {
                            Text(
                                text = "CARD",
                                color = Color(0xFFE67E22),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(
                        text = "0000 0000 0000 0000",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 2.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Valid Thru",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = "01/25",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Column {
                            Text(
                                text = "CVV",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = "000",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Form Fields
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Card Number",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    placeholder = { Text("0000 0000 0000 0000", color = Color.White.copy(alpha = 0.6f)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black.copy(alpha = 0.5f),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Expires",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        OutlinedTextField(
                            value = expiryDate,
                            onValueChange = { expiryDate = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            placeholder = { Text("MM/YY", color = Color.White.copy(alpha = 0.6f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black.copy(alpha = 0.5f),
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Security",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        OutlinedTextField(
                            value = cvv,
                            onValueChange = { cvv = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            placeholder = { Text("CVV", color = Color.White.copy(alpha = 0.6f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black.copy(alpha = 0.5f),
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Submit Button
            Button(
                onClick = {
                    when {
                        cardNumber.isBlank() -> {
                            snackbarState.showError("Please enter card number")
                        }
                        cardNumber.replace(" ", "").length != 16 -> {
                            snackbarState.showError("Card number must be 16 digits")
                        }
                        expiryDate.isBlank() -> {
                            snackbarState.showError("Please enter expiry date")
                        }
                        cvv.isBlank() -> {
                            snackbarState.showError("Please enter CVV")
                        }
                        else -> {
                            paymentState = PaymentState.LOADING
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107)
                ),
                shape = RoundedCornerShape(28.dp),
                enabled = paymentState == PaymentState.IDLE
            ) {
                Text(
                    text = "Pay now",
                    color = Color(0xFF6D4C41),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Snackbar
        CommonSnackbar(
            modifier = Modifier.align(Alignment.BottomCenter),
            message = snackbarState.message,
            type = snackbarState.type,
            onDismiss = { snackbarState.dismiss() }
        )

        // Loading and Success Overlay
        if (paymentState == PaymentState.LOADING || paymentState == PaymentState.SUCCESS) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                when (paymentState) {
                    PaymentState.LOADING -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(64.dp),
                                color = Color(0xFFFFC107),
                                strokeWidth = 4.dp
                            )
                            Text(
                                text = "Processing Payment...",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    PaymentState.SUCCESS -> {
                        PaymentSuccessAnimation()
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun PaymentSuccessAnimation() {
    val scale = remember { Animatable(0f) }
    val checkmarkProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )
        checkmarkProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.scale(scale.value)
    ) {
        // Success Circle with Checkmark
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Circle background
                drawCircle(
                    color = Color(0xFF4CAF50),
                    radius = size.minDimension / 2
                )

                // Checkmark
                val progress = checkmarkProgress.value
                val strokeWidth = 8f

                // Short line of checkmark
                val shortLineStart = Offset(
                    x = size.width * 0.3f,
                    y = size.height * 0.5f
                )
                val shortLineEnd = Offset(
                    x = size.width * 0.3f + (size.width * 0.15f * progress.coerceIn(0f, 0.5f) / 0.5f),
                    y = size.height * 0.5f + (size.height * 0.15f * progress.coerceIn(0f, 0.5f) / 0.5f)
                )

                if (progress > 0f) {
                    drawLine(
                        color = Color.White,
                        start = shortLineStart,
                        end = shortLineEnd,
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                }

                // Long line of checkmark
                if (progress > 0.5f) {
                    val longProgress = (progress - 0.5f) / 0.5f
                    val longLineStart = shortLineEnd
                    val longLineEnd = Offset(
                        x = shortLineEnd.x + (size.width * 0.35f * longProgress),
                        y = shortLineEnd.y - (size.height * 0.35f * longProgress)
                    )

                    drawLine(
                        color = Color.White,
                        start = longLineStart,
                        end = longLineEnd,
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        Text(
            text = "Payment Successful!",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Redirecting to delivery tracking...",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp
        )
    }
}