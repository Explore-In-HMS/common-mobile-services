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

/**
 * Implementation of the Awareness API for Google Mobile Services.
 *
 * @property context The application context.
 */
class GoogleAwarenessKit(var context: Context) : IAwarenessAPI {
    /**
     * SharedPreferences instance for storing data related to the awareness kit.
     */
    private var sharedPreferences: SharedPreferences? = null

    /**
     * Initializes the [sharedPreferences] property with the application's SharedPreferences.
     *
     * This property is used for storing data related to the awareness kit.
     * It is initialized with the application's SharedPreferences using the provided [context].
     * The SharedPreferences file name is set to "com.hms.lib.commonmobileservices.productadvisor"
     * with the mode set to private (Context.MODE_PRIVATE).
     */
    init {
        sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
    }

    /**
     * Retrieves time awareness data.
     *
     * @param callback Callback function to handle the result data.
     */
    override fun getTimeAwareness(callback: (timeVal: ResultData<IntArray>) -> Unit) {
        /**
         * Checks for the required location permission and retrieves time intervals data.
         *
         * If the ACCESS_FINE_LOCATION permission is not granted, the callback is invoked with a failure result
         * indicating a missing permission. Otherwise, time intervals data is retrieved using the Awareness API.
         * Upon successful retrieval, the callback is invoked with the calculated time awareness value.
         * If an error occurs during the retrieval process, the callback is invoked with a failure result
         * indicating a Google Awareness error.
         *
         * @param callback The callback function to handle the result data.
         */
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback.invoke(ResultData.Failed(context.getString(R.string.missing_permission)))
            return
        }
        Awareness.getSnapshotClient(context).timeIntervals.addOnSuccessListener { response ->
            callback.invoke(calculateTimeValue(response))
        }.addOnFailureListener {
            callback.invoke(ResultData.Failed(context.getString(R.string.gms_awareness_error)))
        }
    }

    /**
     * Calculates the time awareness value based on the provided time intervals response.
     *
     * This function processes the time intervals response obtained from the Awareness API
     * and calculates the corresponding time awareness value. The resulting time awareness value
     * represents the time of day or specific time categories, such as morning, afternoon, etc.
     * The calculated time awareness value is encapsulated in a [ResultData] object.
     *
     * @param timeIntervalsResponse The response containing time intervals data obtained from the Awareness API.
     * @return A [ResultData] object encapsulating the calculated time awareness value as an integer array.
     *         If the calculation is successful, the result contains the time awareness value.
     *         If an error occurs during the calculation, the result indicates failure.
     */
    private fun calculateTimeValue(timeIntervalsResponse: TimeIntervalsResponse): ResultData<IntArray> {
        val timeValue: ArrayList<Int> = arrayListOf()
        for (index in 0 until timeIntervalsResponse.timeIntervals.timeIntervals.size) {
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

    /**
     * Retrieves headset awareness data.
     *
     * @param callback Callback function to handle the result data.
     */
    override fun getHeadsetAwareness(callback: (headsetVal: ResultData<IntArray>) -> Unit) {
        /**
         * Retrieves the headset awareness data using the Awareness API.
         *
         * This function retrieves the current state of the headphones using the Awareness API.
         * Upon successful retrieval, it determines whether the headphones are connected or disconnected,
         * and constructs the corresponding headset awareness data.
         * The constructed data is then encapsulated in a [ResultData] object and passed to the callback function.
         * If an error occurs during the retrieval process, the callback function is invoked with a failure result
         * containing an error message.
         *
         * @param callback The callback function to handle the result data.
         */
        val headsetValue: ArrayList<Int> = arrayListOf()
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
                    ResultData.Failed(
                        context.getString(R.string.gms_awareness_error),
                        ErrorModel(exception = e)
                    )
                )
            }
    }

    /**
     * Retrieves behavior awareness data.
     *
     * @param callback Callback function to handle the result data.
     */
    override fun getBehaviorAwareness(callback: (behaviorVal: ResultData<IntArray>) -> Unit) {
        /**
         * Retrieves the behavior awareness data using the Awareness API.
         *
         * This function retrieves the current detected activity using the Awareness API.
         * It requires appropriate permissions for activity recognition and location access.
         * If the required permissions are granted, it retrieves the detected activity and constructs
         * the corresponding behavior awareness data.
         * The constructed data is then encapsulated in a [ResultData] object and passed to the callback function.
         * If permissions are not granted, a request for permissions is initiated.
         * If an error occurs during the retrieval process, the callback function is invoked with a failure result
         * containing an error message.
         *
         * @param callback The callback function to handle the result data.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
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
            } else {
                val strings = arrayOf(
                    Manifest.permission.ACTIVITY_RECOGNITION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(context as Activity, strings, 2)
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
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
            } else {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(context as Activity, strings, 2)
            }
        }
    }

    /**
     * Retrieves weather awareness data.
     *
     * @param callback Callback function to handle the result data.
     */
    override fun getWeatherAwareness(callback: (weatherVal: ResultData<IntArray>) -> Unit) {
        /**
         * Retrieves the weather awareness data using the Awareness API.
         *
         * This function checks if the necessary permission for accessing location is granted.
         * If not, it requests the permission and returns a failed result indicating the missing permission.
         * If the permission is granted, it retrieves the current weather conditions using the Awareness API.
         * It then maps the weather conditions to corresponding values and constructs the weather awareness data.
         * The constructed data is encapsulated in a [ResultData] object and passed to the callback function.
         * If an error occurs during the retrieval process, the callback function is invoked with a failure result
         * containing an error message.
         *
         * @param callback The callback function to handle the result data.
         */
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val strings = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(context as Activity, strings, 2)
            callback.invoke(ResultData.Failed(context.getString(R.string.missing_permission)))
            return
        }
        Awareness.getSnapshotClient(context).weather.addOnSuccessListener { weatherResponse ->
            val weatherValue: ArrayList<Int> = arrayListOf()
            weatherResponse.weather.conditions.forEach { condition ->
                when (condition) {
                    Weather.CONDITION_CLEAR -> {
                        weatherValue.add(WeatherDataValue.WEATHER_CLEAR.value)
                    }
                    Weather.CONDITION_CLOUDY -> {
                        weatherValue.add(WeatherDataValue.WEATHER_CLOUDS.value)
                    }
                    Weather.CONDITION_FOGGY -> {
                        weatherValue.add(WeatherDataValue.WEATHER_FOG.value)
                    }
                    Weather.CONDITION_HAZY -> {
                        weatherValue.add(WeatherDataValue.WEATHER_HAZY_SUNSHINE.value)
                    }
                    Weather.CONDITION_ICY -> {
                        weatherValue.add(WeatherDataValue.WEATHER_ICE.value)
                    }
                    Weather.CONDITION_RAINY -> {
                        weatherValue.add(WeatherDataValue.WEATHER_RAIN.value)
                    }
                    Weather.CONDITION_SNOWY -> {
                        weatherValue.add(WeatherDataValue.WEATHER_SNOW.value)
                    }
                    Weather.CONDITION_STORMY -> {
                        weatherValue.add(WeatherDataValue.WEATHER_T_STORMS.value)
                    }
                    Weather.CONDITION_WINDY -> {
                        weatherValue.add(WeatherDataValue.WEATHER_WINDY.value)
                    }
                }
                val resultData = WeatherAwarenessData(weatherValue)
                callback.invoke(ResultData.Success(resultData.weatherDataArray.toIntArray()))
            }
        }.addOnFailureListener {
            callback.invoke(ResultData.Failed(context.getString(R.string.gms_awareness_error)))
        }
    }
}