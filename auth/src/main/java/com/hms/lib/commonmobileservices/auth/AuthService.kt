// Copyright 2020. Explore in HMS. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

// http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.hms.lib.commonmobileservices.auth

import android.app.Activity
import android.content.Context
import com.hms.lib.commonmobileservices.auth.common.VerificationType
import com.hms.lib.commonmobileservices.auth.google.GoogleAuthServiceImpl
import com.hms.lib.commonmobileservices.auth.huawei.HuaweiAuthServiceImpl
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.Work
import java.util.*

interface AuthService {
    fun signInWithFacebook(accessToken: String): Work<AuthUser>
    fun signInWithGoogleOrHuawei(token: String): Work<AuthUser>
    fun signInWithEmail(email: String, password: String): Work<AuthUser>
    fun signInWithTwitter(token: String, secret: String): Work<AuthUser>
    fun signInWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser>

    fun signUp(
        email: String,
        password: String,
        locale: Locale? = Locale.ENGLISH
    ): Work<VerificationType>

    fun verifyCode(email: String, password: String, verifyCode: String): Work<Unit>
    fun resetPassword(email: String, locale: Locale? = Locale.ENGLISH): Work<VerificationType>
    fun verifyCodeToResetPassword(
        email: String,
        newPassword: String,
        verifyCode: String
    ): Work<Unit>

    fun anonymousSignIn(): Work<AuthUser>
    fun getUser(): AuthUser?
    fun signOut(): Work<Unit>
    fun updatePhoto(photo: String?): Work<Unit>
    fun updateUsername(username: String?): Work<Unit>
    fun updateEmail(email: String?, verifyCode: String?): Work<Unit>
    fun updatePhone(countryCode: String?, phoneNumber: String?, verifyCode: String?): Work<Unit>
    fun updatePasswordWithEmail(password: String?, verifyCode: String?): Work<Unit>
    fun updatePasswordWithPhone(password: String?, verifyCode: String?): Work<Unit>
    fun getCode(email: String?): Work<Unit>
    fun getCodePassword(email: String?): Work<Unit>
    fun getPhoneCode(countryCode: String?, phoneNumber: String?, activity: Activity): Work<Unit>
    fun deleteUser(): Work<Unit>
    fun reAuthenticate(email: String, password: String): Work<Unit>
    fun linkWithTwitter(token: String, secret: String): Work<AuthUser>
    fun linkWithFacebook(accessToken: String): Work<AuthUser>
    fun unlink(provider: String): Work<AuthUser>
    fun linkWithEmail(email: String, password: String, verifyCode: String): Work<AuthUser>
    fun linkWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser>
    object Factory {
        fun create(context: Context): AuthService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleAuthServiceImpl(context)
                }
                MobileServiceType.HMS -> {
                    HuaweiAuthServiceImpl()
                }
                else -> {
                    throw Exception("In order to use this SDK, google mobile services or huawei mobile services must be installed on your device.")
                }
            }
        }
    }
}