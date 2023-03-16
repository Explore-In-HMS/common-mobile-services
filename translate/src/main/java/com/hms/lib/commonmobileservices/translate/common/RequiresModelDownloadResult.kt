package com.hms.lib.commonmobileservices.translate.common

sealed interface RequiresModelDownloadResult {
    object Required : RequiresModelDownloadResult
    object NotRequired : RequiresModelDownloadResult
    data class Error(val exception: Exception) : RequiresModelDownloadResult
}