package com.example.deliveryapp.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.deliveryapp.presentation.feature.auth.view.LoginScreen
import com.example.deliveryapp.presentation.feature.auth.view.OtpScreen
import com.example.deliveryapp.presentation.feature.auth.view.RegisterScreen
import com.example.deliveryapp.presentation.feature.auth.view.SplashScreen
import com.example.deliveryapp.presentation.navigation.pop
import com.example.deliveryapp.presentation.navigation.push
import com.example.deliveryapp.presentation.navigation.replaceAll
import com.example.deliveryapp.routes.AuthRoutes
import com.example.deliveryapp.routes.HomeRoutes

fun NavGraphBuilder.authNavGraph(navController: NavController){
    composable<AuthRoutes.SplashRoute> {
        //Splash Screen
        SplashScreen(
            onNavigateToHome = {
                navController.replaceAll(HomeRoutes.DashboardRoute)
            },
            onNavigateToLogin = {
                navController.replaceAll(AuthRoutes.LoginRoute)
            }
        )
    }

    composable<AuthRoutes.LoginRoute> {
        //Login Screen
        LoginScreen(
            onNavigateToRegister = {
                navController.push(AuthRoutes.RegisterRoute)
            },
            onNavigateToHome = {
                navController.replaceAll(HomeRoutes.DashboardRoute)
            },
            onNavigateToOtp = {
                navController.push(AuthRoutes.OtpRoute)
            }
        )
    }

    composable<AuthRoutes.RegisterRoute> {
        RegisterScreen(
            onNavigateToHome = {
                navController.replaceAll(HomeRoutes.DashboardRoute)
            },
            onNavigateToLogin = {
                navController.pop()
            }
        )
    }

    composable<AuthRoutes.OtpRoute> {
        OtpScreen(onContinueClick = {
            navController.replaceAll(HomeRoutes.DashboardRoute)
        })
    }
}