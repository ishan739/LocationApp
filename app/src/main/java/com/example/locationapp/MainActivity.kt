package com.example.locationapp

import android.content.Context
import android.os.Bundle
import android.Manifest.permission
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme
import com.example.locationapp.ui.theme.LocationUtils
import com.example.locationapp.ui.theme.LocationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : LocationViewModel = viewModel()
            LocationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: LocationViewModel){
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    LocationDisplay(locationUtils, viewModel ,context)
}



@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context
) {

    val location = viewModel.location.value

    val address = location?.let { locationUtils.reverseGeocode(it) }

    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val coarseLocationGranted = permissions[permission.ACCESS_COARSE_LOCATION] == true
            val fineLocationGranted = permissions[permission.ACCESS_FINE_LOCATION] == true

            if (coarseLocationGranted && fineLocationGranted) {
                // I have access to location
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                // At least one permission denied
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    permission.ACCESS_COARSE_LOCATION
                )

                if(rationaleRequired){
                    Toast.makeText(context, "Location Permission Required", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Location Permission is required. Please" +
                            " enable from settings.", Toast.LENGTH_LONG).show()
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (location != null) {
            Text(text = "Address: ${location.latitude} , ${location.longitude} \n $address")
        } else {
            Text(text = "Location Not Available")
        }

        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                // Permission already granted
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                // Request permission
                requestPermissionsLauncher.launch(
                    arrayOf(
                        permission.ACCESS_COARSE_LOCATION,
                        permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "Request Permission")
        }
    }
}
