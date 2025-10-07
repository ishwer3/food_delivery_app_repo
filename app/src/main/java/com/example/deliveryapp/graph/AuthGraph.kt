package com.example.deliveryapp.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.deliveryapp.presentation.feature.auth.view.LoginScreen
import com.example.deliveryapp.presentation.feature.auth.view.OtpScreen
import com.example.deliveryapp.presentation.feature.auth.view.RegisterScreen
import com.example.deliveryapp.presentation.feature.auth.view.SplashScreen
import com.example.deliveryapp.routes.AuthRoutes
import com.example.deliveryapp.routes.HomeRoutes

fun NavGraphBuilder.authNavGraph(navController: NavController){
    composable<AuthRoutes.SplashRoute> {
        //Splash Screen
        SplashScreen(
            onNavigateToHome = {
                navController.navigate(HomeRoutes.DashboardRoute) {
                    popUpTo(AuthRoutes.SplashRoute) { inclusive = true }
                }
            },
            onNavigateToLogin = {
                navController.navigate(AuthRoutes.LoginRoute) {
                    popUpTo(AuthRoutes.SplashRoute) { inclusive = true }
                }
            }
        )
    }

    composable<AuthRoutes.LoginRoute> {
        //Login Screen
        LoginScreen(
            onNavigateToRegister = {
                navController.navigate(AuthRoutes.RegisterRoute)
            },
            onNavigateToHome = {
                navController.navigate(HomeRoutes.DashboardRoute) {
                    popUpTo(AuthRoutes.SplashRoute) { inclusive = true }
                }
            },
            onNavigateToOtp = {
                navController.navigate(AuthRoutes.OtpRoute)
            }
        )
    }

    composable<AuthRoutes.RegisterRoute> {
        RegisterScreen(
            onNavigateToHome = {
                navController.navigate(HomeRoutes.DashboardRoute) {
                    popUpTo(AuthRoutes.SplashRoute) { inclusive = true }
                }
            },
            onNavigateToLogin = {
                navController.popBackStack()
            }
        )
    }

    composable<AuthRoutes.OtpRoute> {
        OtpScreen(onContinueClick = {
            navController.navigate(HomeRoutes.DashboardRoute) {
                popUpTo(AuthRoutes.OtpRoute) { inclusive = true }
            }
        })
    }
}