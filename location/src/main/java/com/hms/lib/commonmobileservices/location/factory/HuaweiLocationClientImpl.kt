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

import android.app.Activity
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.lifecycle.Lifecycle
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.location.CommonLocationClient
import com.hms.lib.commonmobileservices.location.Constants.OPEN_LOCATION_SETTING_REQUEST_CODE
import com.hms.lib.commonmobileservices.location.model.CheckGpsEnabledResult
import com.hms.lib.commonmobileservices.location.model.CommonLocationResult
import com.hms.lib.commonmobileservices.location.model.LocationResultState
import com.hms.lib.commonmobileservices.location.model.Priority
import com.huawei.hms.common.ApiException
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*

class HuaweiLocationClientImpl(
    activity: Activity,
    lifecycle: Lifecycle,
    needBackgroundPermissions: Boolean = false,
) : CommonLocationClient(activity, lifecycle, needBackgroundPermissions) {
    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    private var locationCallback: LocationCallback? = null

    override fun getLastKnownLocationCore(locationListener: (commonLocationResult: CommonLocationResult) -> Unit) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                locationListener.invoke(CommonLocationResult(location))
            } ?: run {
                locationListener.invoke(
                    CommonLocationResult(
                        null, LocationResultState.NO_LAST_LOCATION,
                        Exception("no last known location")
                    )
                )
            }
        }.addOnFailureListener { err ->
            locationListener.invoke(
                CommonLocationResult(null, LocationResultState.FAIL, err)
            )
        }
    }

    override fun requestLocationUpdatesCore(
        priority: Priority?,
        interval: Long?,
        locationListener: (commonLocationResult: CommonLocationResult) -> Unit
    ) {
        val locationRequest = LocationRequest()
        locationRequest.interval = interval ?: 100000
        locationRequest.priority = when (priority) {
            Priority.PRIORITY_BALANCED_POWER_ACCURACY -> LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            Priority.PRIORITY_HIGH_ACCURACY -> LocationRequest.PRIORITY_HIGH_ACCURACY
            Priority.PRIORITY_LOW_POWER -> LocationRequest.PRIORITY_LOW_POWER
            Priority.PRIORITY_NO_POWER -> LocationRequest.PRIORITY_NO_POWER
            else -> LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (locationCallback == null) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.let {
                        locationListener.invoke(CommonLocationResult(it.lastLocation))
                    } ?: run {
                        locationListener.invoke(
                            CommonLocationResult(
                                null, LocationResultState.FAIL,
                                Exception("location unavailable")
                            )
                        )
                    }
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback,
            Looper.getMainLooper()
        ).addOnFailureListener { err ->
            locationListener.invoke(
                CommonLocationResult(null, LocationResultState.FAIL, err)
            )
        }
    }

    override fun removeLocationUpdates() {
        locationCallback?.let {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                .addOnSuccessListener { locationCallback = null }
        }
    }

    override fun checkLocationSettings(
        activity: Activity,
        callback: (
            checkGpsEnabledResult: CheckGpsEnabledResult,
            error: Exception?
        ) -> Unit
    ) {
        val settingsClient = LocationServices.getSettingsClient(activity)
        val locationSettingRequest = LocationSettingsRequest.Builder().apply {
            addLocationRequest(LocationRequest())
        }.build()

        settingsClient.checkLocationSettings(locationSettingRequest).addOnSuccessListener {
            callback.invoke(CheckGpsEnabledResult.ENABLED, null)
        }.addOnFailureListener { exception ->
            (exception as? ApiException)?.let { apiException ->
                if (apiException.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    (apiException as ResolvableApiException).startResolutionForResult(
                        activity,
                        OPEN_LOCATION_SETTING_REQUEST_CODE
                    )
                } else {
                    callback.invoke(CheckGpsEnabledResult.ERROR, apiException)
                }
            } ?: run {
                callback.invoke(CheckGpsEnabledResult.ERROR, exception)
            }
        }
    }

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