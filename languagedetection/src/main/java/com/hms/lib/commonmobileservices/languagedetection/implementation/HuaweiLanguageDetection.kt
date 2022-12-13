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

import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.languagedetection.common.DetectionResult
import com.hms.lib.commonmobileservices.languagedetection.common.PossibleLanguage
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector

class HuaweiLanguageDetection(
    private val languageDetector: MLRemoteLangDetector
): ILanguageDetection {

    override fun detectLanguage(
        sourceText: String,
        callback: (detectResult: DetectionResult<String>) -> Unit
    ) {
        val firstBestDetectTask = languageDetector.firstBestDetect(sourceText)
        firstBestDetectTask.addOnSuccessListener {
            callback(DetectionResult.Success(it))
        }.addOnFailureListener { e ->
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
        val probabilityDetectTask = languageDetector.probabilityDetect(sourceText)
        probabilityDetectTask.addOnSuccessListener { detectedLanguages ->
            callback(
                DetectionResult.Success(
                    detectedLanguages.map {
                        PossibleLanguage(
                            langCode = it.langCode,
                            confidence = it.probability
                        )
                    }
                )
            )
        }.addOnFailureListener { e ->
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

    override fun stopDetector() {
        languageDetector.stop()
    }
}