package com.huawei.hms.languagedetection.implementation

import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.core.ResultData
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector

internal class HuaweiLanguageDetection(
    private val languageDetector: MLRemoteLangDetector
): ILanguageDetection {

    override fun detectLanguage(
        sourceText: String,
        callback: (detectResult: ResultData<String>) -> Unit
    ) {
        val firstBestDetectTask = languageDetector.firstBestDetect(sourceText)
        firstBestDetectTask.addOnSuccessListener {
            callback(ResultData.Success(it))
        }.addOnFailureListener { e ->
            callback(
                ResultData.Failed(
                    error = e.localizedMessage,
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
        callback: (detectResult: ResultData<List<String>>) -> Unit
    ) {
        val probabilityDetectTask = languageDetector.probabilityDetect(sourceText)
        probabilityDetectTask.addOnSuccessListener { detectedLanguages ->
            callback(
                ResultData.Success(
                    detectedLanguages.map { it.langCode }
                )
            )
        }.addOnFailureListener { e ->
            callback(
                ResultData.Failed(
                    error = e.localizedMessage,
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