package com.huawei.hms.languagedetection.implementation

import com.huawei.hms.languagedetection.common.DetectionResult
import com.huawei.hms.languagedetection.common.PossibleLanguage

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