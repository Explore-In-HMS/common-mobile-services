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

import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions
import com.hms.lib.commonmobileservices.languagedetection.implementation.GoogleLanguageIdentification
import com.hms.lib.commonmobileservices.languagedetection.implementation.ILanguageDetection

/**
 * Factory class for creating instances of ILanguageDetection using Google's language detection services.
 */
class GoogleLanguageDetectorFactory : LanguageDetectionFactory() {

    /**
     * Creates an instance of ILanguageDetection without specifying a confidence threshold.
     * @return An instance of ILanguageDetection using Google's default language identification client.
     */
    override fun create(): ILanguageDetection {
        return GoogleLanguageIdentification(LanguageIdentification.getClient())
    }

    /**
     * Creates an instance of ILanguageDetection with a specified confidence threshold.
     * @param confidenceThreshold The confidence threshold to be applied to language identification.
     * @return An instance of ILanguageDetection using Google's language identification client with the specified confidence threshold.
     */
    override fun create(confidenceThreshold: Float): ILanguageDetection {
        val threshold = confidenceThreshold.coerceIn(0.0f, 1.0f)
        val options = LanguageIdentificationOptions.Builder()
            .setConfidenceThreshold(threshold)
            .build()
        val languageIdentifier = LanguageIdentification.getClient(options)

        return GoogleLanguageIdentification(languageIdentifier)
    }
}