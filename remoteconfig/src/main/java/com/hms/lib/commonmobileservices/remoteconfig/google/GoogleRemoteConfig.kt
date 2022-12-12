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

class GoogleRemoteConfig:IRemoteConfigService {
    private var config: FirebaseRemoteConfig = Firebase.remoteConfig
    override fun setDefaultXml(xml: Int) {
        config.setDefaultsAsync(xml)
    }

    override fun fetchAndApply(
        callback: (result: ResultData<Unit>) -> Unit,
        interval: Long
    ) {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = interval
        }
        config.setConfigSettingsAsync(configSettings)
        config.fetchAndActivate().addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback.invoke(ResultData.Success())
            }
            else{
                callback.invoke(ResultData.Failed())
            }
        }
    }

    override fun getString(keyValue: String): String {
        return config.getString(keyValue)
    }

    override fun getBoolean(keyValue: String): Boolean {
        return config.getBoolean(keyValue)
    }

    override fun getLong(keyValue: String): Long {
        return config.getLong(keyValue)
    }

    override fun getDouble(keyValue: String): Double {
        return config.getDouble(keyValue)
    }
}