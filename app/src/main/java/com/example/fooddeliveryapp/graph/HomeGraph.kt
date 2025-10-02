package com.example.fooddeliveryapp.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.fooddeliveryapp.presentation.feature.dashboard.view.CategoryScreen
import com.example.fooddeliveryapp.presentation.feature.dashboard.view.DashboardScreen
import com.example.fooddeliveryapp.presentation.feature.dashboard.view.PaymentCardScreen
import com.example.fooddeliveryapp.presentation.feature.dashboard.view.settings.MapScreen
import com.example.fooddeliveryapp.presentation.feature.delivery.view.DeliveryTrackingScreen
import com.example.fooddeliveryapp.routes.AuthRoutes
import com.example.fooddeliveryapp.routes.HomeRoutes

fun NavGraphBuilder.homeNavGraph(navController: NavController){
    composable<HomeRoutes.DashboardRoute> {
        DashboardScreen(
            onNavigateToAuth = {
                navController.navigate(AuthRoutes.SplashRoute) {
                    popUpTo(HomeRoutes.DashboardRoute) { inclusive = true }
                }
            },
            onBuyNowClick = {
                navController.navigate(HomeRoutes.PaymentRoute)
            },
            onNavigateToCategory = {
                navController.navigate(HomeRoutes.SeeAllCategoriesRoute)
            }
        )
    }

    composable<HomeRoutes.MapRoute> {
        MapScreen()
    }

    composable<HomeRoutes.SeeAllCategoriesRoute> {
        CategoryScreen()
    }

    composable<HomeRoutes.PaymentRoute> {
        PaymentCardScreen(onPaymentSuccess = {
            navController.navigate(HomeRoutes.DeliveryTrackingRoute)
        })
    }

    composable<HomeRoutes.DeliveryTrackingRoute> {
        DeliveryTrackingScreen(
            onBackClick = {
                navController.navigate(HomeRoutes.DashboardRoute) {
                    popUpTo(HomeRoutes.DashboardRoute) { inclusive = false }
                }
            },
            onCallDriver = { phoneNumber ->
                // Handle call driver functionality
                // You can implement intent to dial the phone number
            }
        )
    }
}