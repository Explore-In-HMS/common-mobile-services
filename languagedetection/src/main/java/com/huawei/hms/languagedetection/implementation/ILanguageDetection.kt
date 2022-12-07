package com.huawei.hms.languagedetection.implementation

import com.hms.lib.commonmobileservices.core.ResultData

interface ILanguageDetection {
    fun detectLanguage(
        sourceText: String,
        callback: (detectResult: ResultData<String>) -> Unit
    )
    fun detectPossibleLanguages(
        sourceText: String,
        callback: (detectResult: ResultData<List<String>>) -> Unit
    )
    fun stopDetector()
}