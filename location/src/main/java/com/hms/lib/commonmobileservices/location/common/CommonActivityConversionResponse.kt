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
import com.google.android.gms.location.ActivityTransitionResult
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.location.ActivityConversionResponse

/**
 * A class representing a response for common activity conversions.
 *
 * This class encapsulates a list of activity conversion data.
 *
 * @property getActivityConversionDataList The list of activity conversion data.
 */
class CommonActivityConversionResponse {

    var getActivityConversionDataList: List<CommonActivityConversionData>? = null

    /**
     * Fetches common activity conversion response from an intent.
     *
     * This function extracts activity conversion data from the provided intent based on the mobile service provider.
     *
     * @param context The context used for accessing resources and services.
     * @param intent The intent from which to extract activity conversion data.
     * @return A [CommonActivityConversionResponse] containing the extracted activity conversion data.
     */
    fun fetchDataFromIntent(context: Context, intent: Intent): CommonActivityConversionResponse {
        return when (Device.getMobileServiceType(context)) {
            MobileServiceType.HMS -> ActivityConversionResponse.getDataFromIntent(intent)
                .toCommonActivityConversionResponse()

            else -> ActivityTransitionResult.extractResult(intent)!!
                .toCommonActivityConversionResponse()
        }
    }
}