package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.local.session.SessionManager
import com.example.deliveryapp.data.mapper.toDomain
import com.example.deliveryapp.data.remote.api.AuthApi
import com.example.deliveryapp.data.remote.api.LoginRequest
import com.example.deliveryapp.data.remote.api.RegisterRequest
import com.example.deliveryapp.domain.model.User
import com.example.deliveryapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<User> {
        return try {
            val response = authApi.login(LoginRequest(username, password))
            val user = response.toDomain()

            // Save user session to local storage
            sessionManager.saveUserSession(
                userId = response.id.toString(),
                username = response.username,
                email = response.email,
                name = "${response.firstName} ${response.lastName}",
                profileImageUrl = response.image,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken
            )

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(username: String, password: String, name: String): Result<User> {
        return try {
            val response = authApi.register(RegisterRequest(username, password, name))
            val user = response.user.toDomain()

            // Save user session to local storage
            sessionManager.saveUserSession(
                userId = response.user.id,
                username = username,
                email = response.user.email,
                name = response.user.name,
                profileImageUrl = response.user.profileImageUrl,
                accessToken = response.token,
                refreshToken = response.token // For now, using same token
            )

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            // Call logout API if needed
            try {
                authApi.logout()
            } catch (e: Exception) {
                // API logout might fail, but we still want to clear local session
            }

            // Clear local session
            sessionManager.clearSession()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        return sessionManager.currentUser.map { userSession ->
            userSession?.let {
                User(
                    id = it.userId,
                    email = it.email,
                    name = it.name,
                    profileImageUrl = it.profileImageUrl
                )
            }
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return sessionManager.isSessionValid()
    }

    // Additional method to check login state as Flow
    fun isLoggedInFlow(): Flow<Boolean> {
        return sessionManager.isLoggedIn
    }

    // Get access token for API calls
    suspend fun getAccessToken(): String? {
        return sessionManager.getAccessToken()
    }
}