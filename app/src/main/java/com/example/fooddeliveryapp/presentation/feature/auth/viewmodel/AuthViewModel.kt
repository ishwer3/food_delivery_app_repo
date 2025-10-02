package com.example.fooddeliveryapp.presentation.feature.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.data.local.Constants.TAG
import com.example.fooddeliveryapp.domain.usecase.LoginUseCase
import com.example.fooddeliveryapp.presentation.feature.auth.intent.AuthIntent
import com.example.fooddeliveryapp.presentation.feature.auth.state.AuthState
import com.example.fooddeliveryapp.presentation.feature.auth.state.EmailValidationState
import com.example.fooddeliveryapp.presentation.feature.auth.state.PasswordValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Login -> handleLogin(intent.username, intent.password)
            is AuthIntent.Register -> handleRegister(intent.email, intent.password, intent.name)
            is AuthIntent.ValidateEmail -> validateEmail(intent.email)
            is AuthIntent.ValidatePassword -> validatePassword(intent.password)
            is AuthIntent.Logout -> handleLogout()
            is AuthIntent.NavigateToRegister -> navigateToRegister()
            is AuthIntent.NavigateToLogin -> navigateToLogin()
            is AuthIntent.ClearError -> clearError()
        }
    }

    private fun handleLogin(username: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            loginUseCase(username, password)
                .onSuccess { user ->
                    Log.d(TAG,"handleLogin: $user")
                    _state.value = _state.value.copy(
                        isLoading = false,
                        user = user,
                        isLoggedIn = true
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
        }
    }

    private fun handleRegister(email: String, password: String, name: String) {
        // TODO: Implement register logic
    }

    private fun handleLogout() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            loginUseCase.logout()
                .onSuccess {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Logout failed"
                    )
                }
        }
    }

    // Add method to check authentication state on app startup
    fun checkAuthenticationState() {
        viewModelScope.launch {
            try {
                val isLoggedIn = loginUseCase.isLoggedIn()
                _state.value = _state.value.copy(isLoggedIn = isLoggedIn)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoggedIn = false)
            }
        }
    }

    private fun navigateToRegister() {
        _state.value = _state.value.copy(showRegisterScreen = true)
    }

    private fun navigateToLogin() {
        _state.value = _state.value.copy(showRegisterScreen = false)
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    private fun validateEmail(email: String) {
        val emailValidation = EmailValidationState.validate(email)
        val currentState = _state.value
        val newState = currentState.copy(
            emailValidation = emailValidation,
            isLoginButtonEnabled = emailValidation.isValid && currentState.passwordValidation.isValid
        )
        _state.value = newState
    }

    private fun validatePassword(password: String) {
        val passwordValidation = PasswordValidationState.validate(password)
        val currentState = _state.value
        val newState = currentState.copy(
            passwordValidation = passwordValidation,
            isLoginButtonEnabled = passwordValidation.isValid
        )
        _state.value = newState
    }
}