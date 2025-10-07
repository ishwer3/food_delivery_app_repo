package com.example.deliveryapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// DummyJSON Login Response Model
@Serializable
data class LoginResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("image")
    val image: String,
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)

// Legacy AuthResponse for register/other endpoints if needed
@Serializable
data class AuthResponse(
    @SerialName("user")
    val user: UserDto,
    @SerialName("token")
    val token: String
)

@Serializable
data class UserDto(
    @SerialName("id")
    val id: String,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image_url")
    val profileImageUrl: String? = null
)