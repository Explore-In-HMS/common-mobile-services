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

/**
 * Interface defining authentication operations.
 */
interface AuthService {
    /**
     * Signs in with Facebook using the provided access token.
     */
    fun signInWithFacebook(accessToken: String): Work<AuthUser>

    /**
     * Signs in with Google or Huawei using the provided token.
     */
    fun signInWithGoogleOrHuawei(token: String): Work<AuthUser>

    /**
     * Signs in with email and password.
     */
    fun signInWithEmail(email: String, password: String): Work<AuthUser>

    /**
     * Signs in with Twitter using the provided token and secret.
     */
    fun signInWithTwitter(token: String, secret: String): Work<AuthUser>

    /**
     * Signs in with phone number, optionally providing country code, phone number, password, and verification code.
     */
    fun signInWithPhone(
        countryCode: String? = null,
        phoneNumber: String? = null,
        password: String? = null,
        verifyCode: String? = null
    ): Work<AuthUser>

    /**
     * Signs up with email and password, optionally specifying the locale.
     */
    fun signUp(
        email: String,
        password: String,
        locale: Locale? = Locale.ENGLISH
    ): Work<VerificationType>

    /**
     * Signs up with phone number, providing country code, phone number, password, and verification code.
     */
    fun signUpWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<Unit>

    /**
     * Verifies the provided email, password, and verification code.
     */
    fun verifyCode(
        email: String? = null,
        password: String? = null,
        verifyCode: String? = null
    ): Work<Unit>

    /**
     * Resets the password using the provided email, optionally specifying the locale.
     */
    fun resetPassword(email: String, locale: Locale? = Locale.ENGLISH): Work<VerificationType>

    /**
     * Verifies the provided email, sets the new password, and updates it.
     */
    fun verifyCodeToResetPassword(
        email: String,
        newPassword: String,
        verifyCode: String
    ): Work<Unit>

    /**
     * Signs in anonymously.
     */
    fun anonymousSignIn(): Work<AuthUser>

    /**
     * Retrieves the currently authenticated user.
     */
    fun getUser(): AuthUser?

    /**
     * Signs out the current user.
     */
    fun signOut(): Work<Unit>

    /**
     * Updates the user's profile photo.
     */
    fun updatePhoto(photo: String): Work<Unit>

    /**
     * Updates the user's username.
     */
    fun updateUsername(username: String): Work<Unit>

    /**
     * Updates the user's email address, optionally verifying it with a verification code.
     */
    fun updateEmail(email: String, verifyCode: String? = null): Work<Unit>

    /**
     * Updates the user's phone number, optionally providing country code, phone number, and verification code.
     */
    fun updatePhone(
        countryCode: String? = null,
        phoneNumber: String? = null,
        verifyCode: String? = null
    ): Work<Unit>

    /**
     * Updates the user's password using email, optionally providing a verification code.
     */
    fun updatePasswordWithEmail(password: String, verifyCode: String? = null): Work<Unit>

    /**
     * Updates the user's password using phone number, optionally providing a verification code.
     */
    fun updatePasswordWithPhone(password: String, verifyCode: String? = null): Work<Unit>

    /**
     * Requests a verification code for the specified email.
     */
    fun getCode(email: String): Work<Unit>

    /**
     * Requests a verification code to reset the password for the specified email.
     */
    fun getCodePassword(email: String?): Work<Unit>

    /**
     * Requests a verification code for the specified phone number and country code.
     */
    fun getPhoneCode(
        countryCode: String,
        phoneNumber: String,
        activity: Activity? = null
    ): Work<Unit>

    /**
     * Deletes the currently authenticated user account.
     */
    fun deleteUser(): Work<Unit>

    /**
     * Re-authenticates the user using email and password.
     */
    fun reAuthenticate(email: String, password: String): Work<Unit>

    /**
     * Links the currently authenticated user account with Twitter using the provided token and secret.
     */
    fun linkWithTwitter(token: String, secret: String): Work<AuthUser>

    /**
     * Links the currently authenticated user account with Facebook using the provided access token.
     */
    fun linkWithFacebook(accessToken: String): Work<AuthUser>

    /**
     * Unlinks a specified provider from the currently authenticated user account.
     */
    fun unlink(provider: String): Work<AuthUser>

    /**
     * Links the currently authenticated user account with an email and password.
     */
    fun linkWithEmail(email: String, password: String, verifyCode: String): Work<AuthUser>

    /**
     * Links the currently authenticated user account with a phone number.
     */
    fun linkWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser>

    /**
     * Factory object to create an instance of [AuthService] based on the mobile service type.
     */
    object Factory {
        /**
         * Creates an instance of [AuthService] based on the mobile service type.
         *
         * @param context The application context.
         * @return An instance of [AuthService].
         * @throws Exception If Google Mobile Services or Huawei Mobile Services are not installed on the device.
         */
        fun create(context: Context): AuthService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleAuthServiceImpl(context)
                }
                MobileServiceType.HMS -> {
                    HuaweiAuthServiceImpl()
                }
                else -> {
                    throw Exception("In order to use this SDK, Google Mobile Services or Huawei Mobile Services must be installed on your device.")
                }
            }
        }
    }
}