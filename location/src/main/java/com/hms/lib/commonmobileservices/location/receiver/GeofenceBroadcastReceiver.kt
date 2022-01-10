package com.hms.lib.commonmobileservices.location.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.hms.lib.commonmobileservices.location.CommonLocationClient
import com.hms.lib.commonmobileservices.location.common.CommonGeofenceData
import com.hms.lib.commonmobileservices.location.common.GeofencingData


class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!= null){
           val action = intent.action
           if(ACTION_PROCESS_LOCATION == action){
               val geoData =  (test as CommonGeofenceData).fetchDataFromIntent(intent)
               val errorCode = geoData.errorCode
               val conversion = geoData.conversion
               val list = geoData.convertingGeofenceList
               val mLocation = geoData.convertingLocation

               Toast.makeText(context,"Error code: $errorCode conversion: $conversion Geolist: $list Locations: $mLocation",Toast.LENGTH_LONG).show()

           }
       }

    }

    companion object {
        const val ACTION_PROCESS_LOCATION =
            "com.hms.lib.commonmobileservices.location.GeoFenceBroadcastReceiver.ACTION_PROCESS_LOCATION"
    }

}

object test{

}
