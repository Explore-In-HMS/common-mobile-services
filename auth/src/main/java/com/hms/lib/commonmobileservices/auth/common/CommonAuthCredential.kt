package com.hms.lib.commonmobileservices.auth.common

data class CommonAuthCredential(
    val signInMethod: String,
    val provider: String
)