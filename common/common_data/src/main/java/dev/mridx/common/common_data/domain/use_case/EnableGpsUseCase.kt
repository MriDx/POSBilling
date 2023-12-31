package dev.mridx.common.common_data.domain.use_case

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.mridx.common.common_data.data.local.model.gps_result.GpsResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class EnableGpsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun execute() = callbackFlow<GpsResult> {

        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //gps enabled
            trySend(element = GpsResult(enabled = true))
        }
        /*val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000 * 10 //in every 10 sec
            fastestInterval = 1000 * 2 // 2 sec

        }*/
        val settingsClient = LocationServices.getSettingsClient(context)
        settingsClient.checkLocationSettings(
            LocationSettingsRequest
                .Builder()
                .addLocationRequest(com.google.android.gms.location.LocationRequest.create())
                .setAlwaysShow(true)
                .build()
        )
            .addOnSuccessListener {
                trySend(element = GpsResult(enabled = true))
            }
            .addOnFailureListener {
                trySend(element = GpsResult(enabled = false, exception = it))
            }

        awaitClose { }

    }

}