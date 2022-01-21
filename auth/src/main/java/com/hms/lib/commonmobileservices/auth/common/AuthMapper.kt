package com.hms.lib.commonmobileservices.auth.common

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.huawei.agconnect.auth.AGConnectAuthCredential

fun AuthCredential.toCommonAuthCredential(): CommonAuthCredential {
    return CommonAuthCredential(signInMethod, provider)
}

fun AGConnectAuthCredential.toCommonAuthCredential(): CommonAuthCredential {
    return CommonAuthCredential("", provider.toString())
}

fun CommonAuthCredential.toGMSAuthCredential(): AuthCredential {
    return signInMethod.let { provider.let { it1 -> EmailAuthProvider.getCredential(it, it1) } }
}

fun CommonAuthCredential.toHMSAuthCredential(): AGConnectAuthCredential {
    return signInMethod.let {
        provider.let { it1 ->
            com.huawei.agconnect.auth.EmailAuthProvider.credentialWithPassword(
                it,
                it1
            )
        }
    }
}