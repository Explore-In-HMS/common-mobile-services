package com.hms.lib.commonmobileservices.languagedetection.factory

import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions
import com.hms.lib.commonmobileservices.languagedetection.implementation.GoogleLanguageIdentification
import com.hms.lib.commonmobileservices.languagedetection.implementation.ILanguageDetection

class GoogleLanguageDetectorFactory: LanguageDetectionFactory() {

    override fun create(): ILanguageDetection {
        return GoogleLanguageIdentification(LanguageIdentification.getClient())
    }

    override fun create(confidenceThreshold: Float): ILanguageDetection {
        val threshold = confidenceThreshold.coerceIn(0.0f,1.0f)
        val options = LanguageIdentificationOptions.Builder()
            .setConfidenceThreshold(threshold)
            .build()
        val languageIdentifier = LanguageIdentification.getClient(options)

        return GoogleLanguageIdentification(languageIdentifier)
    }
}