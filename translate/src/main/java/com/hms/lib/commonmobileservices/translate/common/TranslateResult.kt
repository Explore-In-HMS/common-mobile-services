package com.hms.lib.commonmobileservices.translate.common

sealed interface TranslateResult {
    data class Success(val translatedText: String) : TranslateResult
    data class Error(val exception: Exception) : TranslateResult
}