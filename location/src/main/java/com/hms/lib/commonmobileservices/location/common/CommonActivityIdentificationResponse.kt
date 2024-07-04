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


/**
 * A class representing a response for common activity identification.
 *
 * This class encapsulates lists of activity identification data, the most identified activity,
 * and related time information.
 *
 * @property activityIdentificationDataList The list of activity identification data.
 * @property mostActivityIdentification The most identified activity.
 * @property time The time of the identification.
 * @property elapsedTimeFromReboot The elapsed time in milliseconds since the device reboot.
 */
class CommonActivityIdentificationResponse {
    var activityIdentificationDataList: List<CommonActivityIdentificationData>? = null
    var mostActivityIdentification: CommonActivityIdentificationData? = null
    var time: Long? = null
    var elapsedTimeFromReboot: Long? = null

    /**
     * Fetches common activity identification response from an intent.
     *
     * This function extracts activity identification data from the provided intent based on the mobile service provider.
     *
     * @param context The context used for accessing resources and services.
     * @param intent The intent from which to extract activity identification data.
     * @return A [CommonActivityIdentificationResponse] containing the extracted activity identification data.
     */
    fun fetchDataFromIntent(
        context: Context,
        intent: Intent
    ): CommonActivityIdentificationResponse? {
        return when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> ActivityIdentificationResponse.getDataFromIntent(intent)
                .toCommonActivityIdentificationResponse()

            else -> ActivityRecognitionResult.extractResult(intent)
                ?.toCommonActivityIdentificationResponse()
        }
    }
}