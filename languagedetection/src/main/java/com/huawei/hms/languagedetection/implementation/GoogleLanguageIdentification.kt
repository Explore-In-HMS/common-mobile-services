package com.huawei.hms.languagedetection.implementation

import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.core.ResultData

internal class GoogleLanguageIdentification(
    private val languageIdentifier: LanguageIdentifier
) : ILanguageDetection {

    override fun detectLanguage(
        sourceText: String,
        callback: (detectResult: ResultData<String>) -> Unit
    ) {
        languageIdentifier.identifyLanguage(sourceText)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    callback(
                        ResultData.Failed(error = "Can't identify language.")
                    )
                } else {
                   callback( ResultData.Success(languageCode))
                }
            }
            .addOnFailureListener { e->
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
        languageIdentifier.identifyPossibleLanguages(sourceText)
            .addOnSuccessListener { identifiedLanguages ->
                callback(
                    ResultData.Success(
                        identifiedLanguages.map { it.languageTag }
                    )
                )
            }
            .addOnFailureListener { e ->
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

    override fun stopDetector() {}
}