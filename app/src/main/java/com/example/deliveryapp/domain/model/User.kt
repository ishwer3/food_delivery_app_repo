package com.example.deliveryapp.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val profileImageUrl: String? = null
)