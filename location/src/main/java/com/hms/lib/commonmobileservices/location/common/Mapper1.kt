package com.hms.lib.commonmobileservices.location.common

import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest
import com.huawei.hms.location.ActivityConversionInfo
import com.huawei.hms.location.GeofenceData
import com.huawei.hms.location.GeofenceRequest

typealias HmsGeoReq = com.huawei.hms.location.GeofenceRequest
typealias GmsGeoReq = com.google.android.gms.location.GeofencingRequest
typealias HmsGeoReqBuilder = com.huawei.hms.location.GeofenceRequest.Builder
typealias GmsGeoReqBuilder = com.google.android.gms.location.GeofencingRequest.Builder

fun HmsGeoReq.toHMSGeofenceReq() : CommonGeofenceRequest = CommonGeofenceRequest(this)
fun GmsGeoReq.toGMSGeofenceReq() : CommonGeofenceRequest = CommonGeofenceRequest(this)
fun HmsGeoReqBuilder.toHMSGeofenceReqBuilder() : CommonGeofenceReqBuilder = CommonGeofenceReqBuilder(this)
fun GmsGeoReqBuilder.toGMSGeofenceReqBuilder() : CommonGeofenceReqBuilder = CommonGeofenceReqBuilder(this)



fun List<Geofence>.toHMSGeofenceList() : List<com.huawei.hms.location.Geofence>{
    return GeofenceRequestRes().geofenceList!!.map { it.toHMSGeofence() }
}

fun List<Geofence>.toGMSGeofenceList() : List<com.google.android.gms.location.Geofence>{
    return GeofenceRequestRes().geofenceList!!.map { it.toGMSGeofence() }
}

fun Geofence.toHMSGeofence() : com.huawei.hms.location.Geofence {
    return com.huawei.hms.location.Geofence(({ uniqueId }))
}

fun Geofence.toGMSGeofence() : com.google.android.gms.location.Geofence {
    return com.google.android.gms.location.Geofence(({ uniqueId }))

}

fun com.huawei.hms.location.Geofence.toCommonGeofence() : Geofence {
    return Geofence(uniqueId)
}

fun com.google.android.gms.location.Geofence.toCommonGeofence() : Geofence {
    return Geofence(requestId)
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

fun GeofenceData.toCommonGeofenceData(): GeofencingData{
    return GeofencingData(errorCode,conversion,convertingGeofenceList.map { it.toCommonGeofence() },convertingLocation)
}

fun GeofencingEvent.toCommonGeofenceData(): GeofencingData{
    return GeofencingData(errorCode, geofenceTransition,triggeringGeofences.map { it.toCommonGeofence() },triggeringLocation)
}









