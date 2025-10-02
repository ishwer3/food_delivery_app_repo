package com.example.fooddeliveryapp.presentation.feature.auth.state

import com.example.fooddeliveryapp.domain.model.User

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val showRegisterScreen: Boolean = false,
    val emailValidation: EmailValidationState = EmailValidationState(),
    val passwordValidation: PasswordValidationState = PasswordValidationState(),
    val isLoginButtonEnabled: Boolean = false
)