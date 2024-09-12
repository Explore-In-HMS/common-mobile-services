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

package com.hms.lib.commonmobileservices.auth.huawei

import android.app.Activity
import com.hms.lib.commonmobileservices.auth.AuthService
import com.hms.lib.commonmobileservices.auth.AuthUser
import com.hms.lib.commonmobileservices.auth.common.Mapper
import com.hms.lib.commonmobileservices.auth.common.VerificationType
import com.hms.lib.commonmobileservices.auth.exception.AuthException
import com.hms.lib.commonmobileservices.auth.exception.ExceptionUtil
import com.hms.lib.commonmobileservices.core.Work
import com.huawei.agconnect.auth.*
import java.util.*

private const val EMPTY_ERROR = "Verify code can not be empty."
private const val NO_EXISTING_USER = "There is no existing user."

/**
 * Implementation of AuthService for Huawei AGConnectAuth service.
 */
class HuaweiAuthServiceImpl : AuthService {

    private val agcConnectAuth: AGConnectAuth = AGConnectAuth.getInstance()
    private val mapper: Mapper<AGConnectUser, AuthUser> = AgcUserMapper()


    /**
     * Signs in using Facebook authentication provider.
     *
     * @param accessToken The Facebook access token.
     * @return A Work object representing the asynchronous sign-in operation.
     */
    override fun signInWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(FacebookAuthProvider.credentialWithToken(accessToken))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs in using Twitter authentication provider.
     *
     * @param token The Twitter access token.
     * @param secret The Twitter access token secret.
     * @return A Work object representing the asynchronous sign-in operation.
     */
    override fun signInWithTwitter(token: String, secret: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(TwitterAuthProvider.credentialWithToken(token, secret))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs in with a Google or Huawei account using the provided token.
     *
     * @param token The token associated with the Google or Huawei account.
     * @return A `Work` object representing the asynchronous sign-in operation.
     */
    override fun signInWithGoogleOrHuawei(token: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(HwIdAuthProvider.credentialWithToken(token))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs in with an email and password combination.
     *
     * @param email The email address of the user.
     * @param password The password associated with the email.
     * @return A `Work` object representing the asynchronous sign-in operation.
     */
    override fun signInWithEmail(email: String, password: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(EmailAuthProvider.credentialWithPassword(email, password))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs in with a phone number, country code, and password combination.
     *
     * @param countryCode The country code of the phone number.
     * @param phoneNumber The phone number of the user.
     * @param password The password associated with the phone number.
     * @param verifyCode The verification code sent to the phone number (optional).
     * @return A `Work` object representing the asynchronous sign-in operation.
     */
    override fun signInWithPhone(
        countryCode: String?,
        phoneNumber: String?,
        password: String?,
        verifyCode: String?
    ): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        countryCode?.let { code ->
            phoneNumber?.let { phone ->
                password?.let { pass ->
                    agcConnectAuth.signIn(
                        PhoneAuthProvider.credentialWithPassword(
                            code,
                            phone,
                            pass
                        )
                    )
                        .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
                        .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                        .addOnCanceledListener { work.onCanceled() }

                } ?: run { work.onFailure(AuthException("Country code can not be empty.")) }
            } ?: run { work.onFailure(AuthException("Phone number can not be empty.")) }
        } ?: run { work.onFailure(AuthException("Password can not be empty.")) }

        return work
    }

    /**
     * Initiates the sign-up process with an email and password, requesting a verification code.
     *
     * @param email The email address for the new account.
     * @param password The password for the new account.
     * @param locale The locale to use for sending the verification code (optional).
     * @return A `Work` object representing the asynchronous sign-up operation.
     */
    override fun signUp(email: String, password: String, locale: Locale?): Work<VerificationType> {
        val work: Work<VerificationType> = Work()

        val settings: VerifyCodeSettings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
            .sendInterval(30)
            .locale(locale)
            .build()

        agcConnectAuth.requestVerifyCode(email, settings)
            .addOnSuccessListener { work.onSuccess(VerificationType.CODE) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs up a user with a phone number, verifying the provided verification code.
     *
     * @param countryCode The country code associated with the phone number.
     * @param phoneNumber The phone number of the user.
     * @param password The password for the new account.
     * @param verifyCode The verification code sent to the phone number.
     * @return A `Work` object representing the asynchronous sign-up operation.
     */
    override fun signUpWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<Unit> {
        val work: Work<Unit> = Work()

        val phoneUser = PhoneUser.Builder()
            .setCountryCode(countryCode)
            .setPhoneNumber(phoneNumber)
            .setVerifyCode(verifyCode)
            .setPassword(password)
            .build()

        agcConnectAuth.createUser(phoneUser)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Verifies the provided verification code for resetting the password associated with the given email.
     *
     * @param email The email address of the user.
     * @param newPassword The new password to set.
     * @param verifyCode The verification code received for resetting the password.
     * @return A `Work` object representing the asynchronous verification operation.
     */
    override fun verifyCodeToResetPassword(
        email: String,
        newPassword: String,
        verifyCode: String
    ): Work<Unit> {
        val work: Work<Unit> = Work()

        AGConnectAuth.getInstance().resetPassword(email, newPassword, verifyCode)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Requests a verification code to reset the password associated with the given email address.
     *
     * @param email The email address for which to reset the password.
     * @param locale The locale to use for sending the verification code (optional).
     * @return A `Work` object representing the asynchronous request for a verification code.
     */
    override fun resetPassword(email: String, locale: Locale?): Work<VerificationType> {
        val work: Work<VerificationType> = Work()

        val settings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_RESET_PASSWORD)
            .sendInterval(30)
            .locale(locale)
            .build()

        agcConnectAuth.requestVerifyCode(email, settings)
            .addOnSuccessListener { work.onSuccess(VerificationType.CODE) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Verifies the provided verification code for creating a new account with the given email and password.
     *
     * @param email The email address for the new account.
     * @param password The password for the new account.
     * @param verifyCode The verification code received for creating the account.
     * @return A `Work` object representing the asynchronous verification operation.
     */
    override fun verifyCode(
        email: String?,
        password: String?,
        verifyCode: String?
    ): Work<Unit> {
        val work: Work<Unit> = Work()

        email?.let {
            verifyCode?.let {
                password?.let {
                    val user = EmailUser.Builder()
                        .setEmail(email)
                        .setPassword(password)
                        .setVerifyCode(verifyCode)
                        .build()

                    agcConnectAuth.createUser(user)
                        .addOnSuccessListener { work.onSuccess(Unit) }
                        .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                        .addOnCanceledListener { work.onCanceled() }

                } ?: run { work.onFailure(AuthException("Email can not be empty.")) }
            } ?: run { work.onFailure(AuthException(EMPTY_ERROR)) }
        } ?: run { work.onFailure(AuthException("Password can not be empty.")) }

        return work
    }

    /**
     * Signs in anonymously, creating a new anonymous user if one does not already exist.
     *
     * @return A `Work` object representing the asynchronous sign-in operation.
     */
    override fun anonymousSignIn(): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signInAnonymously()
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Retrieves the currently signed-in user, if any.
     *
     * @return The currently signed-in user, or null if no user is signed in.
     */
    override fun getUser(): AuthUser? {
        val user = agcConnectAuth.currentUser
        return if (user == null) null else mapper.map(user)
    }

    /**
     * Signs out the current user.
     *
     * @return A `Work` object representing the asynchronous sign-out operation.
     */
    override fun signOut(): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.signOut()
        work.onSuccess(Unit)

        return work
    }

    /**
     * Updates the photo URL of the current user.
     *
     * @param photo The new photo URL.
     * @return A `Work` object representing the asynchronous update operation.
     */
    override fun updatePhoto(photo: String): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.currentUser?.let { user ->
            val userProfile = ProfileRequest.Builder()
                .setPhotoUrl(photo)
                .build()

            user.updateProfile(userProfile)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Updates the display name (username) of the current user.
     *
     * @param username The new display name.
     * @return A `Work` object representing the asynchronous update operation.
     */
    override fun updateUsername(username: String): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.currentUser?.let { user ->
            val userProfile = ProfileRequest.Builder()
                .setDisplayName(username)
                .build()

            user.updateProfile(userProfile)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Updates the email address of the current user.
     *
     * @param email The new email address.
     * @param verifyCode The verification code for updating the email address (optional).
     * @return A `Work` object representing the asynchronous update operation.
     */
    override fun updateEmail(email: String, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.currentUser?.let { user ->
            user.updateEmail(email, verifyCode)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Updates the phone number of the current user.
     *
     * @param countryCode The country code of the phone number.
     * @param phoneNumber The new phone number.
     * @param verifyCode The verification code for updating the phone number.
     * @return A `Work` object representing the asynchronous update operation.
     */
    override fun updatePhone(
        countryCode: String?,
        phoneNumber: String?,
        verifyCode: String?
    ): Work<Unit> {
        val work: Work<Unit> = Work()

        countryCode?.let {
            verifyCode?.let {
                phoneNumber?.let {
                    agcConnectAuth.currentUser?.let { user ->
                        user.updatePhone(
                            countryCode,
                            phoneNumber,
                            verifyCode
                        )
                            .addOnSuccessListener { work.onSuccess(Unit) }
                            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                            .addOnCanceledListener { work.onCanceled() }

                    } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

                } ?: run { work.onFailure(AuthException("Phone number can not be empty.")) }
            } ?: run { work.onFailure(AuthException(EMPTY_ERROR)) }
        } ?: run { work.onFailure(AuthException("Country code can not be empty.")) }

        return work
    }

    /**
     * Updates the password of the current user using email verification.
     *
     * @param password The new password.
     * @param verifyCode The verification code for updating the password.
     * @return A `Work` object representing the asynchronous update operation.
     */
    override fun updatePasswordWithEmail(password: String, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()

        verifyCode?.let { code ->
            agcConnectAuth.currentUser?.let { user ->
                user.updatePassword(
                    password,
                    code,
                    AGConnectAuthCredential.Email_Provider
                )
                    .addOnSuccessListener { work.onSuccess(Unit) }
                    .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                    .addOnCanceledListener { work.onCanceled() }

            } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }
        } ?: run { work.onFailure(AuthException(EMPTY_ERROR)) }

        return work
    }

    /**
     * Updates the password of the current user using phone verification.
     *
     * @param password The new password.
     * @param verifyCode The verification code for updating the password.
     * @return A `Work` object representing the asynchronous update operation.
     */
    override fun updatePasswordWithPhone(password: String, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()

        verifyCode?.let { code ->
            agcConnectAuth.currentUser?.let { user ->
                user.updatePassword(
                    password,
                    code,
                    AGConnectAuthCredential.Phone_Provider
                )
                    .addOnSuccessListener { work.onSuccess(Unit) }
                    .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                    .addOnCanceledListener { work.onCanceled() }

            } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }
        } ?: run { work.onFailure(AuthException(EMPTY_ERROR)) }

        return work
    }

    /**
     * Requests a verification code to be sent to the provided email address for registration or login purposes.
     *
     * @param email The email address to which the verification code will be sent.
     * @return A `Work` object representing the asynchronous operation of requesting the verification code.
     */
    override fun getCode(email: String): Work<Unit> {
        val work: Work<Unit> = Work()
        val settings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
            .build()

        agcConnectAuth.requestVerifyCode(email, settings)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Requests a verification code to be sent to the provided email address for password reset purposes.
     *
     * @param email The email address to which the verification code will be sent.
     * @return A `Work` object representing the asynchronous operation of requesting the verification code.
     */
    override fun getCodePassword(email: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val settings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_RESET_PASSWORD)
            .build()

        agcConnectAuth.requestVerifyCode(email, settings)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Requests a verification code to be sent to the provided phone number for registration or login purposes.
     *
     * @param countryCode The country code of the phone number.
     * @param phoneNumber The phone number to which the verification code will be sent.
     * @param activity The activity context for verifying the phone number (optional).
     * @return A `Work` object representing the asynchronous operation of requesting the verification code.
     */
    override fun getPhoneCode(
        countryCode: String,
        phoneNumber: String,
        activity: Activity?
    ): Work<Unit> {
        val work: Work<Unit> = Work()
        val settings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
            .sendInterval(30)
            .build()

        agcConnectAuth.requestVerifyCode(countryCode, phoneNumber, settings)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Deletes the currently authenticated user account.
     *
     * @return A `Work` object representing the asynchronous operation of deleting the user account.
     */
    override fun deleteUser(): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.deleteUser()
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Re-authenticates the user using email and password.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @return A `Work` object representing the asynchronous re-authentication operation.
     */
    override fun reAuthenticate(email: String, password: String): Work<Unit> {
        val work: Work<Unit> = Work()

        val credential = EmailAuthProvider.credentialWithPassword(email, password)

        agcConnectAuth.currentUser.reauthenticate(credential)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Links the currently authenticated user account with Twitter.
     *
     * @param token The Twitter access token.
     * @param secret The Twitter access token secret.
     * @return A `Work` object representing the asynchronous operation of linking the account with Twitter.
     */
    override fun linkWithTwitter(token: String, secret: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = TwitterAuthProvider.credentialWithToken(token, secret)
        agcConnectAuth.currentUser.link(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Links the currently authenticated user account with Facebook.
     *
     * @param accessToken The Facebook access token.
     * @return A `Work` object representing the asynchronous operation of linking the account with Facebook.
     */
    override fun linkWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = FacebookAuthProvider.credentialWithToken(accessToken)
        agcConnectAuth.currentUser.link(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Links the currently authenticated user account with an email and password.
     *
     * @param email The email address to link.
     * @param password The password to link.
     * @param verifyCode The verification code for linking the email and password.
     * @return A `Work` object representing the asynchronous operation of linking the account with email and password.
     */
    override fun linkWithEmail(
        email: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = EmailAuthProvider.credentialWithVerifyCode(email, password, verifyCode)
        agcConnectAuth.currentUser.link(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Unlinks a specified provider from the currently authenticated user account.
     *
     * @param provider The provider ID to unlink (e.g., "2" for Twitter).
     * @return A `Work` object representing the asynchronous operation of unlinking the provider.
     */
    override fun unlink(provider: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.currentUser.unlink(provider.toInt())
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Links the currently authenticated user account with a phone number.
     *
     * @param countryCode The country code of the phone number.
     * @param phoneNumber The phone number to link.
     * @param password The password to link.
     * @param verifyCode The verification code for linking the phone number.
     * @return A `Work` object representing the asynchronous operation of linking the account with a phone number.
     */
    override fun linkWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = PhoneAuthProvider.credentialWithVerifyCode(
            countryCode,
            phoneNumber,
            password,
            verifyCode
        )
        agcConnectAuth.currentUser.link(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }
}