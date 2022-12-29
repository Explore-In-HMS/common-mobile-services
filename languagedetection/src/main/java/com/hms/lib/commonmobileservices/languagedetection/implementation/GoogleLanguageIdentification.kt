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
package com.hms.lib.commonmobileservices.languagedetection.implementation

import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.languagedetection.common.DetectionResult
import com.hms.lib.commonmobileservices.languagedetection.common.PossibleLanguage

internal class GoogleLanguageIdentification(
    private val languageIdentifier: LanguageIdentifier
) : ILanguageDetection {

    override fun detectLanguage(
        sourceText: String,
        callback: (detectResult: DetectionResult<String>) -> Unit
    ) {
        languageIdentifier.identifyLanguage(sourceText)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    callback(
                        DetectionResult.Error(errorMessage = "Can't identify language.")
                    )
                } else {
                    callback(
                        DetectionResult.Success(languageCode)
                    )
                }
            }
            .addOnFailureListener { e ->
                callback(
                    DetectionResult.Error(
                        errorMessage = e.localizedMessage,
                        errorModel = ErrorModel(
                            message = e.message,
                            exception = e
                        )
                    )
                )
            }
    }

    override fun detectPossibleLanguages(
        sourceText: String,
        callback: (detectResult: DetectionResult<List<PossibleLanguage>>) -> Unit
    ) {
        languageIdentifier.identifyPossibleLanguages(sourceText)
            .addOnSuccessListener { identifiedLanguages ->
                callback(
                    DetectionResult.Success(
                        identifiedLanguages.map {
                            PossibleLanguage(
                                langCode = it.languageTag,
                                confidence = it.confidence
                            )
                        }
                    )
                )
            }
            .addOnFailureListener { e ->
                callback(
                    DetectionResult.Error(
                        errorMessage = e.localizedMessage,
                        errorModel = ErrorModel(
                            message = e.message,
                            exception = e
                        )
                    )
                )
            }
    }

    override fun stopDetector() {}
}