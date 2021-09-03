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

class HuaweiGoogleCrashManager(context: Context) {
    private val crashService: CrashService =
        CrashKitFactory().getCrashService(context, Device.getMobileServiceType(context, MobileServiceType.HMS))!!

    fun testIt(context:Context){
        crashService.testIt(context)
    }
    fun log(var1:String){
        crashService.log(var1)
    }
    fun log(var1:Int,var2:String){
        crashService.log(var1,var2)
    }
    fun setCustomKey(var1:String,var2:String){
        crashService.setCustomKey(var1,var2)
    }
    fun setCustomKey(var1:String,var2:Boolean){
        crashService.setCustomKey(var1,var2)
    }
    fun setCustomKey(var1:String,var2:Double){
        crashService.setCustomKey(var1,var2)
    }
    fun setCustomKey(var1:String,var2:Float){
        crashService.setCustomKey(var1,var2)
    }
    fun setCustomKey(var1:String,var2:Int){
        crashService.setCustomKey(var1,var2)
    }
    fun setCustomKey(var1:String,var2:Long){
        crashService.setCustomKey(var1,var2)
    }
    fun setCustomKeys(var1: CustomKeysAndValues){
        crashService.setCustomKeys(var1)
    }
    fun setUserId(var1:String){
        crashService.setUserId(var1)
    }
    fun recordException(var1:Throwable){
        crashService.recordException(var1)
    }
    fun enableCrashCollection(enable:Boolean){
        crashService.enableCrashCollection(enable)
    }
}