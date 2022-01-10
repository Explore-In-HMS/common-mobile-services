package com.hms.lib.commonmobileservices.location.common



class CommonGeofenceRequest(private val geofenceReqImpl: Any) {

    fun getGeofences():List<Geofence>{
        return when(geofenceReqImpl){
            is HmsGeoReq ->  geofenceReqImpl.geofences.map { it.toCommonGeofence() }
            else-> (geofenceReqImpl as GmsGeoReq).geofences.map { it.toCommonGeofence() }
        }
    }

    fun getInitConversions():Int{
        return when(geofenceReqImpl){
            is HmsGeoReq -> geofenceReqImpl.initConversions
            else -> (geofenceReqImpl as GmsGeoReq).initialTrigger
        }
    }



}