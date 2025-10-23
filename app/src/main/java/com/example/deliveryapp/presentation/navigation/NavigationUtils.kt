package com.example.deliveryapp.presentation.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavController.replaceAl(
    nextDestinationId: Int,
    bundle: Bundle? = null,
) {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(this.currentDestination!!.id, true)
        .build()
    this.navigate(nextDestinationId, navOptions)
}

fun <T : Any> NavController.push(
    nextDestinationId: T,
    bundle: Bundle? = null,
) {
    navigate(nextDestinationId)
}

fun <T : Any> NavController.replace(
    nextDestinationId: T,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    popBackStack()
    navigate(nextDestinationId, navOptions, navigatorExtras)
}


// Navigate to a destination, clearing all previous destinations
fun <T : Any> NavController.replaceAll(
    nextDestinationId: T,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
//    popBackStack(graph.startDestinationId, true)
    val options = navOptions ?: NavOptions.Builder()
        .setPopUpTo(0, true)
        .build()
    navigate(nextDestinationId, options, navigatorExtras)

}
fun <T : Any> NavController.replaceUntil(
    nextDestination: T,
    popUpToRoute: Any, // Accepts route as String instead of ID
    inclusive: Boolean = false,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    popBackStack(popUpToRoute, true) // Manually pop backstack to target route
    navigate(nextDestination, navOptions, navigatorExtras)
}

// Pop the top destination off the back stack
fun NavController.pop() {
    popBackStack()
}

// Pop all destinations off the back stack
fun NavController.popAll() {
    popBackStack(graph.startDestinationId, true)
}

// Pop back to a specific destination
fun <T : Any> NavController.popUntil(destinationId: T, inclusive: Boolean = false) {
    popBackStack(destinationId, inclusive)
}
