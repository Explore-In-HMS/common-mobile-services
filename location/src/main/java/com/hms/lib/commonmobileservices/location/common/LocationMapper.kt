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

import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest
import com.huawei.hms.location.ActivityConversionInfo
import com.huawei.hms.location.GeofenceData
import com.huawei.hms.location.GeofenceRequest

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


fun CommonActivityConversionInfo.toHMSCommonActivityConInfo() : com.huawei.hms.location.ActivityConversionInfo {
   return com.huawei.hms.location.ActivityConversionInfo(activityType,conversionType)
}

fun com.huawei.hms.location.ActivityConversionInfo.toCommonActivityConInfo() : CommonActivityConversionInfo {
    return CommonActivityConversionInfo(activityType, conversionType)
}


fun List<CommonActivityConversionInfo>.toHMSCommonActivityConversionInfos() : List<ActivityConversionInfo>{
    return CommonActivityConversionReq().activityConversions!!.map { it.toHMSCommonActivityConInfo() }
}

