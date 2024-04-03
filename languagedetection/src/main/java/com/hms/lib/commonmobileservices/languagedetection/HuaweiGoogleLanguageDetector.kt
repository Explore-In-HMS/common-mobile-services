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
package com.hms.lib.commonmobileservices.languagedetection

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.languagedetection.factory.LanguageDetectionFactory
import com.hms.lib.commonmobileservices.languagedetection.implementation.GoogleLanguageIdentification
import com.hms.lib.commonmobileservices.languagedetection.implementation.HuaweiLanguageDetection
import com.hms.lib.commonmobileservices.languagedetection.implementation.ILanguageDetection
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.mlsdk.common.MLApplication

/**
 * Utility class for accessing language detection services provided by Huawei and Google.
 */
class HuaweiGoogleLanguageDetector private constructor() {

    companion object {

        /**
         * Retrieves a language detection client based on the mobile service type of the device.
         * @param context The context used for determining the mobile service type.
         * @param confidenceThreshold The confidence threshold to be applied to language detection.
         * @return An instance of ILanguageDetection corresponding to the mobile service type.
         * @throws IllegalArgumentException If the mobile service type is not supported.
         */
        fun getClient(
            context: Context,
            confidenceThreshold: Float? = null
        ): ILanguageDetection {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val languageDetectionFactory =
                        LanguageDetectionFactory.createFactory<GoogleLanguageIdentification>()
                    lateinit var googleLanguageIdentification: ILanguageDetection

                    confidenceThreshold?.let {
                        googleLanguageIdentification = languageDetectionFactory.create(it)
                    } ?: run { googleLanguageIdentification = languageDetectionFactory.create() }

                    googleLanguageIdentification
                }
                MobileServiceType.HMS -> {
                    MLApplication.getInstance().apiKey =
                        AGConnectServicesConfig.fromContext(context)
                            .getString("client/api_key")
                    val languageDetectionFactory =
                        LanguageDetectionFactory.createFactory<HuaweiLanguageDetection>()
                    lateinit var huaweiLanguageDetection: ILanguageDetection

                    confidenceThreshold?.let {
                        huaweiLanguageDetection = languageDetectionFactory.create(it)
                    } ?: run { huaweiLanguageDetection = languageDetectionFactory.create() }

                    huaweiLanguageDetection
                }
                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}