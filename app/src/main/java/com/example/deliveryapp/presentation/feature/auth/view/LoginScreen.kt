package com.example.deliveryapp.presentation.feature.auth.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.presentation.common.component.ButtonBgFilled
import com.example.deliveryapp.presentation.common.component.CommonSnackbar
import com.example.deliveryapp.presentation.common.component.CommonTextField
import com.example.deliveryapp.presentation.common.component.TopCurvedView
import com.example.deliveryapp.presentation.common.component.rememberSnackbarState
import com.example.deliveryapp.presentation.feature.auth.intent.AuthIntent
import com.example.deliveryapp.presentation.feature.auth.viewmodel.AuthViewModel
import com.example.deliveryapp.ui.spacer.MaxSpacer
import com.example.deliveryapp.ui.theme.whiteStyle

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToOtp: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarState = rememberSnackbarState()

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("michaelw") }
    var password by remember { mutableStateOf("michaelwpass") }
//    viewModel.handleIntent(AuthIntent.ValidateEmail(email))

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onNavigateToOtp()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarState.showError(error)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
        )
        {
            TopCurvedView {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Lorem ipsum dolor sit",
                        style = whiteStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.Normal)
                    )

                    Text(
                        text = "Login",
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
                    label = "Username",
                    hint = "Enter Username",
                    text = username,
                    onValueChange = {
                        username = it
                        // Note: ValidateEmail is being used for username validation for now
                        viewModel.handleIntent(AuthIntent.ValidateEmail(it))
                    }
                )

                CommonTextField(
                    label = "Password",
                    hint = "Enter Password",
                    text = password,
                    onValueChange = {
                        password = it
                        viewModel.handleIntent(AuthIntent.ValidatePassword(it))
                    }
                )
                MaxSpacer()

                ButtonBgFilled(
                    text = "Continue",
                    onClick = {
                        viewModel.handleIntent(AuthIntent.Login(username, password))
                        /*when {
                            !state.emailValidation.isValid -> {
                                state.emailValidation.errorMessage?.let { message ->
                                    snackbarState.showError(message)
                                }
                            }
                            !state.passwordValidation.isValid -> {
                                state.passwordValidation.errorMessage?.let { message ->
                                    snackbarState.showError(message)
                                }
                            }
                            state.isLoginButtonEnabled -> {
                                viewModel.handleIntent(AuthIntent.Login(email, password))
                                onNavigateToOtp()
                            }
                        }*/
                    },
                    isLoading = state.isLoading,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                TextButton(
                    onClick = onNavigateToRegister
                ) {
                    Text("Don't have an account? Register")
                }
            }
        }

        CommonSnackbar(
            modifier = Modifier.align(Alignment.BottomCenter),
            message = snackbarState.message,
            type = snackbarState.type,
            onDismiss = { snackbarState.dismiss() }
        )
    }
}