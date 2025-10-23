package com.example.deliveryapp.graph

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.deliveryapp.presentation.feature.dashboard.viewmodel.CategoryViewModel
import com.example.deliveryapp.presentation.feature.delivery.view.DeliveryTrackingScreen
import com.example.deliveryapp.presentation.navigation.pop
import com.example.deliveryapp.presentation.navigation.push
import com.example.deliveryapp.presentation.navigation.replaceAll
import com.example.deliveryapp.routes.AuthRoutes
import com.example.deliveryapp.routes.HomeRoutes

fun NavGraphBuilder.homeNavGraph(navController: NavController){
    composable<HomeRoutes.DashboardRoute> { backStackEntry ->
        val categoryViewModel: CategoryViewModel = hiltViewModel(backStackEntry)
        val homeViewModel: com.example.deliveryapp.presentation.feature.dashboard.viewmodel.HomeViewModel = hiltViewModel(backStackEntry)
        DashboardScreen(
            categoryViewModel = categoryViewModel,
            homeViewModel = homeViewModel,
            onNavigateToAuth = {
                navController.replaceAll(AuthRoutes.SplashRoute)
            },
            onBuyNowClick = {
                navController.push(HomeRoutes.PaymentRoute)
            },
            onNavigateToCategory = {
                navController.push(HomeRoutes.SeeAllCategoriesRoute)
            },
            onNavigateToFoodDetails = { foodId ->
                navController.push(HomeRoutes.FoodDetailRoute(foodId))
            }
        )
    }

    composable<HomeRoutes.MapRoute> {
        MapScreen()
    }

    composable<HomeRoutes.SeeAllCategoriesRoute> { backStackEntry ->
        val parentEntry = navController.getBackStackEntry<HomeRoutes.DashboardRoute>()
        val categoryViewModel: CategoryViewModel = hiltViewModel(parentEntry)
        val homeViewModel: com.example.deliveryapp.presentation.feature.dashboard.viewmodel.HomeViewModel = hiltViewModel(parentEntry)
        CategoryScreen(
            categoryViewModel = categoryViewModel,
            homeViewModel = homeViewModel
        )
    }

    composable<HomeRoutes.PaymentRoute> {
        PaymentCardScreen(onPaymentSuccess = {
            navController.push(HomeRoutes.DeliveryTrackingRoute)
        })
    }

    composable<HomeRoutes.DeliveryTrackingRoute> {
        DeliveryTrackingScreen(
            onBackClick = {
                navController.replaceAll(HomeRoutes.DashboardRoute)
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
                navController.pop()
            }
        )
    }

    /*composable<HomeRoutes.FilterScreen> {
        FilterScreen()
    }*/
}