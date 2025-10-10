package com.example.deliveryapp.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeRoutes(val route: String) {

    @Serializable
    data object DashboardRoute : HomeRoutes("dashboard")

    @Serializable
    data object CategoriesRoute : HomeRoutes("categories")

    @Serializable
    data class FoodDetailRoute(val foodId: String) : HomeRoutes("food_detail")

    @Serializable
    data object CartRoute : HomeRoutes("cart")

    @Serializable
    data object CheckoutRoute : HomeRoutes("checkout")

    @Serializable
    data object OrderTrackingRoute : HomeRoutes("order_tracking")

    @Serializable
    data object ProfileRoute : HomeRoutes("profile")

    @Serializable
    data object SettingsRoute : HomeRoutes("settings")

    @Serializable
    data object FilterScreen: HomeRoutes("filter")

    @Serializable
    data object MapRoute : HomeRoutes("map")

    @Serializable
    data object PaymentRoute : HomeRoutes("payment")

    @Serializable
    data object DeliveryTrackingRoute : HomeRoutes("delivery_tracking")

    @Serializable
    data object SeeAllCategoriesRoute : HomeRoutes("see_all_categories")

    @Serializable
    data class AddressRoute(val hotelName: String) : HomeRoutes("address")
}