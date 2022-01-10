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
package com.hms.lib.commonmobileservices.location

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.location.common.CommonGeofenceReqBuilder
import com.hms.lib.commonmobileservices.location.common.CommonGeofenceRequest
import com.hms.lib.commonmobileservices.location.common.Geofence
import com.hms.lib.commonmobileservices.location.common.GeofencingData
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import com.hms.lib.commonmobileservices.location.model.CommonLocationResult
import com.hms.lib.commonmobileservices.location.model.CheckGpsEnabledResult
import com.hms.lib.commonmobileservices.location.model.EnableGPSFinalResult
import com.hms.lib.commonmobileservices.location.model.Priority

abstract class CommonLocationClient(private val activity: Activity,
                                    lifecycle: Lifecycle,
                                    private val needBackgroundPermissions: Boolean){

    var preferredUnsubscribeEvent=Lifecycle.Event.ON_DESTROY

    init {
        lifecycle.addObserver(object : LifecycleObserver{

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun pause(){
                if(preferredUnsubscribeEvent==Lifecycle.Event.ON_PAUSE)
                    removeLocationUpdates()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun stop(){
                if(preferredUnsubscribeEvent==Lifecycle.Event.ON_STOP)
                    removeLocationUpdates()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun destroy(){
                if(preferredUnsubscribeEvent==Lifecycle.Event.ON_DESTROY)
                    removeLocationUpdates()
            }
        })
    }

    var handlePermanentlyDeniedBlock: ((QuickPermissionsRequest) -> Unit)? = null
    var permissionsDeniedBlock: ((QuickPermissionsRequest) -> Unit)? = null

    var options = QuickPermissionsOptions().apply {
        handleRationale=false
        handlePermanentlyDenied=true
        permanentDeniedMethod = {
            handlePermanentlyDeniedBlock?.invoke(it)
        }
        permissionsDeniedMethod ={
            permissionsDeniedBlock?.invoke(it)
        }
    }

    private var enableGpsCallback : ((enableGPSFinalResult: EnableGPSFinalResult, error: Exception?) -> Unit)? = null

    fun enableGps(callback : (enableGPSFinalResult: EnableGPSFinalResult,
                              error: Exception?) -> Unit){

        activity.runWithPermissions(*getLocationPermissions(),options = options){
            enableGpsCallback=callback
            checkLocationSettings(activity){checkGpsEnabledResult, error ->
                when(checkGpsEnabledResult){
                    CheckGpsEnabledResult.ENABLED-> callback.invoke(EnableGPSFinalResult.ENABLED,null)
                    CheckGpsEnabledResult.ERROR -> callback.invoke(EnableGPSFinalResult.FAILED,error)
                }
            }
        }
    }

    abstract fun checkLocationSettings(activity: Activity,callback: (checkGpsEnabledResult: CheckGpsEnabledResult,
                                                                     error: Exception?) -> Unit)

    fun handleResolutionResult(resultCode: Int){
        when(resultCode){
            Activity.RESULT_OK ->             // All required changes were successfully made
                enableGpsCallback?.invoke(EnableGPSFinalResult.ENABLED,null)
            Activity.RESULT_CANCELED ->             // The user was asked to change settings, but chose not to
                enableGpsCallback?.invoke(EnableGPSFinalResult.USER_CANCELLED,null)
        }
    }

    fun getLastKnownLocation(locationListener: (CommonLocationResult) -> Unit) =
        activity.runWithPermissions(*getLocationPermissions(),options = options){
            getLastKnownLocationCore(locationListener)
        }


    abstract fun getLastKnownLocationCore(locationListener: (CommonLocationResult) -> Unit)

    fun requestLocationUpdates(
        priority: Priority?= Priority.PRIORITY_BALANCED_POWER_ACCURACY,
        interval: Long?=10000,
        locationListener: (CommonLocationResult) -> Unit
    ) = activity.runWithPermissions(*getLocationPermissions(),options = options){
        requestLocationUpdatesCore(priority,interval,locationListener)
    }

    abstract fun requestLocationUpdatesCore(priority: Priority?= Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                                            interval: Long?=10000,
                                            locationListener: (CommonLocationResult) -> Unit)

    abstract fun removeLocationUpdates()

    protected fun getLocationPermissions() : Array<String>{
        val perms= mutableListOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P && needBackgroundPermissions)
            perms.add("android.permission.ACCESS_BACKGROUND_LOCATION")
        return perms.toTypedArray()
    }

    fun isLocationEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.isLocationEnabled
        } else {
            val mode = Settings.Secure.getInt(
                activity.contentResolver, Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            )
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    abstract fun setMockMode(isMockMode : Boolean) : Work<Unit>
    abstract fun setMockLocation(location: Location): Work<Unit>
    abstract fun flushLocations(): Work<Unit>
    abstract fun geofenceBuild() : Geofence
    abstract fun setCircularArea(latitude:Double,longitude:Double,radius:Float)
    abstract fun setExpirationDuration(expirationDuration : Long)
    abstract fun setDwellDelayTime(dwellDelayTime: Int)
    abstract fun setNotificationInterval(notificationInterval: Int)
    abstract fun setReqId(reqId: String)
    abstract fun setTriggerType(triggerType: Int)
    abstract fun createGeofenceList(geofences: List<Geofence>) : CommonGeofenceReqBuilder
    abstract fun setInitConversions(conversionType:Int): CommonGeofenceReqBuilder
    abstract fun createGeofence(geofence: Geofence) : CommonGeofenceReqBuilder
    abstract fun deleteGeofenceList(reqIdList : List<String>): Work<Unit>
    abstract fun deleteGeofenceList(pendingIntent: PendingIntent): Work<Unit>
    abstract fun fetchDataFromIntent(intent : Intent): GeofencingData
    abstract fun getTriggeredGeofence() : List<Geofence>
    abstract fun fetchGeofenceList() : List<Geofence>
    abstract fun getConvertingLocation() : Location
    abstract fun getErrorCode() : Int
    abstract fun getConversion(): Int
    abstract fun geofenceReqBuild() : CommonGeofenceRequest

    abstract fun  deleteActivityConversionUpdates(pendingIntent: PendingIntent) : Work<Unit>
    abstract fun deleteActivityIdentificationUpdates(pendingIntent: PendingIntent) : Work<Unit>



    interface ResultCallback{
        fun addOnSuccessListener()
        fun addOnFailureListener(e: java.lang.Exception)
    }

}
