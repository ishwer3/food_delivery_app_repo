package com.example.deliveryapp.presentation.feature.dashboard.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.deliveryapp.presentation.common.component.BadgedNavigationItem
import com.example.deliveryapp.presentation.feature.dashboard.navigation.bottomNavItems
import com.example.deliveryapp.presentation.feature.dashboard.view.cart.view.CartScreen
import com.example.deliveryapp.presentation.feature.dashboard.view.settings.MapScreen
import com.example.deliveryapp.presentation.feature.dashboard.view.settings.SettingsScreen
import com.example.deliveryapp.presentation.feature.dashboard.viewmodel.CategoryViewModel
import com.example.deliveryapp.presentation.state.CartViewModel
import com.example.deliveryapp.routes.HomeRoutes

@Composable
fun DashboardScreen(
    categoryViewModel: CategoryViewModel,
    homeViewModel: com.example.deliveryapp.presentation.feature.dashboard.viewmodel.HomeViewModel,
    navController: NavHostController = rememberNavController(),
    onNavigateToAuth: () -> Unit = {},
    onNavigateToCategory: () -> Unit = {},
    onBuyNowClick: () -> Unit = {},
    onNavigateToFoodDetails: (String) -> Unit = {}
) {
    val cartViewModel: CartViewModel = hiltViewModel()

    // Collect cart item count from StateFlow
    val cartItemCount by cartViewModel.itemCount.collectAsState()

    // Observe current navigation destination to sync bottom nav
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Check if we should show bottom bar
    val showBottomBar = currentRoute?.contains("FoodDetailRoute") != true

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                    bottomNavItems.forEachIndexed { index, item ->
                        // Check if current route matches this bottom nav item
                        // For type-safe routes, the route string contains the full class name
                        val isSelected = when (item.route) {
                            "search" -> currentRoute == "search"
                            "cart" -> currentRoute?.contains(HomeRoutes.CartRoute::class.simpleName ?: "") == true
                            "profile" -> currentRoute?.contains(HomeRoutes.ProfileRoute::class.simpleName ?: "") == true
                            "map" -> currentRoute?.contains(HomeRoutes.MapRoute::class.simpleName ?: "") == true
                            "settings" -> currentRoute?.contains(HomeRoutes.SettingsRoute::class.simpleName ?: "") == true
                            else -> currentRoute == item.route
                        }

                        if (item.route == "cart") {
                            BadgedNavigationItem(
                                selected = isSelected,
                                onClick = {
                                    navController.navigate(HomeRoutes.CartRoute) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = if (isSelected) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                label = item.title,
                                badgeCount = cartItemCount
                            )
                        } else {
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = {
                                    when (item.route) {
                                        "home" -> {
                                            navController.navigate("home") {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                        "search" -> {
                                            navController.navigate("search") {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                        "profile" -> {
                                            navController.navigate(HomeRoutes.ProfileRoute) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                        "map" -> {
                                            navController.navigate(HomeRoutes.MapRoute) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                        "settings" -> {
                                            navController.navigate(HomeRoutes.SettingsRoute) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                },
                                label = {
                                    Text(text = item.title)
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) {
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
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = bottomNavItems[0].route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable("home") {
                    HomeScreen(
                        cartViewModel = cartViewModel,
                        categoryViewModel = categoryViewModel,
                        homeViewModel = homeViewModel,
                        onSeeAllClick = onNavigateToCategory,
                        onFoodItemClick = { item ->
                            onNavigateToFoodDetails(item.id)
                        }
                    )
                }
                composable("search") {
                    SearchScreen()
                }
                composable<HomeRoutes.CartRoute> {
                    CartScreen(
                        cartViewModel = cartViewModel,
                        onBuyNowClick = {
                            onBuyNowClick()
                        },
                        onEnterAddressClick = { hotelName ->
                            navController.navigate(HomeRoutes.AddressRoute(hotelName))
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

                composable<HomeRoutes.ProfileRoute> {
                    ProfileScreen(onNavigateToAuth = onNavigateToAuth)
                }
                composable<HomeRoutes.SettingsRoute> {
                    SettingsScreen(
                        onItemClick = {
                            navController.navigate(HomeRoutes.MapRoute)
                        },
                        onLogout = onNavigateToAuth
                    )
                }
                composable("user") {
                    UserScreen()
//                    FilterScreen()
                }
                composable<HomeRoutes.MapRoute> {
                    MapScreen()
                }

                composable<HomeRoutes.AddressRoute> { backStackEntry ->
                    val addressRoute = backStackEntry.toRoute<HomeRoutes.AddressRoute>()
                    AddressScreen(
                        hotelName = addressRoute.hotelName,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}