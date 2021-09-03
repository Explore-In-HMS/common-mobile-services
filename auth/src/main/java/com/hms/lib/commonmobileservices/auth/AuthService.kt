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
    fun updatePasswordwEmail(password: String?, verifyCode: String?): Work<Unit>
    fun updatePasswordwPhone(password: String?, verifyCode: String?): Work<Unit>
    fun getCode(var1: String?): Work<Unit>
    fun getCodePassword(var1: String?): Work<Unit>
    fun getPhoneCode(var1: String?, var2: String?): Work<Unit>

    object Factory {
        fun create(context: Context): AuthService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleAuthServiceImpl()
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