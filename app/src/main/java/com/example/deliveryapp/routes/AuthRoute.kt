package com.example.deliveryapp.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthRoutes{

    @Serializable
    data object SplashRoute: AuthRoutes()

    @Serializable
    data object LoginRoute: AuthRoutes()

    @Serializable
    data object RegisterRoute: AuthRoutes()

    @Serializable
    data object OtpRoute: AuthRoutes()
}