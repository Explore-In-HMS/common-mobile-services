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
package com.hms.lib.commonmobileservices.awareness.service.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.alperenbabagil.commonmobileservices.R
import com.google.android.gms.awareness.Awareness
import com.google.android.gms.awareness.fence.TimeFence
import com.google.android.gms.awareness.snapshot.TimeIntervalsResponse
import com.google.android.gms.awareness.state.HeadphoneState
import com.google.android.gms.awareness.state.Weather
import com.huawei.hms.kit.awareness.status.HeadsetStatus
import com.hms.lib.commonmobileservices.awareness.model.*
import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.core.ResultData

class GoogleAwarenessKit(var context: Context) : IAwarenessAPI {
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
    }

    override fun getTimeAwareness(callback: (timeVal: ResultData<IntArray>) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            callback.invoke(ResultData.Failed(context.getString(R.string.missing_permission)))
            return
        }
        Awareness.getSnapshotClient(context).timeIntervals.addOnSuccessListener { response ->
            callback.invoke(calculateTimeValue(response))
        }.addOnFailureListener {
            callback.invoke(ResultData.Failed(context.getString(R.string.gms_awareness_error)))
        }
    }

    private fun calculateTimeValue(timeIntervalsResponse: TimeIntervalsResponse) : ResultData<IntArray> {
        val timeValue:ArrayList<Int> = arrayListOf()
        for (index in 0..timeIntervalsResponse.timeIntervals.timeIntervals.size) {
            if (index == 0) {
                if (timeIntervalsResponse.timeIntervals.timeIntervals[0] == TimeFence.TIME_INSTANT_SUNRISE) {
                    timeValue += TimeDataValue.TIME_CATEGORY_MORNING.value
                } else if (timeIntervalsResponse.timeIntervals.timeIntervals[0] == TimeFence.TIME_INSTANT_SUNSET) {
                    timeValue += TimeDataValue.TIME_CATEGORY_NIGHT.value
                }
            } else if (index == 1) {
                when {
                    timeIntervalsResponse.timeIntervals.timeIntervals[1] == TimeFence.TIME_INTERVAL_WEEKDAY -> {
                        timeValue += TimeDataValue.TIME_CATEGORY_WEEKDAY.value
                    }
                    timeIntervalsResponse.timeIntervals.timeIntervals[1] == TimeFence.TIME_INTERVAL_WEEKEND -> {
                        timeValue += TimeDataValue.TIME_CATEGORY_WEEKEND.value
                    }
                    timeIntervalsResponse.timeIntervals.timeIntervals[1] == TimeFence.TIME_INTERVAL_HOLIDAY -> {
                        timeValue += TimeDataValue.TIME_CATEGORY_HOLIDAY.value
                    }
                    timeIntervalsResponse.timeIntervals.timeIntervals[1] == TimeFence.TIME_INTERVAL_MORNING -> {
                        timeValue += TimeDataValue.TIME_CATEGORY_MORNING.value
                    }
                    timeIntervalsResponse.timeIntervals.timeIntervals[1] == TimeFence.TIME_INTERVAL_AFTERNOON -> {
                        timeValue += TimeDataValue.TIME_CATEGORY_AFTERNOON.value
                    }
                    timeIntervalsResponse.timeIntervals.timeIntervals[1] == TimeFence.TIME_INTERVAL_EVENING -> {
                        timeValue += TimeDataValue.TIME_CATEGORY_EVENING.value
                    }
                    timeIntervalsResponse.timeIntervals.timeIntervals[1] == TimeFence.TIME_INTERVAL_NIGHT -> {
                        timeValue += TimeDataValue.TIME_CATEGORY_NIGHT.value
                    }
                }
            }
        }
        return ResultData.Success(TimeAwarenessData(timeValue).timeDataArray.toIntArray())
    }

    override fun getHeadsetAwareness(callback: (headsetVal: ResultData<IntArray>) -> Unit) {
        val headsetValue:ArrayList<Int> = arrayListOf()
        Awareness.getSnapshotClient(context)
            .headphoneState
            .addOnSuccessListener { headphoneStateResponse ->
                val headphoneState: HeadphoneState =
                    headphoneStateResponse?.headphoneState!!
                val pluggedIn =
                    headphoneState.state == HeadphoneState.PLUGGED_IN
                headsetValue += if (pluggedIn)
                    HeadsetStatus.CONNECTED
                else {
                    HeadsetStatus.DISCONNECTED
                }

                val resultData = HeadsetAwarenessData(headsetValue)
                callback.invoke(ResultData.Success(resultData.headsetDataArray.toIntArray()))
            }
            .addOnFailureListener { e ->
                callback.invoke(
                    ResultData.Failed(context.getString(R.string.gms_awareness_error),
                    ErrorModel(exception = e)
                ))
            }
    }

    override fun getBehaviorAwareness(callback: (behaviorVal: ResultData<IntArray>) -> Unit) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if(ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ){
                Awareness.getSnapshotClient(context).detectedActivity
                    .addOnSuccessListener { p0 ->
                        val behaviorValue: ArrayList<Int> = arrayListOf()
                        behaviorValue.add(p0?.activityRecognitionResult?.mostProbableActivity?.type!!)
                        val resultData = BehaviourAwarenessData(behaviorValue)
                        callback.invoke(ResultData.Success(resultData.behaviourDataArray.toIntArray()))
                    }
                    .addOnFailureListener {
                        callback.invoke(ResultData.Failed(context.getString(R.string.gms_awareness_error)))
                    }
             }else{
                val strings = arrayOf(
                    Manifest.permission.ACTIVITY_RECOGNITION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(context as Activity, strings, 2)
            }
        }
        else{
            if(ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ){
                Awareness.getSnapshotClient(context).detectedActivity
                    .addOnSuccessListener { p0 ->
                        val behaviorValue: ArrayList<Int> = arrayListOf()
                        behaviorValue.add(p0?.activityRecognitionResult?.mostProbableActivity?.type!!)
                        val resultData = BehaviourAwarenessData(behaviorValue)
                        callback.invoke(ResultData.Success(resultData.behaviourDataArray.toIntArray()))
                    }
                    .addOnFailureListener {
                        callback.invoke(ResultData.Failed(context.getString(R.string.gms_awareness_error)))
                    }
            }else{
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(context as Activity, strings, 2)
            }
        }
    }

    override fun getWeatherAwareness(callback: (weatherVal: ResultData<IntArray>) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            val strings = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(context as Activity, strings, 2)
            callback.invoke(ResultData.Failed(context.getString(R.string.missing_permission)))
            return
        }
        Awareness.getSnapshotClient(context).weather.addOnSuccessListener { weatherResponse ->
            var weatherValue :ArrayList<Int> = arrayListOf()
            weatherResponse.weather.conditions.forEach { condition->
                if(condition == Weather.CONDITION_CLEAR){
                    weatherValue.add(WeatherDataValue.WEATHER_CLEAR.value)
                }
                else if(condition == Weather.CONDITION_CLOUDY){
                    weatherValue.add(WeatherDataValue.WEATHER_CLOUDS.value)
                }
                else if(condition == Weather.CONDITION_FOGGY){
                    weatherValue.add(WeatherDataValue.WEATHER_FOG.value)
                }
                else if(condition == Weather.CONDITION_HAZY){
                    weatherValue.add(WeatherDataValue.WEATHER_HAZY_SUNSHINE.value)
                }
                else if(condition == Weather.CONDITION_ICY){
                    weatherValue.add(WeatherDataValue.WEATHER_ICE.value)
                }
                else if(condition == Weather.CONDITION_RAINY){
                    weatherValue.add(WeatherDataValue.WEATHER_RAIN.value)
                }
                else if(condition == Weather.CONDITION_SNOWY){
                    weatherValue.add(WeatherDataValue.WEATHER_SNOW.value)
                }
                else if(condition == Weather.CONDITION_STORMY){
                    weatherValue.add(WeatherDataValue.WEATHER_T_STORMS.value)
                }
                else if(condition == Weather.CONDITION_WINDY){
                    weatherValue.add(WeatherDataValue.WEATHER_WINDY.value)
                }
                val resultData = WeatherAwarenessData(weatherValue)
                callback.invoke(ResultData.Success(resultData.weatherDataArray.toIntArray()))
            }
        }.addOnFailureListener {
            callback.invoke(ResultData.Failed(context.getString(R.string.gms_awareness_error)))
        }
    }
}