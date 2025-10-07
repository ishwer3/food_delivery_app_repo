package com.example.deliveryapp.presentation.feature.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.local.Constants.TAG
import com.example.deliveryapp.data.local.session.SessionManager
import com.example.deliveryapp.domain.usecase.LoginUseCase
import com.example.deliveryapp.domain.model.User
import com.example.deliveryapp.presentation.feature.auth.intent.AuthIntent
import com.example.deliveryapp.presentation.feature.auth.state.AuthState
import com.example.deliveryapp.presentation.feature.auth.state.EmailValidationState
import com.example.deliveryapp.presentation.feature.auth.state.PasswordValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        checkAuthenticationState()
    }

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
                    Log.d(TAG,"Saving to DataStore - userId: ${user.id}, username: $username, email: ${user.email}, name: ${user.name}")

                    // Save user session to DataStore
                    sessionManager.saveUserSession(
                        userId = user.id,
                        username = username,
                        email = user.email,
                        name = user.name,
                        profileImageUrl = user.profileImageUrl,
                        accessToken = "dummy_access_token",
                        refreshToken = "dummy_refresh_token"
                    )

                    Log.d(TAG,"Data saved to DataStore successfully")

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

    fun handleLogout() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                // Clear session using SessionManager
                sessionManager.clearSession()
                _state.value = _state.value.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    user = null,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Logout failed"
                )
            }
        }
    }

    // Save user session after OTP verification
    fun saveUserSession(
        userId: String = "user_${System.currentTimeMillis()}",
        username: String = "user",
        email: String = "user@example.com",
        name: String = "User",
        accessToken: String = "dummy_access_token",
        refreshToken: String = "dummy_refresh_token"
    ) {
        viewModelScope.launch {
            try {
                sessionManager.saveUserSession(
                    userId = userId,
                    username = username,
                    email = email,
                    name = name,
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
                val user = User(
                    id = userId,
                    email = email,
                    name = name
                )
                _state.value = _state.value.copy(
                    isLoggedIn = true,
                    user = user
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to save session"
                )
            }
        }
    }

    // Add method to check authentication state on app startup
    fun checkAuthenticationState() {
        viewModelScope.launch {
            try {
                val isLoggedIn = sessionManager.isLoggedIn.first()
                val userSession = sessionManager.currentUser.first()

                Log.d(TAG, "checkAuthenticationState - isLoggedIn: $isLoggedIn")
                Log.d(TAG, "checkAuthenticationState - userSession: $userSession")

                val user = userSession?.let {
                    User(
                        id = it.userId,
                        email = it.email,
                        name = it.name,
                        profileImageUrl = it.profileImageUrl
                    )
                }

                Log.d(TAG, "checkAuthenticationState - user: $user")

                _state.value = _state.value.copy(
                    isLoggedIn = isLoggedIn,
                    user = user
                )
            } catch (e: Exception) {
                Log.e(TAG, "checkAuthenticationState - Error: ${e.message}", e)
                _state.value = _state.value.copy(isLoggedIn = false, user = null)
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