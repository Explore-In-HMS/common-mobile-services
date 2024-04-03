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

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultCallback
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.safety.common.CommonMaliciousAppResponse
import com.hms.lib.commonmobileservices.safety.common.CommonUrlCheckRes
import com.hms.lib.commonmobileservices.safety.common.CommonVerifyAppChecksEnabledRes
import com.hms.lib.commonmobileservices.safety.google.GoogleSafetyServiceImpl
import com.hms.lib.commonmobileservices.safety.huawei.HuaweiSafetyServiceImpl

/**
 * Interface defining methods for interacting with a safety service.
 */
interface SafetyService {

    /**
     * Detects if the user is safe.
     *
     * @param appKey The application key.
     * @param callback The callback to be invoked with the detection result.
     */
    fun userDetect(appKey: String, callback: ResultCallback<SafetyServiceResponse>)

    /**
     * Performs root detection.
     *
     * @param appKey The application key.
     * @param callback The callback to be invoked with the root detection result.
     */
    fun rootDetection(appKey: String, callback: ResultCallback<RootDetectionResponse>)

    /**
     * Retrieves a list of malicious apps.
     *
     * @param callback The callback to be invoked with the list of malicious apps.
     */
    fun getMaliciousAppsList(callback: ResultCallback<CommonMaliciousAppResponse>)

    /**
     * Checks if app checks are enabled.
     *
     * @param callback The callback to be invoked with the result of the check.
     */
    fun isAppChecksEnabled(callback: ResultCallback<CommonVerifyAppChecksEnabledRes>)

    /**
     * Enables app checks.
     *
     * @param callback The callback to be invoked with the result of the operation.
     */
    fun enableAppsCheck(callback: ResultCallback<CommonVerifyAppChecksEnabledRes>)

    /**
     * Initializes URL checking.
     *
     * @return A [Work] object representing the initialization process.
     */
    fun initURLCheck(): Work<Unit>

    /**
     * Checks the safety of a URL.
     *
     * @param url The URL to check.
     * @param appKey The application key.
     * @param threatType The type of threat to check for.
     * @param callback The callback to be invoked with the URL check result.
     */
    fun urlCheck(
        url: String,
        appKey: String,
        threatType: Int,
        callback: ResultCallback<CommonUrlCheckRes>
    )

    /**
     * Shuts down URL checking.
     *
     * @return A [Work] object representing the shutdown process.
     */
    fun shutDownUrlCheck(): Work<Unit>

    /**
     * Factory object responsible for creating instances of [SafetyService] implementations
     * based on the mobile service type obtained from the device.
     */
    object Factory {
        /**
         * Creates an instance of [SafetyService] based on the mobile service type.
         *
         * @param context The application context.
         * @return An instance of [SafetyService].
         * @throws Exception if the mobile service type is unknown.
         */
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