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
package com.hms.lib.commonmobileservices.location.factory

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.lifecycle.Lifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.location.CommonLocationClient
import com.hms.lib.commonmobileservices.location.Constants.OPEN_LOCATION_SETTING_REQUEST_CODE
import com.hms.lib.commonmobileservices.location.model.CheckGpsEnabledResult
import com.hms.lib.commonmobileservices.location.model.CommonLocationResult
import com.hms.lib.commonmobileservices.location.model.LocationResultState
import com.hms.lib.commonmobileservices.location.model.Priority

/**
 * Google implementation of the common location client.
 * Provides access to location services provided by Google Play Services.
 *
 * @property activity The activity context.
 * @property lifecycle The lifecycle of the activity.
 * @property needBackgroundPermissions Flag indicating whether background permissions are needed.
 */
class GoogleLocationClientImpl(
    activity: Activity,
    lifecycle: Lifecycle,
    needBackgroundPermissions: Boolean = false
) : CommonLocationClient(activity, lifecycle, needBackgroundPermissions) {
    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    var locationCallback: LocationCallback? = null

    /**
     * Retrieves the last known location asynchronously.
     *
     * @param locationListener Callback to be invoked with the result.
     */
    @SuppressLint("MissingPermission")
    override fun getLastKnownLocationCore(locationListener: (commonLocationResult: CommonLocationResult) -> Unit) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            locationListener.invoke(CommonLocationResult(location))
        }.addOnFailureListener { err ->
            locationListener.invoke(
                CommonLocationResult(null, LocationResultState.FAIL, err)
            )
        }
    }

    /**
     * Requests location updates.
     *
     * @param priority The priority for location updates.
     * @param interval The interval for location updates.
     * @param locationListener Callback to be invoked with the result.
     */
    @SuppressLint("MissingPermission")
    override fun requestLocationUpdatesCore(
        priority: Priority?,
        interval: Long?,
        locationListener: (commonLocationResult: CommonLocationResult) -> Unit
    ) {
        val locationRequest = LocationRequest.create().apply {
            this.interval = interval ?: 100000
            this.priority = when (priority) {
                Priority.PRIORITY_BALANCED_POWER_ACCURACY -> LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                Priority.PRIORITY_HIGH_ACCURACY -> LocationRequest.PRIORITY_HIGH_ACCURACY
                Priority.PRIORITY_LOW_POWER -> LocationRequest.PRIORITY_LOW_POWER
                Priority.PRIORITY_NO_POWER -> LocationRequest.PRIORITY_NO_POWER
                else -> LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        }

        if (locationCallback == null) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationListener.invoke(CommonLocationResult(locationResult.lastLocation))
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                    super.onLocationAvailability(locationAvailability)
                    if (!locationAvailability.isLocationAvailable) {
                        if (!isLocationEnabled()) locationListener.invoke(
                            CommonLocationResult(
                                null,
                                LocationResultState.GPS_DISABLED, Exception("User disabled gps")
                            )
                        )
                        else locationListener.invoke(
                            CommonLocationResult(
                                null, LocationResultState.LOCATION_UNAVAILABLE,
                                Exception("location unavailable")
                            )
                        )
                    }
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback!!,
            Looper.getMainLooper()
        ).addOnFailureListener { err ->
            locationListener.invoke(
                CommonLocationResult(null, LocationResultState.FAIL, err)
            )
        }
    }

    /**
     * Removes location updates.
     */
    override fun removeLocationUpdates() {
        locationCallback?.let { callback ->
            fusedLocationProviderClient.removeLocationUpdates(callback)
                .addOnSuccessListener { locationCallback = null }
        }
    }

    /**
     * Checks if location settings are enabled.
     *
     * @param activity The activity context.
     * @param callback Callback to be invoked with the result.
     */
    override fun checkLocationSettings(
        activity: Activity,
        callback: (
            checkGpsEnabledResult: CheckGpsEnabledResult,
            error: Exception?
        ) -> Unit
    ) {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 100000
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            callback.invoke(CheckGpsEnabledResult.ENABLED, null)
        }.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(
                        activity,
                        OPEN_LOCATION_SETTING_REQUEST_CODE
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    callback.invoke(CheckGpsEnabledResult.ERROR, exception)
                }
            } else callback.invoke(CheckGpsEnabledResult.ERROR, exception)
        }
    }

    /**
     * Sets the mock mode for location services.
     *
     * @param isMockMode Flag indicating whether mock mode is enabled.
     * @return A work representing the asynchronous operation.
     */
    @SuppressLint("MissingPermission")
    override fun setMockMode(isMockMode: Boolean): Work<Unit> {
        val worker: Work<Unit> = Work()
        fusedLocationProviderClient.setMockMode(isMockMode)
            .addOnSuccessListener {
                worker.onSuccess(Unit)
            }.addOnFailureListener {
                worker.onFailure(it)
            }
        return worker
    }

    /**
     * Sets a mock location for location services.
     *
     * @param location The mock location to be set.
     * @return A work representing the asynchronous operation.
     */
    @SuppressLint("MissingPermission")
    override fun setMockLocation(location: Location): Work<Unit> {
        val worker: Work<Unit> = Work()
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.latitude = location.latitude
        mockLocation.longitude = location.longitude
        fusedLocationProviderClient.setMockLocation(mockLocation)
            .addOnSuccessListener {
                worker.onSuccess(Unit)
            }.addOnFailureListener {
                worker.onFailure(it)
            }
        return worker
    }

    /**
     * Flushes the pending locations.
     *
     * @return A work representing the asynchronous operation.
     */
    override fun flushLocations(): Work<Unit> {
        val worker: Work<Unit> = Work()
        fusedLocationProviderClient.flushLocations()
            .addOnSuccessListener {
                worker.onSuccess(Unit)
            }.addOnFailureListener {
                worker.onFailure(it)
            }
        return worker
    }
}