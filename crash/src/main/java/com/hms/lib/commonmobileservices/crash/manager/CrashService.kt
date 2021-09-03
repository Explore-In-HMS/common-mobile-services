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

interface CrashService {
    object Factory {
        fun create(context: Context): CrashService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleCrashKit(context)
                }
                MobileServiceType.HMS -> {
                    HuaweiCrashKit(context)
                }
                else -> {
                    throw Exception("In order to use this SDK, google mobile services or huawei mobile services must be installed on your device.")
                }
            }
        }
    }
    fun testIt(context:Context)
    fun log(var1:String)
    fun log(var1:Int,var2:String)
    fun setCustomKey(var1:String,var2:String)
    fun setCustomKey(var1:String,var2:Boolean)
    fun setCustomKey(var1:String,var2:Double)
    fun setCustomKey(var1:String,var2:Float)
    fun setCustomKey(var1:String,var2:Int)
    fun setCustomKey(var1:String,var2:Long)
    fun setCustomKeys(var1:CustomKeysAndValues)
    fun setUserId(var1:String)
    fun recordException(var1:Throwable)
    fun enableCrashCollection(enable:Boolean)
}