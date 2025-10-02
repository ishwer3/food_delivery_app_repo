package com.example.fooddeliveryapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fooddeliveryapp.RootNavGraph

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    RootNavGraph(navController = navController)
}