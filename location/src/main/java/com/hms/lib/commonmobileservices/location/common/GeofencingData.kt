package com.hms.lib.commonmobileservices.location.common

import android.content.Intent
import android.location.Location
import com.huawei.hms.location.GeofenceData

data class GeofencingData(
     val errorCode : Int,
     val conversion : Int,
     val convertingGeofenceList : List<Geofence>,
     val convertingLocation : Location
    )



