

# <u>LocationApp </u>

LocationApp is an Android application built with Kotlin and Jetpack Compose, designed to provide users with real-time location tracking and address details. It leverages Google’s Fused Location Provider for accurate location updates and reverse-geocodes coordinates into human-readable addresses using the Geocoder API.

**<u>Features</u>**

**1. Real-Time Location Tracking**
Fetches the user's current location with high accuracy using Google’s Fused Location Provider.
Updates location in real-time based on user interaction or movement.

**2. Reverse Geocoding**
Converts latitude and longitude into detailed addresses.
Displays location coordinates and their corresponding address to the user.

**3. Permission Management**
Dynamically handles runtime permissions for location access.
Provides user-friendly prompts when permissions are denied or require manual enabling.

**4. Modern UI with Jetpack Compose**
Fully responsive and intuitive UI built using Jetpack Compose.
Organized and clean interface for seamless user interaction.

**<u>Technology Stack</u>**

Programming Language: Kotlin

UI Framework: Jetpack Compose

Location Services: Google Fused Location Provider API

Reverse Geocoding: Geocoder API

Architecture: MVVM (Model-View-ViewModel)

**<u>Code Structure</u>**

**1. MainActivity.kt**

Entry point of the app, sets up the UI and integrates the LocationViewModel with the Compose theme.

**2. LocationUtils.kt**

Handles location-related operations, such as requesting location updates and performing reverse geocoding.

Manages permission checks and interactions with the Fused Location Provider API.

**3. LocationViewModel.kt**

Manages the app's state for location data using Jetpack Compose’s state management.

Ensures reactive UI updates when location data changes.

**4. LocationData.kt**

Data class representing the user's location with latitude and longitude fields.

**<u>How It Works</u>**

The app checks for location permissions upon user interaction.

If permissions are granted, it fetches the current location using the Fused Location Provider.

The latitude and longitude are displayed and reverse-geocoded into an address.

If permissions are denied, the app provides helpful prompts for enabling them.

**<u>Installation</u>**

Clone the repository:

Copy code : <u>git clone https://github.com/your-repo/LocationApp.git</u>

Open the project in Android Studio.

Sync Gradle and run the app on an emulator or physical device.

**<u>Future Enhancements</u>**

Add support for background location tracking.

Implement a map-based UI using Google Maps API.

Include a history of visited locations.
