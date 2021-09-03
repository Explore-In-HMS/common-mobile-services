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
package com.hms.lib.commonmobileservices.safety

import android.app.Activity
import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.safety.google.GoogleSafetyServiceImpl
import com.hms.lib.commonmobileservices.safety.huawei.HuaweiSafetyServiceImpl

interface SafetyService {

    fun userDetect(appKey : String,callback: SafetyServiceCallback<SafetyServiceResponse>)
    fun rootDetection(appKey: String,callback: SafetyRootDetectionCallback<RootDetectionResponse> )

    interface SafetyServiceCallback<T>{
        fun onSuccessUserDetect(result: T? = null)
        fun onFailUserDetect(e: java.lang.Exception)
    }

    interface SafetyRootDetectionCallback<T>{
        fun onSuccessRootDetect(result: T? = null)
        fun onFailRootDetect(e: java.lang.Exception)
    }

    object Factory {
        fun create(context: Context): SafetyService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleSafetyServiceImpl(context)
                }
                MobileServiceType.HMS -> {
                    HuaweiSafetyServiceImpl(context)
                }
                else -> {
                    throw Exception("Unknown service")
                }
            }
        }
    }
}