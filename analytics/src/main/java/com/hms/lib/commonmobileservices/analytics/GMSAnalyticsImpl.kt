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
import com.google.firebase.analytics.FirebaseAnalytics
import com.hms.lib.commonmobileservices.core.Work

/**
 * Implementation of CommonAnalytics interface using Firebase Analytics. This class provides a concrete
 * implementation for tracking analytics events, user properties, and sessions within an app using Google's Firebase Analytics.
 * It encapsulates the functionality for enabling/disabling analytics collection, managing user identification and profiles,
 * setting session durations, logging custom events, and managing cached data.
 *
 * @param context The context from which Firebase Analytics instance will be obtained. This is typically your application or activity context.
 */
class GMSAnalyticsImpl(context: Context) : CommonAnalytics {

    private val analytics = FirebaseAnalytics.getInstance(context)

    /**
     * Enables or disables the collection of analytics data. When disabled, no analytics data will be sent to Firebase.
     *
     * @param enabled True to enable analytics collection, false to disable.
     */
    override fun setAnalyticsEnabled(enabled: Boolean) {
        analytics.setAnalyticsCollectionEnabled(enabled)
    }

    /**
     * Sets the user ID for the current app user. This ID can be used to identify users across different devices
     * and platforms for analytics purposes.
     *
     * @param id A unique ID representing the user.
     */
    override fun setUserId(id: String) {
        analytics.setUserId(id)
    }

    /**
     * Sets a user property for the current user. User properties are attributes that can be used to segment
     * users into groups for analytics insights and targeting.
     *
     * @param name The name of the user property to set.
     * @param value The value of the user property.
     */
    override fun setUserProfile(name: String, value: String) {
        analytics.setUserProperty(name, value)
    }

    /**
     * Sets the duration of inactivity that constitutes the end of a session. A new session will be started after
     * this timeout.
     *
     * @param milliseconds The duration in milliseconds after which a session is considered ended due to inactivity.
     */
    override fun setSessionDuration(milliseconds: Long) {
        analytics.setSessionTimeoutDuration(milliseconds)
    }

    /**
     * Logs a custom event to Firebase Analytics. Custom events can be used to track user interactions that are
     * specific to your app.
     *
     * @param key The name of the event to log.
     * @param bundle A Bundle containing additional information about the event.
     */
    override fun saveEvent(key: String, bundle: Bundle) {
        analytics.logEvent(key, bundle)
    }

    /**
     * Clears all analytics data cached locally on the device. This includes any unsent events and user properties.
     */
    override fun clearCachedData() {
        analytics.resetAnalyticsData()
    }

    /**
     * Sets default parameters for all events. These parameters will be included with every event logged by Firebase Analytics.
     *
     * @param params A Bundle containing the default event parameters.
     */
    override fun addDefaultEventParams(params: Bundle) {
        analytics.setDefaultEventParameters(params)
    }

    /**
     * Asynchronously retrieves the app instance ID (AAID) from Firebase Analytics. The AAID is a unique identifier
     * for the app instance and can be used for attribution and app instance-specific operations.
     *
     * @return A Work object that will contain the AAID when the operation is successful.
     */
    override fun getAAID(): Work<String> {
        val worker: Work<String> = Work()
        analytics.appInstanceId
            .addOnSuccessListener { worker.onSuccess(it) }
            .addOnFailureListener { worker.onFailure(it) }
            .addOnCanceledListener { worker.onCanceled() }
        return worker
    }

}