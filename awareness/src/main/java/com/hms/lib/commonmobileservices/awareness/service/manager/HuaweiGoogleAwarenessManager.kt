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
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultData

class HuaweiGoogleAwarenessManager(context: Context) {
    private val awarenessService: IAwarenessAPI =
        AwarenessKitFactory().getAwarenessService(context, Device.getMobileServiceType(context,
            MobileServiceType.HMS))!!

    fun getTime(callback: (timeVal: ResultData<IntArray>) -> Unit) {
        awarenessService.getTimeAwareness(callback)
    }

    fun getWeather(callback: (weatherVal: ResultData<IntArray>) -> Unit) {
        awarenessService.getWeatherAwareness(callback)
    }

    fun getBehavior(callback: (behaviorVal: ResultData<IntArray>) -> Unit) {
        awarenessService.getBehaviorAwareness(callback)
    }

    fun getHeadset(callback: (headsetVal: ResultData<IntArray>) -> Unit) {
        awarenessService.getHeadsetAwareness(callback)
    }
}