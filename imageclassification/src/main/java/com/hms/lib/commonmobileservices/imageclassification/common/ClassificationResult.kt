package com.hms.lib.commonmobileservices.imageclassification.common

import com.hms.lib.commonmobileservices.core.ErrorModel

sealed class ClassificationResult<out T> {
    data class Success<out T>(
        val data: T
    ) : ClassificationResult<T>()

    data class Error(
        val errorMessage: String? = null,
        val errorModel: ErrorModel? = null
    ) : ClassificationResult<Nothing>()
}
