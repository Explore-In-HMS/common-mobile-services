package com.hms.lib.commonmobileservices.auth.common

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.agconnect.auth.EmailAuthProvider

class CommonEmailAuthProvider {

    fun getCredential(ctx: Context, email: String, password: String): CommonAuthCredential {
        return when (Device.getMobileServiceType(ctx)) {
            MobileServiceType.HMS -> EmailAuthProvider.credentialWithPassword(email, password)
                .toCommonAuthCredential()
            else -> com.google.firebase.auth.EmailAuthProvider.getCredential(email, password)
                .toCommonAuthCredential()
        }
    }
}