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

interface IRemoteConfigService {
    fun setDefaultXml(xml: Int)
    fun fetchAndApply(callback: (result: ResultData<Unit>) -> Unit, interval:Long)
    fun getString(keyValue: String): String
    fun getBoolean(keyValue: String): Boolean
    fun getLong(keyValue: String): Long
    fun getDouble(keyValue: String): Double

    object Factory {
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