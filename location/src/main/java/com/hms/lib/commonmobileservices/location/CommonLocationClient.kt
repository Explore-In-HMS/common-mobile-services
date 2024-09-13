// Copyright 2020. Explore in HMS. All rights reserved.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

// http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.hms.lib.commonmobileservices.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.location.model.*

/**
 * Abstract class representing a common location client for accessing location-related services.
 */
abstract class CommonLocationClient(
    private val activity: Activity,
    lifecycle: Lifecycle,
    private val needBackgroundPermissions: Boolean
) {

    var preferredUnsubscribeEvent = Lifecycle.Event.ON_DESTROY

    init {
        lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun pause() {
                if (preferredUnsubscribeEvent == Lifecycle.Event.ON_PAUSE)
                    removeLocationUpdates()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun stop() {
                if (preferredUnsubscribeEvent == Lifecycle.Event.ON_STOP)
                    removeLocationUpdates()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun destroy() {
                if (preferredUnsubscribeEvent == Lifecycle.Event.ON_DESTROY)
                    removeLocationUpdates()
            }
        })
    }

    private var enableGpsCallback: ((enableGPSFinalResult: EnableGPSFinalResult, error: Exception?) -> Unit)? =
        null

    /**
     * Enables GPS location services.
     * @param callback Callback to be invoked with the result of enabling GPS.
     */
    fun enableGps(
        callback: (
            enableGPSFinalResult: EnableGPSFinalResult,
            error: Exception?
        ) -> Unit
    ) {
        if (hasLocationPermission(activity)) {
            enableGpsCallback = callback
            checkLocationSettings(activity) { checkGpsEnabledResult, error ->
                when (checkGpsEnabledResult) {
                    CheckGpsEnabledResult.ENABLED -> callback.invoke(
                        EnableGPSFinalResult.ENABLED,
                        null
                    )

                    CheckGpsEnabledResult.ERROR -> callback.invoke(
                        EnableGPSFinalResult.FAILED,
                        error
                    )
                }
            }
        } else {
            enableGpsCallback = null
            callback.invoke(
                EnableGPSFinalResult.FAILED,
                Exception("No permissions")
            )
        }
    }

    /**
     * Abstract method to check the location settings.
     * @param activity The activity context.
     * @param callback Callback to be invoked with the result of the location settings check.
     */
    abstract fun checkLocationSettings(
        activity: Activity, callback: (
            checkGpsEnabledResult: CheckGpsEnabledResult,
            error: Exception?
        ) -> Unit
    )

    /**
     * Handles the result of the resolution request for enabling GPS.
     * @param resultCode The result code of the resolution request.
     */
    fun handleResolutionResult(resultCode: Int) {
        when (resultCode) {
            Activity.RESULT_OK ->             // All required changes were successfully made
                enableGpsCallback?.invoke(EnableGPSFinalResult.ENABLED, null)

            Activity.RESULT_CANCELED ->             // The user was asked to change settings, but chose not to
                enableGpsCallback?.invoke(EnableGPSFinalResult.USER_CANCELLED, null)
        }
    }

    /**
     * Retrieves the last known location.
     * @param locationListener Callback to be invoked with the result of the last known location retrieval.
     */
    fun getLastKnownLocation(locationListener: (CommonLocationResult) -> Unit) {
        if (hasLocationPermission(activity)) {
            getLastKnownLocationCore(locationListener)
        } else {
            ActivityCompat.requestPermissions(activity, getLocationPermissions().toTypedArray(), 2)
        }
    }

    /**
     * Abstract method to retrieve the last known location.
     * @param locationListener Callback to be invoked with the result of the last known location retrieval.
     */
    abstract fun getLastKnownLocationCore(locationListener: (CommonLocationResult) -> Unit)

    /**
     * Requests location updates.
     * @param priority The priority of the location request.
     * @param interval The interval for location updates.
     * @param locationListener Callback to be invoked with each location update.
     */
    fun requestLocationUpdates(
        priority: Priority? = Priority.PRIORITY_BALANCED_POWER_ACCURACY,
        interval: Long? = 10000,
        locationListener: (CommonLocationResult) -> Unit
    ) {
        if (hasLocationPermission(activity)) {
            requestLocationUpdatesCore(priority, interval, locationListener)
        } else {
            locationListener.invoke(
                CommonLocationResult(
                    null,
                    LocationResultState.LOCATION_UNAVAILABLE, Exception("No location permission")
                )
            )
        }
    }

    /**
     * Abstract method to request location updates.
     * @param priority The priority of the location request.
     * @param interval The interval for location updates.
     * @param locationListener Callback to be invoked with each location update.
     */
    abstract fun requestLocationUpdatesCore(
        priority: Priority? = Priority.PRIORITY_BALANCED_POWER_ACCURACY,
        interval: Long? = 10000,
        locationListener: (CommonLocationResult) -> Unit
    )

    /**
     * Removes location updates.
     */
    abstract fun removeLocationUpdates()

    /**
     * Retrieves the location permissions required by the client.
     * @return The list of location permissions required.
     */
    protected fun getLocationPermissions(): MutableList<String> {
        val perms = mutableListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P && needBackgroundPermissions)
            perms.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        return perms
    }

    /**
     * Checks if location services are enabled on the device.
     * @return True if location services are enabled, false otherwise.
     */
    fun isLocationEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.isLocationEnabled
        } else {
            val mode = Settings.Secure.getInt(
                activity.contentResolver, Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            )
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    /**
     * Checks if the required location permissions are granted.
     * @param activity The activity context.
     * @return True if the required permissions are granted, false otherwise.
     */
    private fun hasLocationPermission(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED) ||
                    ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        } else {
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Abstract method to set the mock mode for the location client.
     * @param isMockMode Boolean indicating whether mock mode should be enabled or disabled.
     * @return A [Work] object representing the asynchronous work to set the mock mode.
     */
    abstract fun setMockMode(isMockMode: Boolean): Work<Unit>

    /**
     * Abstract method to set a mock location for the location client.
     * @param location The mock location to be set.
     * @return A [Work] object representing the asynchronous work to set the mock location.
     */
    abstract fun setMockLocation(location: Location): Work<Unit>

    /**
     * Abstract method to flush any pending location updates.
     * @return A [Work] object representing the asynchronous work to flush locations.
     */
    abstract fun flushLocations(): Work<Unit>
}