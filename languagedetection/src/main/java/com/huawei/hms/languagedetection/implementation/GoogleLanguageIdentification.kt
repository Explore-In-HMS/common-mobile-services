package com.huawei.hms.languagedetection.implementation

import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.hms.lib.commonmobileservices.core.ErrorModel
import com.huawei.hms.languagedetection.common.DetectionResult
import com.huawei.hms.languagedetection.common.PossibleLanguage

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