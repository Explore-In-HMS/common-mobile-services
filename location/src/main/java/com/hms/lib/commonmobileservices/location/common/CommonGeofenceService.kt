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
package com.hms.lib.commonmobileservices.location.common

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.Work
import com.huawei.hms.location.GeofenceService


class CommonGeofenceService {

    @SuppressLint("MissingPermission")
    fun createGeofenceList(
        context: Context,
        geofenceReq: GeofenceRequestRes,
        pendingIntent: PendingIntent
    ): Work<Unit> {
        val worker: Work<Unit> = Work()
        when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> GeofenceService(context).createGeofenceList(
                geofenceReq.toHmsGeofenceReq(),
                pendingIntent
            )
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
            else -> LocationServices.getGeofencingClient(context)
                .addGeofences(geofenceReq.toGmsGeofenceReq(), pendingIntent)
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
        }
        return worker
    }

    fun deleteGeofenceList(context: Context, geofenceList: List<String>): Work<Unit> {
        val worker: Work<Unit> = Work()
        when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> GeofenceService(context).deleteGeofenceList(geofenceList)
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
            else -> LocationServices.getGeofencingClient(context).removeGeofences(geofenceList)
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
        }
        return worker
    }

    fun deleteGeofenceList(context: Context, pendingIntent: PendingIntent): Work<Unit> {
        val worker: Work<Unit> = Work()
        when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> GeofenceService(context).deleteGeofenceList(pendingIntent)
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
            else -> LocationServices.getGeofencingClient(context).removeGeofences(pendingIntent)
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
        }
        return worker
    }
}
