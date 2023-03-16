package com.hms.lib.commonmobileservices.translate.common

sealed interface DownloadModelResult {
    object Success : DownloadModelResult
    data class Error(val exception: Exception) : DownloadModelResult
}