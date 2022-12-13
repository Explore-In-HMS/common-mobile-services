package com.hms.lib.commonmobileservices.languagedetection.implementation

import com.hms.lib.commonmobileservices.languagedetection.common.DetectionResult
import com.hms.lib.commonmobileservices.languagedetection.common.PossibleLanguage

interface ILanguageDetection {
    fun detectLanguage(
        sourceText: String,
        callback: (detectResult: DetectionResult<String>) -> Unit
    )
    fun detectPossibleLanguages(
        sourceText: String,
        callback: (detectResult: DetectionResult<List<PossibleLanguage>>) -> Unit
    )
    fun stopDetector()
}