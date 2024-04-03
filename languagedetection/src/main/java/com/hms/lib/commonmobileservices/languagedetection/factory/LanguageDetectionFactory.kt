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
package com.hms.lib.commonmobileservices.languagedetection.factory

import com.hms.lib.commonmobileservices.languagedetection.implementation.GoogleLanguageIdentification
import com.hms.lib.commonmobileservices.languagedetection.implementation.HuaweiLanguageDetection
import com.hms.lib.commonmobileservices.languagedetection.implementation.ILanguageDetection
import java.lang.IllegalArgumentException

/**
 * Abstract factory class for creating instances of ILanguageDetection.
 */
abstract class LanguageDetectionFactory {

    /**
     * Creates an instance of ILanguageDetection without specifying a confidence threshold.
     * @return An instance of ILanguageDetection.
     */
    abstract fun create(): ILanguageDetection

    /**
     * Creates an instance of ILanguageDetection with a specified confidence threshold.
     * @param confidenceThreshold The confidence threshold to be applied to language detection.
     * @return An instance of ILanguageDetection.
     */
    abstract fun create(confidenceThreshold: Float): ILanguageDetection

    /**
     * Companion object containing utility methods for creating LanguageDetectionFactory instances.
     */
    companion object {

        /**
         * Creates a specific LanguageDetectionFactory based on the type of ILanguageDetection.
         * @return An instance of LanguageDetectionFactory corresponding to the ILanguageDetection type.
         * @throws IllegalArgumentException If the ILanguageDetection type is not supported.
         */
        inline fun <reified T : ILanguageDetection> createFactory(): LanguageDetectionFactory =
            when (T::class) {
                HuaweiLanguageDetection::class -> HuaweiLanguageDetectorFactory()
                GoogleLanguageIdentification::class -> GoogleLanguageDetectorFactory()
                else -> throw IllegalArgumentException()
            }
    }
}