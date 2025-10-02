package com.example.fooddeliveryapp.presentation.feature.dashboard.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fooddeliveryapp.presentation.common.component.BadgedNavigationItem
import com.example.fooddeliveryapp.presentation.feature.dashboard.view.cart.view.CartScreen
import com.example.fooddeliveryapp.presentation.feature.dashboard.navigation.bottomNavItems
import com.example.fooddeliveryapp.presentation.feature.dashboard.view.settings.MapScreen
import com.example.fooddeliveryapp.presentation.feature.dashboard.view.settings.SettingsScreen
import com.example.fooddeliveryapp.presentation.state.CartViewModel
import com.example.fooddeliveryapp.routes.HomeRoutes

@Composable
fun DashboardScreen(
    navController: NavHostController = rememberNavController(),
    onNavigateToAuth: () -> Unit = {},
    onNavigateToCategory: () -> Unit = {},
    onBuyNowClick: () -> Unit = {}
) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val cartViewModel: CartViewModel = viewModel()

    // State to show/hide track order UI (simulate active order)
    var hasActiveOrder by remember { mutableStateOf(true) } // Set to true to show the track order UI

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, item ->
                        if (item.route == "cart") {
                            BadgedNavigationItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                label = item.title,
                                badgeCount = cartViewModel.itemCount
                            )
                        } else {
                            NavigationBarItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                label = {
                                    Text(text = item.title)
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = bottomNavItems[0].route,
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                composable("home") {
                    HomeScreen(
                        cartViewModel = cartViewModel,
//                        onSeeAllClick = onNavigateToCategory
                        onSeeAllClick = {
                            navController.navigate(HomeRoutes.AddressRoute)
                        }
                    )
                }
                composable("cart") {
                    CartScreen(
                        cartViewModel = cartViewModel,
                        onBuyNowClick = {
                            onBuyNowClick()
                        },
                        onEnterAddressClick = {
                            // Handle enter address - navigate to address screen
                        }
                    )
                }
                /*composable("payment") {
                    PaymentCardScreen(
                        onPaymentSuccess = {
                            navController.navigate("delivery_tracking") {
                                popUpTo("cart") { inclusive = true }
                            }
                        }
                    )
                }*/

                composable("profile") {
                    ProfileScreen(onNavigateToAuth = onNavigateToAuth)
                }
                composable("settings") {
                    SettingsScreen(onItemClick = {
                        navController.navigate(HomeRoutes.MapRoute)
                    })
                }
                composable("user") {
                    UserScreen()
                }
                composable<HomeRoutes.MapRoute> {
                    MapScreen()
                }

                composable<HomeRoutes.AddressRoute> {
                    AddressScreen {

                    }
                }
            }
        }
    }
}