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

/**
 * Manager class for handling awareness functionalities from Huawei or Google Awareness Kit.
 *
 * This class provides methods to retrieve awareness data such as time, weather, behavior, and headset status.
 *
 * @property context The context used to access Awareness Kit services.
 */
class HuaweiGoogleAwarenessManager(context: Context) {
    /**
     * The awareness service instance obtained from either Huawei or Google Awareness Kit.
     *
     * This property is initialized lazily using the AwarenessKitFactory to get the appropriate
     * awareness service based on the mobile service type detected on the device.
     *
     * @see AwarenessKitFactory
     * @see Device.getMobileServiceType
     */
    private val awarenessService: IAwarenessAPI =
        AwarenessKitFactory().getAwarenessService(
            context, Device.getMobileServiceType(
                context,
                MobileServiceType.HMS
            )
        )!!

    /**
     * Retrieves time awareness data.
     *
     * @param callback Callback function to handle the result data, containing an array of time awareness values.
     */
    fun getTime(callback: (timeVal: ResultData<IntArray>) -> Unit) {
        awarenessService.getTimeAwareness(callback)
    }

    /**
     * Retrieves weather awareness data.
     *
     * @param callback Callback function to handle the result data, containing an array of weather awareness values.
     */
    fun getWeather(callback: (weatherVal: ResultData<IntArray>) -> Unit) {
        awarenessService.getWeatherAwareness(callback)
    }

    /**
     * Retrieves behavior awareness data.
     *
     * @param callback Callback function to handle the result data, containing an array of behavior awareness values.
     */
    fun getBehavior(callback: (behaviorVal: ResultData<IntArray>) -> Unit) {
        awarenessService.getBehaviorAwareness(callback)
    }

    /**
     * Retrieves headset awareness data.
     *
     * @param callback Callback function to handle the result data, containing an array of headset awareness values.
     */
    fun getHeadset(callback: (headsetVal: ResultData<IntArray>) -> Unit) {
        awarenessService.getHeadsetAwareness(callback)
    }
}