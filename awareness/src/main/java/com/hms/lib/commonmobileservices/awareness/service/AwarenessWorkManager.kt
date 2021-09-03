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

package com.hms.lib.commonmobileservices.awareness.service

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hms.lib.commonmobileservices.awareness.IntentLoader
import com.hms.lib.commonmobileservices.awareness.service.manager.HuaweiGoogleAwarenessManager
import com.hms.lib.commonmobileservices.awareness.service.manager.IAwarenessAPI
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.core.handleSuccess
import java.lang.Exception

class AwarenessWorkManager(val context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        workIt()
        return Result.success()
    }

    private fun callAwarenessTime(callback: (timeVal: ResultData<IntArray>) -> Unit) {
        HuaweiGoogleAwarenessManager(context).getTime(callback)
    }

    private fun callAwarenessBehavior(callback: (behaviorVal: ResultData<IntArray>) -> Unit) {
        HuaweiGoogleAwarenessManager(context).getBehavior(callback)
    }

    private fun callAwarenessWeather(callback: (weatherVal: ResultData<IntArray>) -> Unit) {
        HuaweiGoogleAwarenessManager(context).getWeather(callback)
    }

    private fun callAwarenessHeadset(callback: (headsetVal: ResultData<IntArray>) -> Unit) {
        HuaweiGoogleAwarenessManager(context).getHeadset(callback)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun workIt() {
        val sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
        val className = sharedPreferences.getString(IAwarenessAPI.CLASS_NAME_KEY, "")

        callAwarenessTime {
            it.handleSuccess {
                val intent =
                    IntentLoader().loadIntentWithExtrasOrReturnNull(
                        context,
                        className!!,
                        Bundle().apply {
                            putIntArray(IAwarenessAPI.TIME_KEY,it.data)
                        })
                try {
                    context.startService(intent)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }

        callAwarenessBehavior {
            it.handleSuccess {
                val intent =
                    IntentLoader().loadIntentWithExtrasOrReturnNull(
                        context,
                        className!!,
                        Bundle().apply {
                            putIntArray(IAwarenessAPI.BEHAVIOR_KEY, it.data)
                        })
                try {
                    context.startService(intent)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }

        callAwarenessHeadset {
            it.handleSuccess {
                val intent =
                    IntentLoader().loadIntentWithExtrasOrReturnNull(
                        context,
                        className!!,
                        Bundle().apply {
                            putIntArray(IAwarenessAPI.HEADSET_KEY, it.data)
                        })
                try {
                    context.startService(intent)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }

        callAwarenessWeather {
            it.handleSuccess {
                val intent =
                    IntentLoader().loadIntentWithExtrasOrReturnNull(
                        context,
                        className!!,
                        Bundle().apply {
                            putIntArray(IAwarenessAPI.WEATHER_KEY, it.data)
                        })
                try {
                    context.startService(intent)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }
    }
}