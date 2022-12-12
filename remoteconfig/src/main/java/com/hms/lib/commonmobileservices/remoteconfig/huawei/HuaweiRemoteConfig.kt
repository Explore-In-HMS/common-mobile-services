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

class HuaweiRemoteConfig:IRemoteConfigService {
    private var config: AGConnectConfig = AGConnectConfig.getInstance()
    override fun setDefaultXml(xml: Int) {
        config.applyDefault(xml)
    }

    override fun fetchAndApply(
        callback: (IsTaskSuccessfull: ResultData<Boolean>) -> Unit,
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

    override fun getString(keyValue: String): String {
        return config.getValueAsString(keyValue)
    }

    override fun getBoolean(keyValue: String): Boolean {
        return config.getValueAsBoolean(keyValue)
    }

    override fun getLong(keyValue: String): Long {
        return config.getValueAsLong(keyValue)
    }

    override fun getDouble(keyValue: String): Double {
        return config.getValueAsDouble(keyValue)
    }
}