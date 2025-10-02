package com.example.fooddeliveryapp.data.mapper

import com.example.fooddeliveryapp.data.remote.dto.LoginResponse
import com.example.fooddeliveryapp.data.remote.dto.UserDto
import com.example.fooddeliveryapp.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        name = name,
        profileImageUrl = profileImageUrl
    )
}

fun LoginResponse.toDomain(): User {
    return User(
        id = id.toString(),
        email = email,
        name = "$firstName $lastName",
        profileImageUrl = image
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        name = name,
        profileImageUrl = profileImageUrl
    )
}