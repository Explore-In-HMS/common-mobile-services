package com.hms.lib.commonmobileservices.languagedetection.common

import com.hms.lib.commonmobileservices.core.ErrorModel

sealed class DetectionResult<out T> {
    data class Success<out T>(
        val data: T
    ): DetectionResult<T>()
    data class Error(
        val errorMessage: String? = null,
        val errorModel: ErrorModel? = null
    ): DetectionResult<Nothing>()
}
