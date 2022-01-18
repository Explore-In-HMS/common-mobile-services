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

fun GeofenceRequestRes.toHmsGeofenceReq(): GeofenceRequest{
    val builder = GeofenceRequest.Builder()
    geofenceList?.let { builder.createGeofenceList(it.map { it.toHMSGeofence() }) }
    initConversion?.let { builder.setInitConversions(it) }
    geofence?.let { builder.createGeofence(it.toHMSGeofence())}
    return builder.build()
}

fun GeofenceRequestRes.toGmsGeofenceReq(): GeofencingRequest{
    val builder = GeofencingRequest.Builder()
    geofenceList?.let { builder.addGeofences(it.map { it.toGMSGeofence() }) }
    initConversion?.let { builder.setInitialTrigger(it) }
    geofence?.let { builder.addGeofence(it.toGMSGeofence())}
    return builder.build()
}


fun Geofence.toHMSGeofence() : com.huawei.hms.location.Geofence {
    val builder = com.huawei.hms.location.Geofence.Builder()
    uniqueId?.let { builder.setUniqueId(it) }
    conversions?.let { builder.setConversions(it) }
    validDuration?.let { builder.setValidContinueTime(it) }
    latitude?.let { longitude?.let { it1 -> radius?.let { it2 ->
        builder.setRoundArea(it, it1, it2)} } }
    notificationInterval?.let { builder.setNotificationInterval(it) }
    dwellDelayTime?.let { builder.setDwellDelayTime(it) }
    return builder.build()
}

fun Geofence.toGMSGeofence() : com.google.android.gms.location.Geofence{
    val builder = com.google.android.gms.location.Geofence.Builder()
    uniqueId?.let { builder.setRequestId(it) }
    conversions?.let { builder.setTransitionTypes(it) }
    validDuration?.let { builder.setExpirationDuration(it) }
    latitude?.let { longitude?.let { it1 -> radius?.let { it2 ->
        builder.setCircularRegion(it, it1, it2)} } }
    notificationInterval?.let { builder.setNotificationResponsiveness(it) }
    dwellDelayTime?.let { builder.setLoiteringDelay(it) }
    return builder.build()
}

fun com.huawei.hms.location.Geofence.toCommonGeofence(): Geofence{
    return Geofence()
        .also { it.uniqueId = uniqueId }.also { it.conversions = 0 }.also { it.validDuration=0 }
        .also { it.latitude=0.0 }.also { it.longitude= 0.0 }.also { it.radius=0.0f }
        .also { it.notificationInterval = 0 }.also { it.dwellDelayTime =-1 }

}

fun com.google.android.gms.location.Geofence.toCommonGeofence() : Geofence {
    return Geofence()
        .also { it.uniqueId = requestId }.also { it.conversions = 0 }.also { it.validDuration=0 }
        .also { it.latitude=0.0 }.also { it.longitude= 0.0 }.also { it.radius=0.0f }
        .also { it.notificationInterval = 0 }.also { it.dwellDelayTime =-1 }
}

fun GeofenceData.toCommonGeofenceData(): GeofencingData{
    return GeofencingData(errorCode,conversion,convertingGeofenceList.map { it.toCommonGeofence() },convertingLocation,isFailure)
}

fun GeofencingEvent.toCommonGeofenceData(): GeofencingData{
    return GeofencingData(errorCode, geofenceTransition,triggeringGeofences.map { it.toCommonGeofence() },triggeringLocation,hasError())
}


//ActivityRecognition Mapper

fun CommonActivityConversionReq.toHMSActivityConversionReq(): ActivityConversionRequest{
    return ActivityConversionRequest(activityConversions!!.let { it -> it.map { it.toHMSActivityConversionInfo() } })
}

fun CommonActivityConversionReq.toGMSActivityConversionReq(): ActivityTransitionRequest{
    return ActivityTransitionRequest(activityConversions!!.let { it -> it.map { it.toGMSActivityConversionInfo() } })
}

fun CommonActivityConversionInfo.toHMSActivityConversionInfo() : ActivityConversionInfo {
    val activityConversionInfo = ActivityConversionInfo.Builder()
    activityType?.let { activityConversionInfo.setActivityType(it) }
    conversionType?.let { activityConversionInfo.setConversionType(it) }
    return activityConversionInfo.build()

}

fun CommonActivityConversionInfo.toGMSActivityConversionInfo() : ActivityTransition {
    val activityConversionInfo = ActivityTransition.Builder()
    activityType?.let { activityConversionInfo.setActivityType(it) }
    conversionType?.let { activityConversionInfo.setActivityTransition(it) }
    return activityConversionInfo.build()
}

fun ActivityConversionInfo.toCommonActivityConversionInfo() : CommonActivityConversionInfo {
    return CommonActivityConversionInfo().also { it.activityType=activityType }.also { it.conversionType=conversionType }
}

fun ActivityTransition.toCommonActivityConversionInfo() : CommonActivityConversionInfo {
    return CommonActivityConversionInfo().also { it.activityType=activityType }.also { it.conversionType=transitionType }
}

fun ActivityConversionResponse.toCommonActivityConversionResponse(): CommonActivityConversionResponse{
    return CommonActivityConversionResponse().also { it -> it.getActivityConversionDataList = activityConversionDatas.map { it.toCommonConversionData() } }
}

fun ActivityTransitionResult.toCommonActivityConversionResponse(): CommonActivityConversionResponse{
    return CommonActivityConversionResponse().also { it -> it.getActivityConversionDataList = transitionEvents.map { it.toCommonConversionData() } }
}

fun ActivityConversionData.toCommonConversionData(): CommonActivityConversionData{
    return CommonActivityConversionData().also { it.getActivityType = activityType }.also { it.getConversionType= conversionType }.also { it.getElapsedTimeFromReboot = elapsedTimeFromReboot }
}

fun ActivityTransitionEvent.toCommonConversionData(): CommonActivityConversionData{
    return CommonActivityConversionData().also { it.getActivityType = activityType }.also { it.getConversionType= transitionType }.also { it.getElapsedTimeFromReboot = elapsedRealTimeNanos }
}

fun CommonActivityIdentificationData.toHmsActivityIdentificationData(): ActivityIdentificationData{
    return ActivityIdentificationData(possibility!!,identificationActivity!!)
}

fun CommonActivityIdentificationData.toGmsActivityIdentificationData(): DetectedActivity{
    return DetectedActivity(possibility!!,identificationActivity!!)
}

fun ActivityIdentificationData.toCommonActivityIdentificationData(): CommonActivityIdentificationData{
    return CommonActivityIdentificationData().also { it.possibility = possibility }.also { it.identificationActivity = identificationActivity }
}

fun DetectedActivity.toCommonActivityIdentificationData(): CommonActivityIdentificationData{
    return CommonActivityIdentificationData().also { it.possibility = confidence }.also { it.identificationActivity = type }
}

fun ActivityIdentificationResponse.toCommonActivityIdentificationResponse(): CommonActivityIdentificationResponse{
    return CommonActivityIdentificationResponse().also { it.activityIdentificationDataList = activityIdentificationDatas.map { it.toCommonActivityIdentificationData() } }
        .also { it.mostActivityIdentification = mostActivityIdentification.toCommonActivityIdentificationData() }.also { it.time = time }.also { it.elapsedTimeFromReboot = elapsedTimeFromReboot }
}

fun ActivityRecognitionResult.toCommonActivityIdentificationResponse(): CommonActivityIdentificationResponse{
    return CommonActivityIdentificationResponse().also { it.activityIdentificationDataList = probableActivities.map { it.toCommonActivityIdentificationData() } }
        .also { it.mostActivityIdentification = mostProbableActivity.toCommonActivityIdentificationData() }.also { it.time = time }.also { it.elapsedTimeFromReboot = elapsedRealtimeMillis }
}



