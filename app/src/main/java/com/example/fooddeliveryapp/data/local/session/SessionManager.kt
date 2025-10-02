package com.example.fooddeliveryapp.data.local.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USERNAME = stringPreferencesKey("username")
        private val EMAIL = stringPreferencesKey("email")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val PROFILE_IMAGE_URL = stringPreferencesKey("profile_image_url")
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val LOGIN_TIMESTAMP = stringPreferencesKey("login_timestamp")
    }

    // Check if user is logged in
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    // Get current user data
    val currentUser: Flow<UserSession?> = context.dataStore.data.map { preferences ->
        if (preferences[IS_LOGGED_IN] == true) {
            UserSession(
                userId = preferences[USER_ID] ?: "",
                username = preferences[USERNAME] ?: "",
                email = preferences[EMAIL] ?: "",
                name = preferences[USER_NAME] ?: "",
                profileImageUrl = preferences[PROFILE_IMAGE_URL],
                accessToken = preferences[ACCESS_TOKEN] ?: "",
                refreshToken = preferences[REFRESH_TOKEN] ?: "",
                loginTimestamp = preferences[LOGIN_TIMESTAMP] ?: ""
            )
        } else {
            null
        }
    }

    // Save user session after successful login
    suspend fun saveUserSession(
        userId: String,
        username: String,
        email: String,
        name: String,
        profileImageUrl: String? = null,
        accessToken: String,
        refreshToken: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[USER_ID] = userId
            preferences[USERNAME] = username
            preferences[EMAIL] = email
            preferences[USER_NAME] = name
            preferences[PROFILE_IMAGE_URL] = profileImageUrl ?: ""
            preferences[ACCESS_TOKEN] = accessToken
            preferences[REFRESH_TOKEN] = refreshToken
            preferences[LOGIN_TIMESTAMP] = System.currentTimeMillis().toString()
        }
    }

    // Clear all session data (logout)
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Update access token (for token refresh)
    suspend fun updateAccessToken(newAccessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = newAccessToken
        }
    }

    // Get access token
    suspend fun getAccessToken(): String? {
        var token: String? = null
        context.dataStore.data.map { preferences ->
            token = preferences[ACCESS_TOKEN]
        }
        return token
    }

    // Check if session is valid (can be enhanced with token expiry logic)
    suspend fun isSessionValid(): Boolean {
        var isValid = false
        context.dataStore.data.map { preferences ->
            val isLoggedIn = preferences[IS_LOGGED_IN] ?: false
            val hasToken = !preferences[ACCESS_TOKEN].isNullOrEmpty()
            isValid = isLoggedIn && hasToken
        }
        return isValid
    }
}

data class UserSession(
    val userId: String,
    val username: String,
    val email: String,
    val name: String,
    val profileImageUrl: String? = null,
    val accessToken: String,
    val refreshToken: String,
    val loginTimestamp: String
)