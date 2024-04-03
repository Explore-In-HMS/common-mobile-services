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
import com.huawei.agconnect.crash.AGConnectCrash

/**
 * Implementation of [CrashService] for Huawei Mobile Services Crash Kit.
 *
 * @property context The context used for initializing the Crash Kit.
 */
class HuaweiCrashKit(context: Context) : CrashService {
    /**
     * SharedPreferences instance used for storing configuration data related to the Crash Kit.
     */
    private var sharedPreferences: SharedPreferences? = null

    /**
     * Initializes the `sharedPreferences` variable with a SharedPreferences instance.
     * It is initialized with the SharedPreferences instance associated with the provided context,
     * using the file name "com.hms.lib.commonmobileservices.productadvisor" and the mode Context.MODE_PRIVATE.
     */
    init {
        sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
    }

    /**
     * Triggers a test crash to validate the integration of the AGConnect Crash service. This method is
     * useful for testing whether crash reports are successfully sent to the Huawei AGConnect platform
     * from the application. It simulates a crash scenario without causing any real application failure.
     *
     * Note: This method should be used for testing purposes only and not in production code.
     *
     * @param context The Android context, usually provided by an Activity or Application, used for
     *                initiating the test crash within the AGConnect Crash service context.
     */
    override fun testIt(context: Context) {
        AGConnectCrash.getInstance().testIt(context)
    }

    /**
     * Logs a custom message to be included in the crash report. This is useful for adding additional
     * information that might help in diagnosing issues leading up to a crash. The logged messages
     * appear in the crash report sent to the AGConnect Crash service.
     *
     * @param log The custom log message as a String.
     */
    override fun log(log: String) {
        AGConnectCrash.getInstance().log(log)
    }

    /**
     * Logs a custom message with an integer value, providing a structured way to include numeric data
     * in crash reports. This can be particularly useful for logging status codes or other numeric
     * indicators that could help in diagnosing crashes.
     *
     * @param log1 An integer value associated with the log message.
     * @param log2 The log message as a String.
     */
    override fun log(log1: Int, log2: String) {
        AGConnectCrash.getInstance().log(log1, log2)
    }

    /**
     * Sets a custom key-value pair in the crash report. This allows for including additional
     * contextual information in the report, which can be crucial for diagnosing the cause of the crash.
     * Custom keys and values are displayed in the AGConnect Crash service dashboard, making it easier
     * to filter and analyze crashes.
     *
     * @param key The key as a String. This acts as an identifier for the custom data.
     * @param value The value associated with the key, also as a String.
     */
    override fun setCustomKey(key: String, value: String) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair of type Boolean for crash reporting.
     *
     * @param key The key for the custom value.
     * @param value The Boolean value to be associated with the key.
     */
    override fun setCustomKey(key: String, value: Boolean) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair of type Double for crash reporting.
     *
     * @param key The key for the custom value.
     * @param value The Double value to be associated with the key.
     */
    override fun setCustomKey(key: String, value: Double) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair of type Float for crash reporting.
     *
     * @param key The key for the custom value.
     * @param value The Float value to be associated with the key.
     */
    override fun setCustomKey(key: String, value: Float) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair of type Int for crash reporting.
     *
     * @param key The key for the custom value.
     * @param value The Int value to be associated with the key.
     */
    override fun setCustomKey(key: String, value: Int) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets a custom key-value pair of type Long for crash reporting.
     *
     * @param key The key for the custom value.
     * @param value The Long value to be associated with the key.
     */
    override fun setCustomKey(key: String, value: Long) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    /**
     * Sets custom key-value pairs for crash reporting.
     *
     * @param keysAndValues The map containing custom keys and their corresponding values.
     */
    override fun setCustomKeys(keysAndValues: CustomKeysAndValues) {
        // Implementation to set custom keys and values for crash reporting goes here
    }

    /**
     * Sets the user ID for crash reporting.
     *
     * @param id The user ID to be associated with crash reports.
     */
    override fun setUserId(id: String) {
        AGConnectCrash.getInstance().setUserId(id)
    }

    /**
     * Records an exception for crash reporting.
     *
     * @param throwable The Throwable object representing the exception to be recorded.
     */
    override fun recordException(throwable: Throwable) {
        AGConnectCrash.getInstance().recordException(throwable)
    }

    /**
     * Enables or disables crash collection.
     *
     * @param enabled Boolean value indicating whether crash collection is enabled or disabled.
     */
    override fun enableCrashCollection(enabled: Boolean) {
        AGConnectCrash.getInstance().enableCrashCollection(enabled)
    }
}