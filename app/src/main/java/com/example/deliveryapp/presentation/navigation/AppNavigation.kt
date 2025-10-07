package com.example.deliveryapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deliveryapp.RootNavGraph

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    RootNavGraph(navController = navController)
}