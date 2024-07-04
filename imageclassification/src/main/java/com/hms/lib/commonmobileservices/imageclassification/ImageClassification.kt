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
package com.hms.lib.commonmobileservices.imageclassification

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.imageclassification.factory.ImageClassificationFactory
import com.hms.lib.commonmobileservices.imageclassification.implementation.GoogleImageLabeling
import com.hms.lib.commonmobileservices.imageclassification.implementation.HuaweiImageClassification
import com.hms.lib.commonmobileservices.imageclassification.implementation.IImageClassification
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.mlsdk.common.MLApplication

/**
 * A utility class for obtaining instances of image classification based on the mobile service provider.
 *
 * This class provides a companion object with a method for obtaining an instance of [IImageClassification]
 * based on the mobile service provider (Google Mobile Services or Huawei Mobile Services).
 */
class ImageClassification {
    companion object {
        /**
         * Obtains an instance of image classification based on the mobile service provider.
         *
         * @param context The context used for accessing resources and services.
         * @param confidenceThreshold The confidence threshold to be applied for classification. It should be a value
         * between 0.0 and 1.0. Defaults to null.
         * @return An instance of [IImageClassification] for performing image classification.
         * @throws IllegalArgumentException if the mobile service provider is not supported.
         */
        fun getClient(
            context: Context,
            confidenceThreshold: Float? = null
        ): IImageClassification {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val imageLabelingFactory =
                        ImageClassificationFactory.createFactory<GoogleImageLabeling>()
                    lateinit var googleImageLabeling: IImageClassification

                    confidenceThreshold?.let {
                        googleImageLabeling = imageLabelingFactory.create(it)
                    } ?: run { googleImageLabeling = imageLabelingFactory.create() }

                    googleImageLabeling
                }
                MobileServiceType.HMS -> {
                    MLApplication.getInstance().apiKey =
                        AGConnectServicesConfig.fromContext(context)
                            .getString("client/api_key")
                    val imageClassificationFactory =
                        ImageClassificationFactory.createFactory<HuaweiImageClassification>()
                    lateinit var huaweiImageClassification: IImageClassification

                    confidenceThreshold?.let {
                        huaweiImageClassification = imageClassificationFactory.create(it)
                    } ?: run { huaweiImageClassification = imageClassificationFactory.create() }

                    huaweiImageClassification
                }
                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}