package com.example.fooddeliveryapp.presentation.feature.auth.intent

sealed class AuthIntent {
    data class Login(
        val username: String,
        val password: String
    ) : AuthIntent()

    data class Register(
        val email: String,
        val password: String,
        val name: String
    ) : AuthIntent()

    data class ValidateEmail(val email: String) : AuthIntent()

    data class ValidatePassword(val password: String) : AuthIntent()

    object Logout : AuthIntent()

    object NavigateToRegister : AuthIntent()

    object NavigateToLogin : AuthIntent()

    object ClearError : AuthIntent()
}