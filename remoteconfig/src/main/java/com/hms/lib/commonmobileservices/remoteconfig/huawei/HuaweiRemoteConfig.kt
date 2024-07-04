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

package com.hms.lib.commonmobileservices.remoteconfig.huawei

import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.remoteconfig.manager.IRemoteConfigService
import com.huawei.agconnect.remoteconfig.AGConnectConfig

/**
 * Implementation of remote config service using Huawei AGConnect Remote Config.
 */
class HuaweiRemoteConfig:IRemoteConfigService {
    private var config: AGConnectConfig = AGConnectConfig.getInstance()
    /**
     * Set the default values from an XML resource file.
     * @param xml XML resource containing default values.
     */
    override fun setDefaultXml(xml: Int) {
        config.applyDefault(xml)
    }

    /**
     * Fetches the latest Huawei AGConnect Remote Config data and applies it.
     * @param callback Callback to be invoked with the result.
     * @param interval Minimum interval between fetches, in milliseconds.
     */
    override fun fetchAndApply(
        callback: (result: ResultData<Unit>) -> Unit,
        interval: Long
    ) {
        config.fetch(interval)
            .addOnSuccessListener { configValues -> // Apply Network Config to Current Config
                callback.invoke(ResultData.Success())
                config.apply(configValues)
            }.addOnFailureListener {
                callback.invoke(ResultData.Failed())
            }
    }

    /**
     * Get the String value associated with the given key from Huawei AGConnect Remote Config.
     * @param keyValue Key to fetch the String value.
     * @return The String value associated with the given key.
     */
    override fun getString(keyValue: String): String {
        return config.getValueAsString(keyValue)
    }

    /**
     * Get the Boolean value associated with the given key from Huawei AGConnect Remote Config.
     * @param keyValue Key to fetch the Boolean value.
     * @return The Boolean value associated with the given key.
     */
    override fun getBoolean(keyValue: String): Boolean {
        return config.getValueAsBoolean(keyValue)
    }

    /**
     * Get the Long value associated with the given key from Huawei AGConnect Remote Config.
     * @param keyValue Key to fetch the Long value.
     * @return The Long value associated with the given key.
     */
    override fun getLong(keyValue: String): Long {
        return config.getValueAsLong(keyValue)
    }

    /**
     * Get the Double value associated with the given key from Huawei AGConnect Remote Config.
     * @param keyValue Key to fetch the Double value.
     * @return The Double value associated with the given key.
     */
    override fun getDouble(keyValue: String): Double {
        return config.getValueAsDouble(keyValue)
    }
}