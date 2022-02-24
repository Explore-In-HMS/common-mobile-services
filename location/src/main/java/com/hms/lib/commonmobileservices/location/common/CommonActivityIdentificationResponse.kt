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
package com.hms.lib.commonmobileservices.location.common

import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityRecognitionResult
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.location.ActivityIdentificationResponse

class CommonActivityIdentificationResponse {
    var activityIdentificationDataList: List<CommonActivityIdentificationData>?=null
    var mostActivityIdentification: CommonActivityIdentificationData?=null
    var time: Long?=null
    var elapsedTimeFromReboot: Long?=null

    fun fetchDataFromIntent(context:Context, intent:Intent): CommonActivityIdentificationResponse{
        return when(Device.getMobileServiceType(context)){
            MobileServiceType.HMS -> ActivityIdentificationResponse.getDataFromIntent(intent).toCommonActivityIdentificationResponse()
            else -> ActivityRecognitionResult.extractResult(intent)!!.toCommonActivityIdentificationResponse()
        }
    }
}