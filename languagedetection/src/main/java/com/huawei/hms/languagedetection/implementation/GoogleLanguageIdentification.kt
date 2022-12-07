package com.huawei.hms.languagedetection.implementation

import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.hms.lib.commonmobileservices.core.ResultData

class GoogleLanguageIdentification(
    private val languageIdentifier: LanguageIdentifier
) : ILanguageDetection {

    override fun detectLanguage(
        sourceText: String,
        callback: (detectResult: ResultData<String>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun detectPossibleLanguages(
        sourceText: String,
        callback: (detectResult: ResultData<List<String>>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun stopDetector() {
        TODO("Not yet implemented")
    }
}