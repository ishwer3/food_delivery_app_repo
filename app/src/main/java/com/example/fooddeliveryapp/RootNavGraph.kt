package com.example.fooddeliveryapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.fooddeliveryapp.graph.authNavGraph
import com.example.fooddeliveryapp.graph.homeNavGraph
import com.example.fooddeliveryapp.routes.AuthRoutes
import com.example.fooddeliveryapp.routes.HomeRoutes

@Composable
fun RootNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoutes.DashboardRoute
    ) {
        authNavGraph(navController = navController)
        homeNavGraph(navController = navController)
    }
}

