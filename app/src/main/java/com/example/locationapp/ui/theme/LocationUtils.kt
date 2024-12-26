package com.example.locationapp.ui.theme

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import java.util.Locale
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng


class LocationUtils(val context: Context) {

    private val _fusedLocationClient: FusedLocationProviderClient
                    = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: LocationViewModel) {
        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { it ->
                    val location = LocationData(it.latitude, it.longitude)
                    viewModel.updateLocation(location)
                }
            }
        }
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY , 1000).build()

        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }


    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission.ACCESS_FINE_LOCATION
        ) == PackageManager. PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    context,
                    permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun reverseGeocode(location: LocationData) : String {
        val geocoder = Geocoder(context , Locale.getDefault())
        val coordinates = LatLng(location.latitude, location.longitude)
        val addresses : MutableList<Address>? = geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)
        return if(addresses?.isNotEmpty() == true){
           addresses[0].getAddressLine(0)
        } else {
            "Address Not Found"
        }
    }
}









