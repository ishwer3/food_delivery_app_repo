# Delivery Tracking Setup Guide

This guide explains how to set up the Google Maps delivery tracking feature in your food delivery app.

## Required Dependencies

Add these dependencies to your `app/build.gradle` file:

```kotlin
dependencies {
    // Google Maps Compose
    implementation 'com.google.maps.android:maps-compose:4.3.3'
    implementation 'com.google.android.gms:play-services-maps:18.2.0'
    implementation 'com.google.android.gms:play-services-location:21.0.1'

    // Permissions handling
    implementation 'com.google.accompanist:accompanist-permissions:0.32.0'

    // Existing dependencies you already have
    implementation 'androidx.navigation:navigation-compose:2.7.6'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0'
}
```

## Permissions Setup

Add these permissions to your `AndroidManifest.xml`:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Location permissions -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

    <!-- Internet permission for maps -->
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:theme="@style/Theme.FoodDeliveryApp">

        <!-- Google Maps API Key -->
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="YOUR_GOOGLE_MAPS_API_KEY_HERE" />

        <!-- Your activities -->
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

## Google Maps API Key Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the following APIs:
   - Maps SDK for Android
   - Directions API (for route calculations)
   - Places API (optional, for address autocomplete)
4. Create credentials → API Key
5. Restrict the API key to Android apps
6. Add your app's package name and SHA-1 certificate fingerprint
7. Replace `YOUR_GOOGLE_MAPS_API_KEY_HERE` in AndroidManifest.xml with your actual API key

## Features Implemented

### 🗺️ DeliveryTrackingScreen
- **Full-screen Google Maps** with real-time tracking
- **Dual markers**: Driver location (green) and delivery destination (red)
- **Animated polyline route** in orange color
- **Real-time simulation** of driver movement along the route
- **Smart camera positioning** to show both markers

### 📱 Interactive Bottom Sheet
- **Draggable/collapsible** Material 3 bottom sheet
- **Driver profile** with name, rating, and vehicle type
- **Live delivery status** with estimated time
- **Call driver button** for direct communication
- **Delivery address** display
- **Live tracking indicator** with pulse animation

### 🎯 Navigation Flow
1. **Cart Screen** → "Buy Now" button
2. **Payment Screen** → Complete payment
3. **DeliveryTrackingScreen** → Real-time tracking

### 🔐 Permission Handling
- **Automatic location permission** request
- **Graceful fallback** if permissions denied
- **Runtime permission** management with Accompanist

## Usage

### Basic Implementation

The DeliveryTrackingScreen is already integrated into your navigation system:

```kotlin
// Navigation from Cart to Payment to Tracking
composable("cart") {
    CartScreen(
        onBuyNowClick = { navController.navigate("payment") }
    )
}

composable("payment") {
    PaymentCardScreen(
        onPaymentSuccess = { navController.navigate("delivery_tracking") }
    )
}

composable("delivery_tracking") {
    DeliveryTrackingScreen(
        onBackClick = { navController.navigateUp() },
        onCallDriver = { phoneNumber -> /* Handle call */ }
    )
}
```

### Customization Options

#### Update Mock Data
Modify the delivery information in `MockDeliveryData.kt`:

```kotlin
fun getSampleDeliveryOrder(): DeliveryOrder {
    return DeliveryOrder(
        orderId = "YOUR_ORDER_ID",
        estimatedDeliveryTimeMinutes = 15,
        deliveryLocation = LatLng(YOUR_LAT, YOUR_LNG),
        // ... other properties
    )
}
```

#### Custom Route Points
Update the mock route for polyline:

```kotlin
fun getMockRoute(): List<LatLng> {
    return listOf(
        LatLng(startLat, startLng),
        LatLng(waypoint1Lat, waypoint1Lng),
        // ... more waypoints
        LatLng(endLat, endLng)
    )
}
```

#### Styling Options
- **Polyline color**: Change in `DeliveryTrackingScreen.kt`
- **Marker icons**: Customize using `BitmapDescriptorFactory`
- **Bottom sheet design**: Modify in `DeliveryInfoBottomSheet`
- **App colors**: Update theme colors to match your brand

## Color Scheme

The implementation uses your app's color scheme:
- **Primary orange**: `Color(0xFFFF9800)` for routes and accents
- **Success green**: `Color(0xFF4CAF50)` for driver markers and status
- **Background**: Clean whites and light grays
- **Cards**: Material 3 elevated surfaces

## Real-Time Features

### Simulated Movement
- Driver location updates every 3 seconds
- Smooth camera transitions following the route
- Progress simulation along the predefined route

### For Production
Replace the simulation with real data:
1. **WebSocket connection** for real-time updates
2. **Firebase Realtime Database** for driver locations
3. **REST API calls** to your backend
4. **Google Directions API** for dynamic routing

## Troubleshooting

### Common Issues

1. **Maps not loading**
   - Check API key is correct
   - Ensure Maps SDK is enabled
   - Verify package name and SHA-1 fingerprint

2. **Location permission issues**
   - Check AndroidManifest.xml permissions
   - Test on physical device (emulator may have issues)

3. **Build errors**
   - Ensure all dependencies are added
   - Check Gradle sync
   - Verify minimum SDK version (API 21+)

### Performance Tips

- **Limit location updates** to prevent battery drain
- **Cache map tiles** for offline viewing
- **Optimize marker updates** to reduce redraws
- **Use appropriate zoom levels** for best UX

## Next Steps

1. **Integrate with real backend** for live tracking
2. **Add push notifications** for delivery updates
3. **Implement route optimization** with Google Directions API
4. **Add offline support** for poor network conditions
5. **Include delivery photos** and signature capture

## Dependencies Summary

```kotlin
// Core dependencies needed
implementation 'com.google.maps.android:maps-compose:4.3.3'
implementation 'com.google.android.gms:play-services-maps:18.2.0'
implementation 'com.google.accompanist:accompanist-permissions:0.32.0'
```

Your delivery tracking feature is now ready to use! 🚀