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

import com.google.android.gms.location.*
import com.huawei.hms.location.*

/**
 * Extension function to convert a GeofenceRequestRes object to an HMS GeofenceRequest.
 *
 * @return The HMS GeofenceRequest object.
 */
fun GeofenceRequestRes.toHmsGeofenceReq(): GeofenceRequest {
    val builder = GeofenceRequest.Builder()
    geofenceList?.let { builder.createGeofenceList(it.map { it.toHMSGeofence() }) }
    initConversion?.let { builder.setInitConversions(it) }
    geofence?.let { builder.createGeofence(it.toHMSGeofence()) }
    return builder.build()
}

/**
 * Extension function to convert a GeofenceRequestRes object to a GMS GeofencingRequest.
 *
 * @return The GMS GeofencingRequest object.
 */
fun GeofenceRequestRes.toGmsGeofenceReq(): GeofencingRequest {
    val builder = GeofencingRequest.Builder()
    geofenceList?.let { builder.addGeofences(it.map { it.toGMSGeofence() }) }
    initConversion?.let { builder.setInitialTrigger(it) }
    geofence?.let { builder.addGeofence(it.toGMSGeofence()) }
    return builder.build()
}

/**
 * Extension function to convert a Geofence object to an HMS Geofence.
 *
 * @return The HMS Geofence object.
 */
fun Geofence.toHMSGeofence(): com.huawei.hms.location.Geofence {
    val builder = com.huawei.hms.location.Geofence.Builder()
    uniqueId?.let { builder.setUniqueId(it) }
    conversions?.let { builder.setConversions(it) }
    validDuration?.let { builder.setValidContinueTime(it) }
    latitude?.let {
        longitude?.let { it1 ->
            radius?.let { it2 ->
                builder.setRoundArea(it, it1, it2)
            }
        }
    }
    notificationInterval?.let { builder.setNotificationInterval(it) }
    dwellDelayTime?.let { builder.setDwellDelayTime(it) }
    return builder.build()
}

/**
 * Extension function to convert a Geofence object to a GMS Geofence.
 *
 * @return The GMS Geofence object.
 */
fun Geofence.toGMSGeofence(): com.google.android.gms.location.Geofence {
    val builder = com.google.android.gms.location.Geofence.Builder()
    uniqueId?.let { builder.setRequestId(it) }
    conversions?.let { builder.setTransitionTypes(it) }
    validDuration?.let { builder.setExpirationDuration(it) }
    latitude?.let {
        longitude?.let { it1 ->
            radius?.let { it2 ->
                builder.setCircularRegion(it, it1, it2)
            }
        }
    }
    notificationInterval?.let { builder.setNotificationResponsiveness(it) }
    dwellDelayTime?.let { builder.setLoiteringDelay(it) }
    return builder.build()
}

/**
 * Extension function to convert an HMS Geofence object to a common Geofence object.
 *
 * @return The common Geofence object.
 */
fun com.huawei.hms.location.Geofence.toCommonGeofence(): Geofence {
    return Geofence()
        .also { it.uniqueId = uniqueId }.also { it.conversions = 0 }.also { it.validDuration = 0 }
        .also { it.latitude = 0.0 }.also { it.longitude = 0.0 }.also { it.radius = 0.0f }
        .also { it.notificationInterval = 0 }.also { it.dwellDelayTime = -1 }

}

/**
 * Extension function to convert an HMS Geofence object to a common Geofence object.
 *
 * @return The common Geofence object.
 */
fun com.google.android.gms.location.Geofence.toCommonGeofence(): Geofence {
    return Geofence()
        .also { it.uniqueId = requestId }.also { it.conversions = 0 }.also { it.validDuration = 0 }
        .also { it.latitude = 0.0 }.also { it.longitude = 0.0 }.also { it.radius = 0.0f }
        .also { it.notificationInterval = 0 }.also { it.dwellDelayTime = -1 }
}

/**
 * Extension function to convert a GeofenceData object to a common GeofencingData object.
 *
 * @return The common GeofencingData object.
 */
fun GeofenceData.toCommonGeofenceData(): GeofencingData {
    return GeofencingData(
        errorCode,
        conversion,
        convertingGeofenceList.map { it.toCommonGeofence() },
        convertingLocation,
        isFailure
    )
}

/**
 * Extension function to convert a GeofencingEvent object to a common GeofencingData object.
 *
 * @return The common GeofencingData object.
 */
fun GeofencingEvent.toCommonGeofenceData(): GeofencingData? {
    return triggeringGeofences?.let { geofences ->
        triggeringLocation?.let { location ->
            GeofencingData(
                errorCode, geofenceTransition,
                geofences.map { it.toCommonGeofence() }, location, hasError()
            )
        }
    }
}


//ActivityRecognition Mapper
/**
 * Extension function to convert a CommonActivityConversionReq object to an HMS ActivityConversionRequest.
 *
 * @return The HMS ActivityConversionRequest object.
 */
fun CommonActivityConversionReq.toHMSActivityConversionReq(): ActivityConversionRequest {
    return ActivityConversionRequest(
        activityConversions!!.let { conversions ->
            conversions.map { conversion ->
                conversion.toHMSActivityConversionInfo()
            }
        }
    )
}

/**
 * Extension function to convert a CommonActivityConversionReq object to a GMS ActivityTransitionRequest.
 *
 * @return The GMS ActivityTransitionRequest object.
 */
fun CommonActivityConversionReq.toGMSActivityConversionReq(): ActivityTransitionRequest {
    return ActivityTransitionRequest(
        activityConversions!!.let { conversions ->
            conversions.map { conversion ->
                conversion.toGMSActivityConversionInfo()
            }
        }
    )
}

/**
 * Extension function to convert a CommonActivityConversionInfo object to an HMS ActivityConversionInfo.
 *
 * @return The HMS ActivityConversionInfo object.
 */
fun CommonActivityConversionInfo.toHMSActivityConversionInfo(): ActivityConversionInfo {
    val activityConversionInfo = ActivityConversionInfo.Builder()
    activityType?.let { activityConversionInfo.setActivityType(it) }
    conversionType?.let { activityConversionInfo.setConversionType(it) }
    return activityConversionInfo.build()

}

/**
 * Extension function to convert a CommonActivityConversionInfo object to a GMS ActivityTransition.
 *
 * @return The GMS ActivityTransition object.
 */
fun CommonActivityConversionInfo.toGMSActivityConversionInfo(): ActivityTransition {
    val activityConversionInfo = ActivityTransition.Builder()
    activityType?.let { activityConversionInfo.setActivityType(it) }
    conversionType?.let { activityConversionInfo.setActivityTransition(it) }
    return activityConversionInfo.build()
}

/**
 * Extension function to convert an ActivityConversionResponse object to a CommonActivityConversionResponse.
 *
 * @return The CommonActivityConversionResponse object.
 */
fun ActivityConversionResponse.toCommonActivityConversionResponse(): CommonActivityConversionResponse {
    return CommonActivityConversionResponse().also { commonResponse ->
        commonResponse.getActivityConversionDataList =
            activityConversionDatas.map { conversionData ->
                conversionData.toCommonConversionData()
            }
    }
}

/**
 * Extension function to convert an ActivityTransitionResult object to a CommonActivityConversionResponse.
 *
 * @return The CommonActivityConversionResponse object.
 */
fun ActivityTransitionResult.toCommonActivityConversionResponse(): CommonActivityConversionResponse {
    return CommonActivityConversionResponse().also { commonResponse ->
        commonResponse.getActivityConversionDataList = transitionEvents.map { event ->
            event.toCommonConversionData()
        }
    }
}

/**
 * Extension function to convert an ActivityConversionData object to a CommonActivityConversionData.
 *
 * @return The CommonActivityConversionData object.
 */
fun ActivityConversionData.toCommonConversionData(): CommonActivityConversionData {
    return CommonActivityConversionData().also { it.getActivityType = activityType }
        .also { it.getConversionType = conversionType }
        .also { it.getElapsedTimeFromReboot = elapsedTimeFromReboot }
}

/**
 * Extension function to convert an ActivityTransitionEvent object to a CommonActivityConversionData.
 *
 * @return The CommonActivityConversionData object.
 */
fun ActivityTransitionEvent.toCommonConversionData(): CommonActivityConversionData {
    return CommonActivityConversionData().also { it.getActivityType = activityType }
        .also { it.getConversionType = transitionType }
        .also { it.getElapsedTimeFromReboot = elapsedRealTimeNanos }
}

/**
 * Extension function to convert an ActivityIdentificationData object to a CommonActivityIdentificationData.
 *
 * @return The CommonActivityIdentificationData object.
 */
fun ActivityIdentificationData.toCommonActivityIdentificationData(): CommonActivityIdentificationData {
    return CommonActivityIdentificationData().also { it.possibility = possibility }
        .also { it.identificationActivity = identificationActivity }
}

/**
 * Extension function to convert a DetectedActivity object to a CommonActivityIdentificationData.
 *
 * @return The CommonActivityIdentificationData object.
 */
fun DetectedActivity.toCommonActivityIdentificationData(): CommonActivityIdentificationData {
    return CommonActivityIdentificationData().also { it.possibility = confidence }
        .also { it.identificationActivity = type }
}

/**
 * Extension function to convert an ActivityIdentificationResponse object to a CommonActivityIdentificationResponse.
 *
 * @return The CommonActivityIdentificationResponse object.
 */
fun ActivityIdentificationResponse.toCommonActivityIdentificationResponse(): CommonActivityIdentificationResponse {
    return CommonActivityIdentificationResponse().also {
        it.activityIdentificationDataList =
            activityIdentificationDatas.map { it.toCommonActivityIdentificationData() }
    }
        .also {
            it.mostActivityIdentification =
                mostActivityIdentification.toCommonActivityIdentificationData()
        }.also { it.time = time }.also { it.elapsedTimeFromReboot = elapsedTimeFromReboot }
}

/**
 * Extension function to convert an ActivityRecognitionResult object to a CommonActivityIdentificationResponse.
 *
 * @return The CommonActivityIdentificationResponse object.
 */
fun ActivityRecognitionResult.toCommonActivityIdentificationResponse(): CommonActivityIdentificationResponse {
    return CommonActivityIdentificationResponse().also {
        it.activityIdentificationDataList =
            probableActivities.map { it.toCommonActivityIdentificationData() }
    }
        .also {
            it.mostActivityIdentification =
                mostProbableActivity.toCommonActivityIdentificationData()
        }.also { it.time = time }.also { it.elapsedTimeFromReboot = elapsedRealtimeMillis }
}



