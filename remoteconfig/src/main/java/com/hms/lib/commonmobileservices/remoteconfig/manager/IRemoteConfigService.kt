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

package com.hms.lib.commonmobileservices.remoteconfig.manager

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.remoteconfig.google.GoogleRemoteConfig
import com.hms.lib.commonmobileservices.remoteconfig.huawei.HuaweiRemoteConfig

/**
 * Interface for managing remote configuration services.
 */
interface IRemoteConfigService {
    /**
     * Set the default values from an XML resource file.
     * @param xml XML resource containing default values.
     */
    fun setDefaultXml(xml: Int)

    /**
     * Fetches the latest remote config data and applies it.
     * @param callback Callback to be invoked with the result.
     * @param interval Minimum interval between fetches, in milliseconds.
     */
    fun fetchAndApply(callback: (result: ResultData<Unit>) -> Unit, interval: Long)

    /**
     * Get the String value associated with the given key.
     * @param keyValue Key to fetch the String value.
     * @return The String value associated with the given key.
     */
    fun getString(keyValue: String): String

    /**
     * Get the Boolean value associated with the given key.
     * @param keyValue Key to fetch the Boolean value.
     * @return The Boolean value associated with the given key.
     */
    fun getBoolean(keyValue: String): Boolean

    /**
     * Get the Long value associated with the given key.
     * @param keyValue Key to fetch the Long value.
     * @return The Long value associated with the given key.
     */
    fun getLong(keyValue: String): Long

    /**
     * Get the Double value associated with the given key.
     * @param keyValue Key to fetch the Double value.
     * @return The Double value associated with the given key.
     */
    fun getDouble(keyValue: String): Double

    /**
     * Factory object for creating instances of IRemoteConfigService implementations based on the device's mobile service type.
     */
    object Factory {
        /**
         * Create an instance of IRemoteConfigService based on the device's mobile service type.
         * @param context Context for accessing device information.
         * @return An instance of IRemoteConfigService.
         * @throws Exception if the mobile service type is unknown.
         */
        fun create(context: Context): IRemoteConfigService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleRemoteConfig()
                }

                MobileServiceType.HMS -> {
                    HuaweiRemoteConfig()
                }

                else -> {
                    throw Exception("Unknown service")
                }
            }
        }
    }
}