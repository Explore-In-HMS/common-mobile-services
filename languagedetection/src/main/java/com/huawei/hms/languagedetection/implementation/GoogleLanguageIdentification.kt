package com.huawei.hms.languagedetection.implementation

import com.google.mlkit.nl.languageid.LanguageIdentifier

class GoogleLanguageIdentification(private val languageIdentifier: LanguageIdentifier):
    ILanguageDetection {
    override fun detectLanguage(text: String) {

    }
}