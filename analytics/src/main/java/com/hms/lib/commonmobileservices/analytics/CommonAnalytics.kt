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
package com.hms.lib.commonmobileservices.analytics

import android.content.Context
import android.os.Bundle
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.Work


interface CommonAnalytics {
    /**
     * Enables or disables the analytics data collection.
     * @param enabled If true, analytics data collection is enabled.
     */
    fun setAnalyticsEnabled(enabled: Boolean)

    /**
     * Sets the user identifier. This identifier is used to track the user across different sessions and events.
     * @param id The user's identifier.
     */
    fun setUserId(id: String)

    /**
     * Sets a user profile property. These properties can be used for detailed analysis of analytics data.
     * @param name The name of the profile property.
     * @param value The value of the profile property.
     */
    fun setUserProfile(name: String, value: String)

    /**
     * Sets the duration of the session for analytics purposes.
     * @param milliseconds The duration of the session in milliseconds.
     */
    fun setSessionDuration(milliseconds: Long)

    /**
     * Saves an event with associated data. Events are used to analyze user interactions.
     * @param key The event key.
     * @param bundle The data associated with the event.
     */
    fun saveEvent(key: String, bundle: Bundle)

    /**
     * Clears all cached analytics data. Useful for privacy concerns or storage limitations.
     */
    fun clearCachedData()

    /**
     * Adds default parameters to every event logged. These parameters are automatically included in each event.
     * @param params The default event parameters.
     */
    fun addDefaultEventParams(params: Bundle)

    /**
     * Asynchronously retrieves the device's Advertising ID (AAID). This ID can be used for identifying the user and providing targeted advertisements.
     * @return Work<String> The asynchronous operation returning the AAID.
     */
    fun getAAID(): Work<String>


    companion object {
        /**
         * Creates an instance of CommonAnalytics based on the available mobile service on the device.
         * This method determines the mobile service type (GMS, HMS, or none) based on the device and returns an instance of the appropriate analytics implementation.
         * @param context The application context.
         * @param firstPriority The preferred mobile service type, if any.
         * @return An instance of CommonAnalytics or null if no suitable service is available.
         */
        fun instance(
            context: Context,
            firstPriority: MobileServiceType? = null
        ): CommonAnalytics? {
            return when (Device.getMobileServiceType(context, firstPriority)) {
                MobileServiceType.HMS -> {
                    HMSAnalyticsImpl(context)
                }

                MobileServiceType.GMS -> {
                    GMSAnalyticsImpl(context)
                }

                MobileServiceType.NON -> null
            }
        }
    }
}