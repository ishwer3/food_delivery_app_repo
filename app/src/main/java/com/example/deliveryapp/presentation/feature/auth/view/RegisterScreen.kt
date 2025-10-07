package com.example.deliveryapp.presentation.feature.auth.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deliveryapp.presentation.common.component.ButtonBgFilled
import com.example.deliveryapp.presentation.common.component.CommonTextField
import com.example.deliveryapp.presentation.common.component.TopCurvedView
import com.example.deliveryapp.presentation.feature.auth.intent.AuthIntent
import com.example.deliveryapp.presentation.feature.auth.viewmodel.AuthViewModel
import com.example.deliveryapp.ui.spacer.MaxSpacer
import com.example.deliveryapp.ui.theme.whiteStyle
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onNavigateToHome()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TopCurvedView {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create your account",
                    style = whiteStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.Normal)
                )

                Text(
                    text = "Register",
                    style = whiteStyle.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold)
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 20.dp, vertical = 25.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CommonTextField(
                label = "Full Name",
                hint = "Enter Full Name",
                text = name,
                onValueChange = { name = it }
            )
            CommonTextField(
                label = "Email",
                hint = "Enter Email",
                text = email,
                onValueChange = { email = it }
            )
            CommonTextField(
                label = "Password",
                hint = "Enter Password",
                text = password,
                onValueChange = { password = it }
            )
            CommonTextField(
                label = "Confirm Password",
                hint = "Enter Confirm Password",
                text = confirmPassword,
                onValueChange = { confirmPassword = it }
            )
            MaxSpacer()

            ButtonBgFilled(
                text = "Register",
                onClick = {
                    if (password == confirmPassword) {
                        viewModel.handleIntent(AuthIntent.Register(email, password, name))
                    }
                },
                isLoading = state.isLoading,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            TextButton(
                onClick = onNavigateToLogin
            ) {
                Text("Already have an account? Login")
            }
        }

        state.error?.let { error ->
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}