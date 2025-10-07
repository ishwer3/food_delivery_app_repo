package com.example.deliveryapp.data.mapper

import com.example.deliveryapp.data.remote.dto.LoginResponse
import com.example.deliveryapp.data.remote.dto.UserDto
import com.example.deliveryapp.domain.model.User

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