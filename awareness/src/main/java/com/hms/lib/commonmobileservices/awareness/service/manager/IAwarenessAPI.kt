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

import android.content.Context
import com.hms.lib.commonmobileservices.core.ResultData

interface IAwarenessAPI {
    fun getTimeAwareness(callback: (timeVal: ResultData<IntArray>) -> Unit)
    fun getHeadsetAwareness(callback: (headsetVal: ResultData<IntArray>) -> Unit)
    fun getBehaviorAwareness(callback: (behaviorVal: ResultData<IntArray>) -> Unit)
    fun getWeatherAwareness(callback: (weatherVal: ResultData<IntArray>) -> Unit)

    companion object{
        const val CLASS_NAME_KEY="CLASS_NAME_KEY"
        const val TIME_KEY="TIME_KEY"
        const val BEHAVIOR_KEY="BEHAVIOR_KEY"
        const val WEATHER_KEY="WEATHER_KEY"
        const val HEADSET_KEY="HEADSET_KEY"
        fun saveClientServiceClassName(context:Context,className :String){
            val sharedPreferences = context.getSharedPreferences(
                "com.hms.lib.commonmobileservices.productadvisor",
                Context.MODE_PRIVATE
            )
            sharedPreferences?.edit()?.putString(CLASS_NAME_KEY,className)?.apply()
        }
    }
}