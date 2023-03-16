package com.hms.lib.commonmobileservices.translate.common

sealed interface DeleteModelResult {
    object Success : DeleteModelResult
    data class Error(val exception: Exception) : DeleteModelResult
}