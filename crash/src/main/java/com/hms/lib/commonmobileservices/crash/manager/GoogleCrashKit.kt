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
package com.hms.lib.commonmobileservices.crash.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.crashlytics.CustomKeysAndValues
import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * Implementation of the CrashService interface for Google Crashlytics.
 *
 * @param context The context used for initializing Google Crashlytics.
 */
class GoogleCrashKit(var context: Context) : CrashService {
    /**
     * Shared preferences instance used for storing data related to the crash service.
     */
    private var sharedPreferences: SharedPreferences? = null

    /**
     * Initializes the shared preferences instance with the provided context.
     * This method retrieves the shared preferences from the given context using the specified name and mode.
     *
     * @param context The context used to retrieve the shared preferences.
     */
    init {
        sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
    }

    /**
     * Tests the Google Crashlytics by throwing a RuntimeException.
     *
     * @param context The context required for testing.
     */
    override fun testIt(context: Context) {
        throw RuntimeException("Test Crash")
    }

    /**
     * Logs a message to Google Crashlytics.
     *
     * @param log The message to be logged.
     */
    override fun log(log: String) {
        FirebaseCrashlytics.getInstance().log(log)
    }

    /**
     * Logs an integer and a message to Google Crashlytics.
     *
     * @param log1 The integer value to be logged.
     * @param log2 The message to be logged.
     */
    override fun log(log1: Int, log2: String) {
        // Implementation not provided for this method in Google Crashlytics.
    }

    /**
     * Sets a custom key-value pair in Google Crashlytics.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    override fun setCustomKey(key: String, value: String) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair in Google Crashlytics.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    override fun setCustomKey(key: String, value: Boolean) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair in Google Crashlytics.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    override fun setCustomKey(key: String, value: Double) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair in Google Crashlytics.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    override fun setCustomKey(key: String, value: Float) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair in Google Crashlytics.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    override fun setCustomKey(key: String, value: Int) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair in Google Crashlytics.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    override fun setCustomKey(key: String, value: Long) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets multiple custom key-value pairs in Google Crashlytics.
     *
     * @param keysAndValues The map containing custom key-value pairs.
     */
    override fun setCustomKeys(keysAndValues: CustomKeysAndValues) {
        FirebaseCrashlytics.getInstance().setCustomKeys(keysAndValues)
    }

    /**
     * Sets the user ID in Google Crashlytics.
     *
     * @param id The user ID.
     */
    override fun setUserId(id: String) {
        FirebaseCrashlytics.getInstance().setUserId(id)
    }

    /**
     * Records an exception in Google Crashlytics.
     *
     * @param throwable The Throwable object representing the exception.
     */
    override fun recordException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    /**
     * Enables or disables crash collection in Google Crashlytics.
     *
     * @param enabled true to enable crash collection, false to disable.
     */
    override fun enableCrashCollection(enabled: Boolean) {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(enabled)
    }
}