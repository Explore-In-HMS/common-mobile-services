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
package com.hms.lib.commonmobileservices.imageclassification.factory

import com.hms.lib.commonmobileservices.imageclassification.implementation.GoogleImageLabeling
import com.hms.lib.commonmobileservices.imageclassification.implementation.HuaweiImageClassification
import com.hms.lib.commonmobileservices.imageclassification.implementation.IImageClassification
import java.lang.IllegalArgumentException

/**
 * An abstract factory class for creating instances of image classification.
 *
 * This class provides abstract methods to create instances of [IImageClassification] with default options
 * and with custom confidence thresholds.
 */
abstract class ImageClassificationFactory {
    /**
     * Creates an instance of image classification with default options.
     *
     * @return An instance of [IImageClassification] for performing image classification.
     */
    abstract fun create(): IImageClassification

    /**
     * Creates an instance of image classification with a custom confidence threshold.
     *
     * @param confidenceThreshold The confidence threshold to be applied for classification. It should be a value
     * between 0.0 and 1.0.
     * @return An instance of [IImageClassification] for performing image classification with the specified confidence threshold.
     */
    abstract fun create(confidenceThreshold: Float): IImageClassification

    companion object {
        /**
         * Creates a factory instance based on the specified image classification type.
         *
         * @throws IllegalArgumentException if the specified image classification type is not supported.
         * @return An instance of [ImageClassificationFactory] for the specified image classification type.
         */
        inline fun <reified T : IImageClassification> createFactory(): ImageClassificationFactory =
            when (T::class) {
                HuaweiImageClassification::class -> HuaweiImageClassificationFactory()
                GoogleImageLabeling::class -> GoogleImageLabelingFactory()
                else -> throw IllegalArgumentException()
            }
    }
}