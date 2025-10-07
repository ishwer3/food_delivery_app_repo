package com.example.deliveryapp.data.local.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.LatLng

data class DeliveryPerson(
    val id: String,
    val name: String,
    val profilePictureUrl: String,
    val rating: Float,
    val phoneNumber: String,
    val currentLocation: LatLng,
    val vehicleType: String = "Bike"
)

data class DeliveryOrder(
    val orderId: String,
    val status: String,
    val estimatedDeliveryTimeMinutes: Int,
    val deliveryAddress: String,
    val deliveryLocation: LatLng,
    val deliveryPerson: DeliveryPerson
)

data class RoutePoint(
    val latitude: Double,
    val longitude: Double
)

enum class DeliveryStatus(val displayName: String) {
    PREPARING("Preparing your order"),
    ON_THE_WAY("Your order is on the way"),
    NEARBY("Driver is nearby"),
    DELIVERED("Order delivered")
}

object MockDeliveryData {
    fun getSampleDeliveryOrder(): DeliveryOrder {
        return DeliveryOrder(
            orderId = "ORD-12345",
            status = DeliveryStatus.ON_THE_WAY.displayName,
            estimatedDeliveryTimeMinutes = 15,
            deliveryAddress = "123 Main Street, City",
            deliveryLocation = LatLng(37.7849, -122.4094), // Sample SF location
            deliveryPerson = DeliveryPerson(
                id = "driver_001",
                name = "John Delivery",
                profilePictureUrl = "https://example.com/profile.jpg", // Mock URL
                rating = 4.8f,
                phoneNumber = "+1234567890",
                currentLocation = LatLng(37.7849, -122.4194) // Slightly different location
            )
        )
    }

    // Mock route points for polyline
    fun getMockRoute(): List<LatLng> {
        return listOf(
            LatLng(37.7849, -122.4194), // Driver location
            LatLng(37.7859, -122.4184),
            LatLng(37.7869, -122.4174),
            LatLng(37.7879, -122.4164),
            LatLng(37.7869, -122.4154),
            LatLng(37.7859, -122.4144),
            LatLng(37.7849, -122.4134),
            LatLng(37.7849, -122.4094)  // Delivery location
        )
    }
}

@Composable
fun StaticMapImageWithPolyline(apiKey: String) {
    // 1. Define your polyline points (LatLng objects)
    // For simplicity, we'll use a short, unencoded path format
    val pathCoordinates = "40.7128,-74.0060|34.0522,-118.2437" // NY to LA

    // 2. Construct the Static Map API URL
    // Center: somewhere between the points (or use one of the points)
    // path=color:0xff0000ff|weight:5|40.7128,-74.0060|34.0522,-118.2437
    val staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?" +
            "center=37.0902,-95.7129" + // Center of the map
            "&zoom=3" + // Zoom level
            "&size=400x400" + // Image dimensions
            "&maptype=roadmap" +
            "&path=color:0xff0000ff|weight:5|${pathCoordinates}" + // Red Polyline
            "&key=${apiKey}" // Your Google Maps API Key

    // 3. Load the image using Coil
    val painter = rememberAsyncImagePainter(model = staticMapUrl)

    Image(
        painter = painter,
        contentDescription = "Static map with polyline",
        modifier = Modifier.fillMaxSize()
    )
}