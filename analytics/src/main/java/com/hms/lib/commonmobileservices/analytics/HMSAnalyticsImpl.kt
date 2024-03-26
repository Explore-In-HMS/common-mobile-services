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
import com.hms.lib.commonmobileservices.core.Work
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsTools

/**
 * Implementation of the CommonAnalytics interface using Huawei Mobile Services (HMS) Analytics.
 * This class provides a concrete implementation for tracking analytics events, user properties,
 * and sessions within an app using Huawei's Analytics service. It encapsulates functionality
 * for enabling/disabling analytics collection, managing user identification and profiles,
 * setting session durations, logging custom events, and managing cached data in the context of HMS.
 *
 * @param context The context from which the HMS Analytics instance will be obtained.
 * This is typically your application or activity context.
 */
class HMSAnalyticsImpl(context: Context) : CommonAnalytics {

    private val hmsAnalytics = HiAnalytics.getInstance(context)

    init {
        // Enable logging for HiAnalyticsTools for debug purposes
        HiAnalyticsTools.enableLog()
    }

    /**
     * Enables or disables the collection of analytics data by HMS Analytics. When disabled,
     * no analytics data will be sent to Huawei.
     *
     * @param enabled True to enable analytics collection, false to disable it.
     */
    override fun setAnalyticsEnabled(enabled: Boolean) {
        hmsAnalytics.setAnalyticsEnabled(enabled)
    }

    /**
     * Sets the user ID for the current app user. This ID can be used to identify users across
     * different devices and platforms for analytics purposes within HMS ecosystem.
     *
     * @param id A unique ID representing the user.
     */
    override fun setUserId(id: String) {
        hmsAnalytics.setUserId(id)
    }

    /**
     * Sets a user property for the current user. User properties are attributes that can be
     * used to segment users into groups for analytics insights and targeting within the HMS ecosystem.
     *
     * @param name The name of the user property to set.
     * @param value The value of the user property.
     */
    override fun setUserProfile(name: String, value: String) {
        hmsAnalytics.setUserProfile(name, value)
    }

    /**
     * Sets the duration of inactivity that constitutes the end of a session. A new session will be
     * started after this timeout. This duration is set in the context of HMS Analytics.
     *
     * @param milliseconds The duration in milliseconds after which a session is considered ended due to inactivity.
     */
    override fun setSessionDuration(milliseconds: Long) {
        hmsAnalytics.setSessionDuration(milliseconds)
    }

    /**
     * Logs a custom event to HMS Analytics. Custom events can be used to track user interactions
     * that are specific to your app within the HMS ecosystem.
     *
     * @param key The name of the event to log.
     * @param bundle A Bundle containing additional information about the event.
     */
    override fun saveEvent(key: String, bundle: Bundle) {
        hmsAnalytics.onEvent(key, bundle)
    }

    /**
     * Clears all analytics data cached locally on the device for HMS Analytics. This includes
     * any unsent events and user properties.
     */
    override fun clearCachedData() {
        hmsAnalytics.clearCachedData()
    }

    /**
     * Sets default parameters for all events logged by HMS Analytics. These parameters will be
     * included with every event logged.
     *
     * @param params A Bundle containing the default event parameters.
     */
    override fun addDefaultEventParams(params: Bundle) {
        hmsAnalytics.addDefaultEventParams(params)
    }

    /**
     * Asynchronously retrieves the app instance ID (AAID) from HMS Analytics. The AAID is a unique
     * identifier for the app instance and can be used for attribution and app instance-specific
     * operations within the HMS ecosystem.
     *
     * @return A Work object that will contain the AAID when the operation is successful.
     */
    override fun getAAID(): Work<String> {
        val worker: Work<String> = Work()
        hmsAnalytics.aaid
            .addOnSuccessListener {
                worker.onSuccess(it)
            }
            .addOnFailureListener {
                worker.onFailure(it)
            }
            .addOnCanceledListener {
                worker.onCanceled()
            }
        return worker
    }

}