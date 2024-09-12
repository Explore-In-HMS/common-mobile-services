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

package com.hms.lib.commonmobileservices.auth.google

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.hms.lib.commonmobileservices.auth.AuthService
import com.hms.lib.commonmobileservices.auth.AuthUser
import com.hms.lib.commonmobileservices.auth.common.Mapper
import com.hms.lib.commonmobileservices.auth.common.VerificationType
import com.hms.lib.commonmobileservices.auth.exception.AuthException
import com.hms.lib.commonmobileservices.auth.exception.ExceptionUtil
import com.hms.lib.commonmobileservices.core.Work
import java.util.*
import java.util.concurrent.TimeUnit

private const val NO_EXISTING_USER = "There is no existing user."
private const val METHOD_ERROR = "This method cannot be used with Firebase Auth Service"
/**
 * A service implementation for authentication using Firebase Authentication. This class encapsulates the
 * functionality required to authenticate users via various methods including Google, Facebook, and phone number
 * verification. It leverages Firebase Authentication for handling the core authentication logic.
 *
 * @property firebaseAuth An instance of [FirebaseAuth] used for all authentication operations.
 * @property mapper A [Mapper] that converts [FirebaseUser] instances to [AuthUser] instances.
 * @property storedVerificationId A nullable String to store the verification ID from Firebase for phone number authentication.
 * @property resendToken A [PhoneAuthProvider.ForceResendingToken] used for resending the verification code in phone authentication.
 *                        This property is late-initialized as it is obtained asynchronously during the authentication process.
 *
 * The companion object contains constants used within the class:
 * @property CMS_SHARED_PREF The name of the shared preferences file used to persist data related to the authentication process.
 * @property VERIFICATION_ID The key used to store and retrieve the verification ID from shared preferences.
 */
class GoogleAuthServiceImpl(private val context: Context) : AuthService {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mapper: Mapper<FirebaseUser, AuthUser> = FirebaseUserMapper()
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    companion object {
        private const val CMS_SHARED_PREF = "CMS_Shared_Pref"
        private const val VERIFICATION_ID = "verificationId"
    }

    /**
     * Signs in a user using Facebook credentials.
     *
     * @param accessToken The Facebook access token obtained after successful authentication.
     * @return A [Work] object representing the asynchronous task of signing in.
     */
    override fun signInWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInWithCredential(FacebookAuthProvider.getCredential(accessToken))
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    work.onSuccess(mapper.map(it))
                } ?: run {
                    signOut()
                    work.onFailure(AuthException(NO_EXISTING_USER))
                }
            }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs in a user using Twitter credentials.
     *
     * @param token The Twitter access token obtained after successful authentication.
     * @param secret The Twitter secret token obtained after successful authentication.
     * @return A [Work] object representing the asynchronous task of signing in.
     */
    override fun signInWithTwitter(token: String, secret: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInWithCredential(TwitterAuthProvider.getCredential(token, secret))
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    work.onSuccess(mapper.map(it))
                } ?: run {
                    signOut()
                    work.onFailure(AuthException(NO_EXISTING_USER))
                }
            }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs in a user using Google or Huawei credentials.
     *
     * @param token The token obtained after successful Google or Huawei authentication.
     * @return A [Work] object representing the asynchronous task of signing in.
     */
    override fun signInWithGoogleOrHuawei(token: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(token, null))
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    work.onSuccess(mapper.map(it))
                } ?: run {
                    signOut()
                    work.onFailure(AuthException(NO_EXISTING_USER))
                }
            }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs in a user using email and password credentials.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A [Work] object representing the asynchronous task of signing in.
     */
    override fun signInWithEmail(email: String, password: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    work.onSuccess(mapper.map(it))
                } ?: run {
                    signOut()
                    work.onFailure(AuthException(NO_EXISTING_USER))
                }
            }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs in a user using phone number verification.
     *
     * @param countryCode The country code associated with the phone number.
     * @param phoneNumber The user's phone number.
     * @param password The user's password.
     * @param verifyCode The verification code sent to the user's phone number.
     * @return A [Work] object representing the asynchronous task of signing in.
     */
    override fun signInWithPhone(
        countryCode: String?,
        phoneNumber: String?,
        password: String?,
        verifyCode: String?
    ): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val preferences = context.getSharedPreferences(CMS_SHARED_PREF, Context.MODE_PRIVATE)
        val storedVerificationId = preferences.getString(VERIFICATION_ID, null)

        storedVerificationId?.let { verificationId ->
            verifyCode?.let { code ->
                val credential = PhoneAuthProvider.getCredential(verificationId, code)
                firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener { authResult ->
                        authResult.user?.let {
                            work.onSuccess(mapper.map(it))
                        } ?: run {
                            signOut()
                            work.onFailure(AuthException(NO_EXISTING_USER))
                        }
                    }
                    .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                    .addOnCanceledListener { work.onCanceled() }

            } ?: run { work.onFailure(AuthException("Verification code can not be empty.")) }
        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Signs up a user with email and password credentials.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @param locale The locale information of the user.
     * @return A [Work] object representing the asynchronous task of signing up.
     */
    override fun signUp(email: String, password: String, locale: Locale?): Work<VerificationType> {
        val work: Work<VerificationType> = Work()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { work.onSuccess(VerificationType.NON) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Signs up a user using phone number verification.
     *
     * @param countryCode The country code associated with the phone number.
     * @param phoneNumber The user's phone number.
     * @param password The user's password.
     * @param verifyCode The verification code sent to the user's phone number.
     * @return A [Work] object representing the asynchronous task of signing up.
     */
    override fun signUpWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<Unit> {
        val work: Work<Unit> = Work()
        work.addOnFailureListener { ExceptionUtil.get(Exception(METHOD_ERROR)) }
        return work
    }

    /**
     * Sends a verification email to the current user's email address.
     *
     * @param email The user's email address.
     * @param password The user's password (not used).
     * @param verifyCode The verification code (not used).
     * @return A [Work] object representing the asynchronous task of sending the verification email.
     */
    override fun verifyCode(email: String?, password: String?, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.currentUser?.let { user ->
            user.sendEmailVerification()
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email The user's email address.
     * @param locale The locale information of the user.
     * @return A [Work] object representing the asynchronous task of sending the password reset email.
     */
    override fun resetPassword(email: String, locale: Locale?): Work<VerificationType> {
        val work: Work<VerificationType> = Work()

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener { work.onSuccess(VerificationType.LINK) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * This method is not supported with Firebase Auth Service.
     *
     * @param email The user's email address (not used).
     * @param newPassword The new password (not used).
     * @param verifyCode The verification code (not used).
     * @return A [Work] object indicating that the method is not supported.
     */
    override fun verifyCodeToResetPassword(
        email: String,
        newPassword: String,
        verifyCode: String
    ): Work<Unit> {
        val work: Work<Unit> = Work()
        work.addOnFailureListener { ExceptionUtil.get(Exception(METHOD_ERROR)) }
        return work
    }

    /**
     * Signs in anonymously, without requiring any user credentials.
     *
     * @return A [Work] object representing the asynchronous task of signing in anonymously.
     */
    override fun anonymousSignIn(): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInAnonymously()
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    /**
     * Retrieves the currently signed-in user.
     *
     * @return The currently signed-in user, or null if there is no signed-in user.
     */
    override fun getUser(): AuthUser? {
        val user = firebaseAuth.currentUser
        return if (user == null) null else mapper.map(user)
    }

    /**
     * Signs out the current user.
     *
     * @return A [Work] object representing the asynchronous task of signing out.
     */
    override fun signOut(): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.signOut()
        work.onSuccess(Unit)

        return work
    }

    /**
     * Updates the profile photo of the current user.
     *
     * @param photo The URL of the new profile photo.
     * @return A [Work] object representing the asynchronous task of updating the profile photo.
     */
    override fun updatePhoto(photo: String): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.currentUser?.let { user ->
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(photo))
                .build()

            user.updateProfile(profileUpdates)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Updates the username (display name) of the current user.
     *
     * @param username The new username.
     * @return A [Work] object representing the asynchronous task of updating the username.
     */
    override fun updateUsername(username: String): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.currentUser?.let { user ->
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()

            user.updateProfile(profileUpdates)
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
     * @param verifyCode The verification code sent to the user's email address (not used).
     * @return A [Work] object representing the asynchronous task of updating the email address.
     */
    override fun updateEmail(email: String, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.currentUser?.let { user ->
            user.updateEmail(email)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Updates the phone number of the current user.
     *
     * @param countryCode The country code associated with the phone number (not used).
     * @param phoneNumber The new phone number (not used).
     * @param verifyCode The verification code sent to the user's phone number.
     * @return A [Work] object representing the asynchronous task of updating the phone number.
     */
    override fun updatePhone(
        countryCode: String?,
        phoneNumber: String?,
        verifyCode: String?
    ): Work<Unit> {

        val work: Work<Unit> = Work()
        val preferences = context.getSharedPreferences(CMS_SHARED_PREF, Context.MODE_PRIVATE)
        val storedVerificationId = preferences.getString(VERIFICATION_ID, null)

        firebaseAuth.currentUser?.let { user ->
            storedVerificationId?.let { verificationId ->
                verifyCode?.let { code ->
                    val credential = PhoneAuthProvider.getCredential(verificationId, code)
                    user.updatePhoneNumber(credential)
                        .addOnSuccessListener { work.onSuccess(Unit) }
                        .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                        .addOnCanceledListener { work.onCanceled() }

                } ?: run { work.onFailure(AuthException("Verification code can not be empty.")) }

            } ?: run { work.onFailure(AuthException("Unexpected authentication error")) }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Updates the password of the current user using email authentication.
     *
     * @param password The new password.
     * @param verifyCode The verification code sent to the user's email address (not used).
     * @return A [Work] object representing the asynchronous task of updating the password.
     */
    override fun updatePasswordWithEmail(password: String, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.currentUser?.let { user ->
            user.updatePassword(password)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Updates the password of the current user using phone authentication.
     *
     * @param password The new password.
     * @param verifyCode The verification code sent to the user's phone number (not used).
     * @return A [Work] object representing the asynchronous task of updating the password.
     */
    override fun updatePasswordWithPhone(password: String, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.currentUser?.let { user ->
            user.updatePassword(password)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: work.onFailure(AuthException(NO_EXISTING_USER))

        return work
    }

    /**
     * This method is not supported with Firebase Auth Service.
     *
     * @param email The user's email address (not used).
     * @return A [Work] object indicating that the method is not supported.
     */
    override fun getCode(email: String): Work<Unit> {
        val work: Work<Unit> = Work()
        work.addOnFailureListener { ExceptionUtil.get(Exception(METHOD_ERROR)) }
        return work
    }

    /**
     * This method is not supported with Firebase Auth Service.
     *
     * @param email The user's email address (not used).
     * @return A [Work] object indicating that the method is not supported.
     */
    override fun getCodePassword(email: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        work.addOnFailureListener { ExceptionUtil.get(Exception(METHOD_ERROR)) }
        return work
    }

    /**
     * Sends a verification code to the specified phone number for password reset.
     *
     * @param countryCode The country code associated with the phone number.
     * @param phoneNumber The phone number to which the verification code will be sent.
     * @param activity The activity context for the verification process.
     * @return A [Work] object representing the asynchronous task of sending the verification code.
     */
    override fun getPhoneCode(
        countryCode: String,
        phoneNumber: String,
        activity: Activity?
    ): Work<Unit> {
        val work: Work<Unit> = Work()

        activity?.let {
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(countryCode + phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

                    override fun onVerificationFailed(p0: FirebaseException) {
                        work.onFailure(ExceptionUtil.get(p0))
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationId, token)
                        storedVerificationId = verificationId
                        resendToken = token

                        val preferences =
                            context.getSharedPreferences(CMS_SHARED_PREF, Context.MODE_PRIVATE)
                        val editor = preferences.edit()
                        editor.putString(VERIFICATION_ID, storedVerificationId)
                        editor.apply()

                        work.onSuccess(Unit)
                    }
                }).build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        } ?: run { work.onFailure(AuthException("Activity can not be empty.")) }

        return work
    }

    /**
     * Deletes the current user account.
     *
     * @return A [Work] object representing the asynchronous task of deleting the user account.
     */
    override fun deleteUser(): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.currentUser?.let { user ->
            user.delete()
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Re-authenticates the current user with the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A [Work] object representing the asynchronous task of re-authenticating the user.
     */
    override fun reAuthenticate(email: String, password: String): Work<Unit> {
        val work: Work<Unit> = Work()

        val credential = EmailAuthProvider.getCredential(email, password)
        firebaseAuth.currentUser?.let { user ->
            user.reauthenticate(credential)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Links the current user account with Twitter credentials.
     *
     * @param token The Twitter access token.
     * @param secret The Twitter secret token.
     * @return A [Work] object representing the asynchronous task of linking the user account.
     */
    override fun linkWithTwitter(token: String, secret: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = TwitterAuthProvider.getCredential(token, secret)
        firebaseAuth.currentUser?.let { user ->
            user.linkWithCredential(credential)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let {
                        work.onSuccess(mapper.map(it))
                    } ?: run {
                        signOut()
                        work.onFailure(AuthException(NO_EXISTING_USER))
                    }
                }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }
        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Links the current user account with Facebook credentials.
     *
     * @param accessToken The Facebook access token.
     * @return A [Work] object representing the asynchronous task of linking the user account.
     */
    override fun linkWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = FacebookAuthProvider.getCredential(accessToken)
        firebaseAuth.currentUser?.let { user ->
            user.linkWithCredential(credential)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let {
                        work.onSuccess(mapper.map(it))
                    } ?: run {
                        signOut()
                        work.onFailure(AuthException(NO_EXISTING_USER))
                    }
                }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Unlinks the specified provider from the current user account.
     *
     * @param provider The provider ID of the provider to unlink (e.g., "twitter.com").
     * @return A [Work] object representing the asynchronous task of unlinking the provider.
     */
    override fun unlink(provider: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.currentUser?.let { user ->
            user.unlink(provider)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let {
                        work.onSuccess(mapper.map(it))
                    } ?: run {
                        signOut()
                        work.onFailure(AuthException(NO_EXISTING_USER))
                    }
                }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Links the current user account with email credentials.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @param verifyCode The verification code sent to the user's email address.
     * @return A [Work] object representing the asynchronous task of linking the user account.
     */
    override fun linkWithEmail(
        email: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = EmailAuthProvider.getCredential(email, password)
        firebaseAuth.currentUser?.let { user ->
            user.linkWithCredential(credential)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let {
                        work.onSuccess(mapper.map(it))
                    } ?: run {
                        signOut()
                        work.onFailure(AuthException(NO_EXISTING_USER))
                    }
                }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }

    /**
     * Links the current user account with phone credentials.
     *
     * @param countryCode The country code associated with the phone number.
     * @param phoneNumber The phone number to which the verification code was sent.
     * @param password The user's password.
     * @param verifyCode The verification code sent to the user's phone number.
     * @return A [Work] object representing the asynchronous task of linking the user account.
     */
    override fun linkWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val preferences = context.getSharedPreferences(CMS_SHARED_PREF, Context.MODE_PRIVATE)
        val storedVerificationId = preferences.getString(VERIFICATION_ID, null)

        firebaseAuth.currentUser?.let { user ->
            storedVerificationId?.let { verificationId ->
                val credential = PhoneAuthProvider.getCredential(verificationId, verifyCode)
                user.linkWithCredential(credential)
                    .addOnSuccessListener { authResult ->
                        authResult.user?.let {
                            work.onSuccess(mapper.map(it))
                        } ?: run {
                            signOut()
                            work.onFailure(AuthException(NO_EXISTING_USER))
                        }
                    }
                    .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                    .addOnCanceledListener { work.onCanceled() }

            } ?: run { work.onFailure(AuthException("Unexpected authentication error")) }

        } ?: run { work.onFailure(AuthException(NO_EXISTING_USER)) }

        return work
    }
}
