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

package com.hms.lib.commonmobileservices.remoteconfig.google

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.remoteconfig.manager.IRemoteConfigService

/**
 * Implementation of remote config service using Google Firebase Remote Config.
 */
class GoogleRemoteConfig : IRemoteConfigService {
    private var config: FirebaseRemoteConfig = Firebase.remoteConfig

    /**
     * Set the default values from an XML resource file.
     * @param xml XML resource containing default values.
     */
    override fun setDefaultXml(xml: Int) {
        config.setDefaultsAsync(xml)
    }

    /**
     * Fetches the latest Firebase Remote Config data and applies it.
     * @param callback Callback to be invoked with the result.
     * @param interval Minimum interval between fetches, in seconds.
     */
    override fun fetchAndApply(
        callback: (result: ResultData<Unit>) -> Unit,
        interval: Long
    ) {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = interval
        }
        config.setConfigSettingsAsync(configSettings)
        config.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback.invoke(ResultData.Success())
            } else {
                callback.invoke(ResultData.Failed())
            }
        }
    }

    /**
     * Get the String value associated with the given key from Firebase Remote Config.
     * @param keyValue Key to fetch the String value.
     * @return The String value associated with the given key.
     */
    override fun getString(keyValue: String): String {
        return config.getString(keyValue)
    }

    /**
     * Get the Boolean value associated with the given key from Firebase Remote Config.
     * @param keyValue Key to fetch the Boolean value.
     * @return The Boolean value associated with the given key.
     */
    override fun getBoolean(keyValue: String): Boolean {
        return config.getBoolean(keyValue)
    }

    /**
     * Get the Long value associated with the given key from Firebase Remote Config.
     * @param keyValue Key to fetch the Long value.
     * @return The Long value associated with the given key.
     */
    override fun getLong(keyValue: String): Long {
        return config.getLong(keyValue)
    }

    /**
     * Get the Double value associated with the given key from Firebase Remote Config.
     * @param keyValue Key to fetch the Double value.
     * @return The Double value associated with the given key.
     */
    override fun getDouble(keyValue: String): Double {
        return config.getDouble(keyValue)
    }
}