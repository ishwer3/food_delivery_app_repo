package com.example.deliveryapp.domain.usecase

import com.example.deliveryapp.domain.model.User
import com.example.deliveryapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        return authRepository.login(username, password)
    }

    suspend fun logout(): Result<Unit> {
        return authRepository.logout()
    }

    suspend fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }

    fun getCurrentUser() = authRepository.getCurrentUser()
}