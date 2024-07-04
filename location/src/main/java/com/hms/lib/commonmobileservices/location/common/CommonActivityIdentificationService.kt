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
import com.google.android.gms.location.ActivityRecognition
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.Work
import com.huawei.hms.location.ActivityIdentificationService

/**
 * A service class for managing activity identification updates across different mobile service providers.
 *
 * This class provides methods for creating and deleting activity identification updates.
 */
class CommonActivityIdentificationService {

    /**
     * Creates activity conversion updates.
     *
     * @param context The context used for accessing resources and services.
     * @param activityConversionReq The request for activity conversion updates.
     * @param pendingIntent The pending intent to be triggered when activity conversion updates occur.
     * @return A [Work] object representing the asynchronous task of creating activity conversion updates.
     */
    @SuppressLint("MissingPermission")
    fun createActivityConversionUpdates(
        context: Context,
        activityConversionReq: CommonActivityConversionReq,
        pendingIntent: PendingIntent
    ): Work<Unit> {
        val worker: Work<Unit> = Work()
        when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> ActivityIdentificationService(context).createActivityConversionUpdates(
                activityConversionReq.toHMSActivityConversionReq(),
                pendingIntent
            )
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }

            else -> ActivityRecognition.getClient(context).requestActivityTransitionUpdates(
                activityConversionReq.toGMSActivityConversionReq(),
                pendingIntent
            )
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
        }
        return worker
    }

    /**
     * Creates activity identification updates.
     *
     * @param context The context used for accessing resources and services.
     * @param intervalMillis The time interval for activity identification updates in milliseconds.
     * @param pendingIntent The pending intent to be triggered when activity identification updates occur.
     * @return A [Work] object representing the asynchronous task of creating activity identification updates.
     */
    @SuppressLint("MissingPermission")
    fun createActivityIdentificationUpdates(
        context: Context,
        intervalMillis: Long,
        pendingIntent: PendingIntent
    ): Work<Unit> {
        val worker: Work<Unit> = Work()
        when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> ActivityIdentificationService(context).createActivityIdentificationUpdates(
                intervalMillis,
                pendingIntent
            )
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }

            else -> ActivityRecognition.getClient(context)
                .requestActivityUpdates(intervalMillis, pendingIntent)
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
        }
        return worker
    }

    /**
     * Deletes activity conversion updates.
     *
     * @param context The context used for accessing resources and services.
     * @param pendingIntent The pending intent associated with the activity conversion updates to be deleted.
     * @return A [Work] object representing the asynchronous task of deleting activity conversion updates.
     */
    @SuppressLint("MissingPermission")
    fun deleteActivityConversionUpdates(
        context: Context,
        pendingIntent: PendingIntent
    ): Work<Unit> {
        val worker: Work<Unit> = Work()
        when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> ActivityIdentificationService(context).deleteActivityConversionUpdates(
                pendingIntent
            )
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }

            else -> ActivityRecognition.getClient(context)
                .removeActivityTransitionUpdates(pendingIntent)
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
        }
        return worker
    }

    /**
     * Deletes activity identification updates.
     *
     * @param context The context used for accessing resources and services.
     * @param pendingIntent The pending intent associated with the activity identification updates to be deleted.
     * @return A [Work] object representing the asynchronous task of deleting activity identification updates.
     */
    @SuppressLint("MissingPermission")
    fun deleteActivityIdentificationUpdates(
        context: Context,
        pendingIntent: PendingIntent
    ): Work<Unit> {
        val worker: Work<Unit> = Work()
        when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> ActivityIdentificationService(context).deleteActivityIdentificationUpdates(
                pendingIntent
            )
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }

            else -> ActivityRecognition.getClient(context).removeActivityUpdates(pendingIntent)
                .addOnSuccessListener { worker.onSuccess(Unit) }
                .addOnFailureListener { worker.onFailure(it) }
        }
        return worker
    }
}