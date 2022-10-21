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

class HuaweiAuthServiceImpl : AuthService {

    private val agcConnectAuth: AGConnectAuth = AGConnectAuth.getInstance()
    private val mapper: Mapper<AGConnectUser, AuthUser> = AgcUserMapper()

    override fun signInWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(FacebookAuthProvider.credentialWithToken(accessToken))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun signInWithTwitter(token: String, secret: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(TwitterAuthProvider.credentialWithToken(token, secret))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun signInWithGoogleOrHuawei(token: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(HwIdAuthProvider.credentialWithToken(token))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }
        return work
    }

    override fun signInWithEmail(email: String, password: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(EmailAuthProvider.credentialWithPassword(email, password))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun signInWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signIn(
            PhoneAuthProvider.credentialWithPassword(
                countryCode,
                phoneNumber,
                password
            )
        )
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

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

    override fun verifyCode(email: String, password: String, verifyCode: String): Work<Unit> {
        val work: Work<Unit> = Work()

        val user = EmailUser.Builder()
            .setEmail(email)
            .setPassword(password)
            .setVerifyCode(verifyCode)
            .build()

        agcConnectAuth.createUser(user)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

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

    override fun anonymousSignIn(): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.signInAnonymously()
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun getUser(): AuthUser? {
        val user = agcConnectAuth.currentUser
        return if (user == null) null else mapper.map(user)
    }

    override fun signOut(): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.signOut()
        work.onSuccess(Unit)

        return work
    }

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

        } ?: run { work.onFailure(AuthException("There is no existing user.")) }

        return work
    }

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

        } ?: run { work.onFailure(AuthException("There is no existing user.")) }

        return work
    }

    override fun updateEmail(email: String, verifyCode: String): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.currentUser?.let { user ->
            user.updateEmail(email, verifyCode)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException("There is no existing user.")) }

        return work
    }

    override fun updatePhone(
        phoneNumber: String,
        verifyCode: String,
        countryCode: String?
    ): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.currentUser?.let { user ->
            user.updatePhone(
                countryCode,
                phoneNumber,
                verifyCode
            )
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException("There is no existing user.")) }

        return work
    }

    override fun updatePasswordWithEmail(password: String, verifyCode: String): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.currentUser?.let { user ->
            user.updatePassword(
                password,
                verifyCode,
                AGConnectAuthCredential.Email_Provider
            )
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException("There is no existing user.")) }

        return work
    }

    override fun updatePasswordWithPhone(password: String, verifyCode: String): Work<Unit> {
        val work: Work<Unit> = Work()
        agcConnectAuth.currentUser?.let { user ->
            user.updatePassword(
                password,
                verifyCode,
                AGConnectAuthCredential.Phone_Provider
            )
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        } ?: run { work.onFailure(AuthException("There is no existing user.")) }

        return work
    }

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

    override fun getPhoneCode(
        phoneNumber: String,
        activity: Activity,
        countryCode: String
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

    override fun deleteUser(): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.deleteUser()
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun reAuthenticate(email: String, password: String): Work<Unit> {
        val work: Work<Unit> = Work()

        val credential = EmailAuthProvider.credentialWithPassword(email, password)

        agcConnectAuth.currentUser.reauthenticate(credential)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun linkWithTwitter(token: String, secret: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = TwitterAuthProvider.credentialWithToken(token, secret)
        agcConnectAuth.currentUser.link(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun linkWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = FacebookAuthProvider.credentialWithToken(accessToken)
        agcConnectAuth.currentUser.link(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

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

    override fun unlink(provider: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        agcConnectAuth.currentUser.unlink(provider.toInt())
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

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