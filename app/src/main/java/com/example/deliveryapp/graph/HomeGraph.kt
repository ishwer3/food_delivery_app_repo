package com.example.deliveryapp.graph

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.presentation.feature.dashboard.view.CategoryScreen
import com.example.deliveryapp.presentation.feature.dashboard.view.DashboardScreen
import com.example.deliveryapp.presentation.feature.dashboard.view.FoodDetailsScreen
import com.example.deliveryapp.presentation.feature.dashboard.view.PaymentCardScreen
import com.example.deliveryapp.presentation.feature.dashboard.view.settings.MapScreen
import com.example.deliveryapp.presentation.feature.delivery.view.DeliveryTrackingScreen
import com.example.deliveryapp.routes.AuthRoutes
import com.example.deliveryapp.routes.HomeRoutes

fun NavGraphBuilder.homeNavGraph(navController: NavController){
    composable<HomeRoutes.DashboardRoute> { backStackEntry ->
        // Get selected category from savedStateHandle
        val selectedCategory = backStackEntry.savedStateHandle
            .getStateFlow<String?>("selected_category", null)
            .collectAsState().value

        DashboardScreen(
            selectedCategory = selectedCategory,
            onCategoryHandled = {
                // Clear the saved state after handling
                backStackEntry.savedStateHandle.set("selected_category", null as String?)
            },
            onNavigateToAuth = {
                navController.navigate(AuthRoutes.SplashRoute) {
                    popUpTo(0) { inclusive = true }
                }
            },
            onBuyNowClick = {
                navController.navigate(HomeRoutes.PaymentRoute)
            },
            onNavigateToCategory = {
                navController.navigate(HomeRoutes.SeeAllCategoriesRoute)
            },
            onNavigateToFoodDetails = { foodId ->
                navController.navigate(HomeRoutes.FoodDetailRoute(foodId))
            }
        )
    }

    composable<HomeRoutes.MapRoute> {
        MapScreen()
    }

    composable<HomeRoutes.SeeAllCategoriesRoute> {
        CategoryScreen(
            onCategoryClick = { categoryTitle ->
                // Navigate back to home with the selected category
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("selected_category", categoryTitle)
                navController.popBackStack()
            }
        )
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

    composable<HomeRoutes.FoodDetailRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<HomeRoutes.FoodDetailRoute>()
        val foodId = route.foodId

        FoodDetailsScreen(
            foodId = foodId,
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    /*composable<HomeRoutes.FilterScreen> {
        FilterScreen()
    }*/
}