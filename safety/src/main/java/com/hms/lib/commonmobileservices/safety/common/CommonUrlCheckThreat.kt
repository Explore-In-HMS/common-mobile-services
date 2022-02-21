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
package com.hms.lib.commonmobileservices.safety.common

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType

class CommonUrlCheckThreat {
    companion object{
        const val MALWARE_APPLICATIONS = "MALWARE_APPLICATIONS"
        const val PHISHING = "PHISHING"
    }

    fun urlThreatType(ctx: Context, type:String):Int{
        return if(Device.getMobileServiceType(ctx) == MobileServiceType.HMS){
            when(type){
                "MALWARE_APPLICATIONS" -> 1
                "PHISHING" -> 3
                else -> -1
            }
        }else{
            when(type){
                "MALWARE_APPLICATIONS" -> 4
                "PHISHING" -> 5
                else -> -1
            }
        }
    }

    var urlCheckResult:Int?=null
}