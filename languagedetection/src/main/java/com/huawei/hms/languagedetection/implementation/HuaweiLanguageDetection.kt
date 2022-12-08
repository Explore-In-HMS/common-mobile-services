package com.huawei.hms.languagedetection.implementation

import com.hms.lib.commonmobileservices.core.ErrorModel
import com.huawei.hms.languagedetection.common.DetectionResult
import com.huawei.hms.languagedetection.common.PossibleLanguage
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