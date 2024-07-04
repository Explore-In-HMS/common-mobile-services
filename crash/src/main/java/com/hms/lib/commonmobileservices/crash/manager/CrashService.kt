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
import com.google.firebase.crashlytics.CustomKeysAndValues
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultData

/**
 * Interface defining the contract for a CrashService, which provides functionalities related to crash reporting and logging.
 */
interface CrashService {
    /**
     * Factory object responsible for creating instances of CrashService.
     */
    object Factory {
        /**
         * Creates an instance of CrashService based on the mobile service type available on the device.
         *
         * @param context The context required for initializing the CrashService.
         * @return An instance of CrashService corresponding to the mobile service type available on the device.
         * @throws Exception if neither Google Mobile Services nor Huawei Mobile Services is installed on the device.
         */
        fun create(context: Context): CrashService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleCrashKit(context)
                }
                MobileServiceType.HMS -> {
                    HuaweiCrashKit(context)
                }
                else -> {
                    throw Exception("In order to use this SDK, Google Mobile Services or Huawei Mobile Services must be installed on your device.")
                }
            }
        }
    }

    /**
     * Tests the CrashService.
     *
     * @param context The context required for testing.
     */
    fun testIt(context: Context)

    /**
     * Logs a message.
     *
     * @param log The message to be logged.
     */
    fun log(log: String)

    /**
     * Logs an integer and a message.
     *
     * @param log1 The integer value to be logged.
     * @param log2 The message to be logged.
     */
    fun log(log1: Int, log2: String)

    /**
     * Sets a custom key-value pair.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    fun setCustomKey(key: String, value: String)

    /**
     * Sets a custom key-value pair.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    fun setCustomKey(key: String, value: Boolean)

    /**
     * Sets a custom key-value pair.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    fun setCustomKey(key: String, value: Double)

    /**
     * Sets a custom key-value pair.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    fun setCustomKey(key: String, value: Float)

    /**
     * Sets a custom key-value pair.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    fun setCustomKey(key: String, value: Int)

    /**
     * Sets a custom key-value pair.
     *
     * @param key The key for the custom data.
     * @param value The value associated with the key.
     */
    fun setCustomKey(key: String, value: Long)

    /**
     * Sets multiple custom key-value pairs.
     *
     * @param keysAndValues The map containing custom key-value pairs.
     */
    fun setCustomKeys(keysAndValues: CustomKeysAndValues)

    /**
     * Sets the user ID.
     *
     * @param id The user ID.
     */
    fun setUserId(id: String)

    /**
     * Records an exception.
     *
     * @param throwable The Throwable object representing the exception.
     */
    fun recordException(throwable: Throwable)

    /**
     * Enables or disables crash collection.
     *
     * @param enabled true to enable crash collection, false to disable.
     */
    fun enableCrashCollection(enabled: Boolean)
}