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

/**
 * Interface defining methods to retrieve awareness data such as time, headset status, behavior, and weather.
 */
interface IAwarenessAPI {
    /**
     * Retrieves time awareness data asynchronously and invokes the provided callback upon completion.
     *
     * @param callback Callback function to handle the result data containing time awareness information.
     */
    fun getTimeAwareness(callback: (timeVal: ResultData<IntArray>) -> Unit)

    /**
     * Retrieves headset awareness data asynchronously and invokes the provided callback upon completion.
     *
     * @param callback Callback function to handle the result data containing headset awareness information.
     */
    fun getHeadsetAwareness(callback: (headsetVal: ResultData<IntArray>) -> Unit)

    /**
     * Retrieves behavior awareness data asynchronously and invokes the provided callback upon completion.
     *
     * @param callback Callback function to handle the result data containing behavior awareness information.
     */
    fun getBehaviorAwareness(callback: (behaviorVal: ResultData<IntArray>) -> Unit)

    /**
     * Retrieves weather awareness data asynchronously and invokes the provided callback upon completion.
     *
     * @param callback Callback function to handle the result data containing weather awareness information.
     */
    fun getWeatherAwareness(callback: (weatherVal: ResultData<IntArray>) -> Unit)

    companion object {
        /**
         * Key for storing the class name of the client service in SharedPreferences.
         */
        const val CLASS_NAME_KEY = "CLASS_NAME_KEY"

        /**
         * Key for storing time awareness data in SharedPreferences.
         */
        const val TIME_KEY = "TIME_KEY"

        /**
         * Key for storing behavior awareness data in SharedPreferences.
         */
        const val BEHAVIOR_KEY = "BEHAVIOR_KEY"

        /**
         * Key for storing weather awareness data in SharedPreferences.
         */
        const val WEATHER_KEY = "WEATHER_KEY"

        /**
         * Key for storing headset awareness data in SharedPreferences.
         */
        const val HEADSET_KEY = "HEADSET_KEY"

        /**
         * Saves the class name of the client service to SharedPreferences.
         *
         * @param context The context used to access SharedPreferences.
         * @param className The class name of the client service to be saved.
         */
        fun saveClientServiceClassName(context: Context, className: String) {
            val sharedPreferences = context.getSharedPreferences(
                "com.hms.lib.commonmobileservices.productadvisor",
                Context.MODE_PRIVATE
            )
            sharedPreferences?.edit()?.putString(CLASS_NAME_KEY, className)?.apply()
        }
    }
}