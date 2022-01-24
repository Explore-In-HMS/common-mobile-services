package com.hms.lib.commonmobileservices.auth.common

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.agconnect.auth.PhoneAuthProvider

class CommonPhoneAuthProvider {
    fun getCredential(
        ctx: Context,
        countryCode: String,
        phoneNumber: String,
        password: String
    ): CommonAuthCredential {
        return when (Device.getMobileServiceType(ctx)) {
            MobileServiceType.HMS -> PhoneAuthProvider.credentialWithPassword(
                countryCode,
                phoneNumber,
                password
            )
                .toCommonAuthCredential()
            else -> com.google.firebase.auth.PhoneAuthProvider.getCredential(countryCode, password)
                .toCommonAuthCredential()
        }
    }
}