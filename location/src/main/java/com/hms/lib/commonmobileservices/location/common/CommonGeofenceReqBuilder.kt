package com.hms.lib.commonmobileservices.location.common

import com.google.android.gms.location.GeofencingRequest
import com.huawei.hms.location.GeofenceRequest

class CommonGeofenceReqBuilder(val commonGeofenceReqBuilder: Any) {

    fun createGeofenceList(geofences: List<Geofence>){
        when(commonGeofenceReqBuilder){
            is HmsGeoReqBuilder -> commonGeofenceReqBuilder.createGeofenceList(geofences.map { it.toHMSGeofence() })
            is GmsGeoReqBuilder -> commonGeofenceReqBuilder.addGeofences(geofences.map { it.toGMSGeofence() })
        }
    }

    fun createGeofence(geofence: Geofence){
        when(commonGeofenceReqBuilder){
            is HmsGeoReqBuilder -> commonGeofenceReqBuilder.createGeofence(geofence.toHMSGeofence())
            is GmsGeoReqBuilder -> commonGeofenceReqBuilder.addGeofence(geofence.toGMSGeofence())
        }
    }

    fun setInitConversion(conversion:Int){
        when(commonGeofenceReqBuilder){
            is HmsGeoReqBuilder -> commonGeofenceReqBuilder.setInitConversions(conversion)
            is GmsGeoReqBuilder -> commonGeofenceReqBuilder.setInitialTrigger(conversion)
        }
    }

    fun build(){
        when(commonGeofenceReqBuilder){
            is HmsGeoReqBuilder -> commonGeofenceReqBuilder.build()
            is GmsGeoReqBuilder -> commonGeofenceReqBuilder.build()
        }
    }

}

