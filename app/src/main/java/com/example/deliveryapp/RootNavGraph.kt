package com.example.deliveryapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.deliveryapp.graph.authNavGraph
import com.example.deliveryapp.graph.homeNavGraph
import com.example.deliveryapp.presentation.navigation.CommonNavHost
import com.example.deliveryapp.routes.AuthRoutes
import com.example.deliveryapp.routes.HomeRoutes

@Composable
fun RootNavGraph(
    navController: NavHostController,
) {
    CommonNavHost(
        navController = navController,
        startDestination = AuthRoutes.SplashRoute
    ) {
        authNavGraph(navController = navController)
        homeNavGraph(navController = navController)
    }
}

