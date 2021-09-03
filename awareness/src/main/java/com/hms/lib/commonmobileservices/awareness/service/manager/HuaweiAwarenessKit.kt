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
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.alperenbabagil.commonmobileservices.R
import com.huawei.hms.kit.awareness.Awareness
import com.huawei.hms.kit.awareness.status.BehaviorStatus
import com.huawei.hms.kit.awareness.status.HeadsetStatus
import com.huawei.hms.kit.awareness.status.WeatherStatus
import com.hms.lib.commonmobileservices.awareness.model.*
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.hms.lib.commonmobileservices.core.ResultData

class HuaweiAwarenessKit(var context: Context) : IAwarenessAPI {
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
    }

    override fun getTimeAwareness(callback: (timeVal: ResultData<IntArray>) -> Unit) {
        context.runWithPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
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
        }
    }

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

    override fun getBehaviorAwareness(callback: (behaviorVal: ResultData<IntArray>) -> Unit) {
        try {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                callback.invoke(ResultData.Failed(error = context.getString(R.string.hms_awareness_error)))
                return
            }
            val permissionList = mutableListOf("com.huawei.hms.permission.ACTIVITY_RECOGNITION",
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) permissionList.add(Manifest.permission.ACTIVITY_RECOGNITION)
            context.runWithPermissions(
                *permissionList.toTypedArray()
            ) {
                var awarenessData: AwarenessData?
                val behaviourResultData: MutableList<Int> = mutableListOf()
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
            }
        } catch (e: Exception) {
            callback.invoke(ResultData.Failed(context.getString(R.string.hms_awareness_error)))
        }
    }

    @SuppressLint("MissingPermission")
    override fun getWeatherAwareness(callback: (weatherVal: ResultData<IntArray>) -> Unit) {
        context.runWithPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        ) {
            var awarenessData: AwarenessData?
            val weatherResultData: MutableList<Int> = mutableListOf()
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
                callback.invoke(ResultData.Failed(context.getString(R.string.hms_awareness_error)))
            }
        }
    }
}