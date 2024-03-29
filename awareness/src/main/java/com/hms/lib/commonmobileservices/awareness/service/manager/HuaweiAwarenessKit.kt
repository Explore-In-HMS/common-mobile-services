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
import com.huawei.hms.kit.awareness.Awareness
import com.huawei.hms.kit.awareness.status.BehaviorStatus
import com.huawei.hms.kit.awareness.status.HeadsetStatus
import com.huawei.hms.kit.awareness.status.WeatherStatus
import com.hms.lib.commonmobileservices.awareness.model.*
import com.hms.lib.commonmobileservices.core.ResultData

/**
 * Implementation of the IAwarenessAPI for Huawei Awareness Kit.
 *
 * @property context The application context.
 * @constructor Initializes the HuaweiAwarenessKit with the provided context.
 */
class HuaweiAwarenessKit(var context: Context) : IAwarenessAPI {
    /**
     * The SharedPreferences instance for accessing the preferences data store.
     * Initialized to null by default.
     */
    private var sharedPreferences: SharedPreferences? = null

    /**
     * Initializes the [sharedPreferences] property with the SharedPreferences instance.
     * This instance is obtained using the application context and allows access to the
     * preferences data store for the package 'com.hms.lib.commonmobileservices.productadvisor'.
     * If no SharedPreferences instance is found, [sharedPreferences] is set to null.
     */
    init {
        sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
    }

    /**
     * Retrieves the time awareness data.
     *
     * If the necessary location permissions are granted, this function retrieves time awareness data
     * using Huawei Awareness Kit. It gets the time categories by country code and returns the result
     * through the provided callback.
     *
     * If the required permissions are not granted, this function requests them from the user.
     *
     * @param callback Callback function to handle the result data, containing an array of time awareness values.
     */
    override fun getTimeAwareness(callback: (timeVal: ResultData<IntArray>) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var awarenessData: AwarenessData?
            val timeResultData: MutableList<Int> = mutableListOf()
            val task = Awareness.getCaptureClient(this.context)
                .getTimeCategoriesByCountryCode(context.resources.configuration.locale.country)

            task.addOnSuccessListener { timeCategoriesResponse ->
                val categories = timeCategoriesResponse.timeCategories
                val timeInfo = categories.timeCategories
                timeInfo.forEach {
                    timeResultData.add(it)
                }
                awarenessData =
                    AwarenessData(
                        TimeAwarenessData(
                            timeResultData
                        ), AwarenessType.TIME
                    )
                val resultData = awarenessData?.awarenessValueData as TimeAwarenessData
                callback.invoke(ResultData.Success(resultData.timeDataArray.toIntArray()))
            }.addOnFailureListener {
                callback.invoke(ResultData.Failed(context.getString(R.string.hms_awareness_error)))
            }
        } else {
            val strings = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(context as Activity, strings, 2)
        }
    }

    /**
     * Retrieves the headset awareness data.
     *
     * This function retrieves headset awareness data using Huawei Awareness Kit. It queries the headset status
     * and returns the result through the provided callback.
     *
     * @param callback Callback function to handle the result data, containing an array of headset awareness values.
     */
    override fun getHeadsetAwareness(callback: (headsetVal: ResultData<IntArray>) -> Unit) {
        var awarenessData: AwarenessData?
        val headsetResultData: MutableList<Int> = mutableListOf()
        val task = Awareness.getCaptureClient(context).headsetStatus
        task.addOnSuccessListener { response ->
            val headsetStatus: HeadsetStatus = response.headsetStatus
            headsetResultData.add(headsetStatus.status)
            awarenessData =
                AwarenessData(
                    HeadsetAwarenessData(
                        headsetResultData
                    ),
                    AwarenessType.HEADSET
                )
            val resultData = awarenessData!!.awarenessValueData as HeadsetAwarenessData
            callback.invoke(ResultData.Success(resultData.headsetDataArray.toIntArray()))

        }.addOnFailureListener {
            callback.invoke(ResultData.Failed(context.getString(R.string.hms_awareness_error)))
        }
    }

    /**
     * Retrieves the behavior awareness data.
     *
     * This function retrieves behavior awareness data using Huawei Awareness Kit. It checks for necessary permissions
     * and then queries the behavior status. The result is returned through the provided callback, containing an array
     * of behavior awareness values.
     *
     * @param callback Callback function to handle the result data, containing an array of behavior awareness values.
     */
    override fun getBehaviorAwareness(callback: (behaviorVal: ResultData<IntArray>) -> Unit) {
        try {
            // Checking if the device supports the required SDK version
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                callback.invoke(ResultData.Failed(error = context.getString(R.string.hms_awareness_error)))
                return
            }

            // List of required permissions
            val permissionList = mutableListOf(
                "com.huawei.hms.permission.ACTIVITY_RECOGNITION",
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            // Checking permissions
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context, "com.huawei.hms.permission.ACTIVITY_RECOGNITION"
                ) == PackageManager.PERMISSION_GRANTED && (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_GRANTED))
            ) {
                var awarenessData: AwarenessData?
                val behaviourResultData: MutableList<Int> = mutableListOf()

                // Querying behavior status
                val task = Awareness.getCaptureClient(context).behavior
                task.addOnSuccessListener { response ->
                    val behaviorStatus: BehaviorStatus = response.behaviorStatus
                    behaviourResultData.add(behaviorStatus.mostLikelyBehavior.type)
                    awarenessData =
                        AwarenessData(
                            BehaviourAwarenessData(
                                behaviourResultData
                            ),
                            AwarenessType.BEHAVIOUR
                        )
                    val resultData =
                        awarenessData!!.awarenessValueData as BehaviourAwarenessData
                    callback.invoke(ResultData.Success(resultData.behaviourDataArray.toIntArray()))

                }.addOnFailureListener {
                    callback.invoke(ResultData.Failed(context.getString(R.string.hms_awareness_error)))
                }
            } else {
                // Requesting permissions if not granted
                ActivityCompat.requestPermissions(
                    context as Activity,
                    permissionList.toTypedArray(),
                    2
                )
            }
        } catch (e: Exception) {
            // Handling exceptions
            callback.invoke(ResultData.Failed(context.getString(R.string.hms_awareness_error)))
        }
    }

    /**
     * Retrieves weather awareness data.
     *
     * This function retrieves weather awareness data using Huawei Awareness Kit. It first checks for necessary permissions
     * and then queries the weather status. The result, containing an array of weather awareness values, is returned
     * through the provided callback.
     *
     * @param callback Callback function to handle the result data, containing an array of weather awareness values.
     */
    override fun getWeatherAwareness(callback: (weatherVal: ResultData<IntArray>) -> Unit) {
        // Checking if the required location permissions are granted
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var awarenessData: AwarenessData?
            val weatherResultData: MutableList<Int> = mutableListOf()

            // Querying weather status
            val task = Awareness.getCaptureClient(context).weatherByDevice
            task.addOnSuccessListener { response ->
                val weatherStatus: WeatherStatus = response.weatherStatus
                weatherStatus.dailyWeather.forEach {
                    weatherResultData.add(it.situationDay.weatherId)
                }
                awarenessData =
                    AwarenessData(
                        WeatherAwarenessData(
                            weatherResultData
                        ),
                        AwarenessType.WEATHER
                    )
                val resultData = awarenessData!!.awarenessValueData as WeatherAwarenessData
                callback.invoke(ResultData.Success(resultData.weatherDataArray.toIntArray()))
            }.addOnFailureListener {
                // Handling failure
                callback.invoke(ResultData.Failed(context.getString(R.string.hms_awareness_error)))
            }
        } else {
            // Requesting permissions if not granted
            val strings = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(context as Activity, strings, 2)
        }
    }
}