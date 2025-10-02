package com.example.fooddeliveryapp.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeRoutes {

    @Serializable
    data object DashboardRoute : HomeRoutes()

    @Serializable
    data object CategoriesRoute : HomeRoutes()

    @Serializable
    data class FoodDetailRoute(val foodId: String) : HomeRoutes()

    @Serializable
    data object CartRoute : HomeRoutes()

    @Serializable
    data object CheckoutRoute : HomeRoutes()

    @Serializable
    data object OrderTrackingRoute : HomeRoutes()

    @Serializable
    data object ProfileRoute : HomeRoutes()

    @Serializable
    data object SettingsRoute : HomeRoutes()

    @Serializable
    data object FilterScreen: HomeRoutes()

    @Serializable
    data object MapRoute : HomeRoutes()

    @Serializable
    data object PaymentRoute : HomeRoutes()

    @Serializable
    data object DeliveryTrackingRoute : HomeRoutes()

    @Serializable
    data object SeeAllCategoriesRoute : HomeRoutes()

    @Serializable
    data object AddressRoute : HomeRoutes()
}