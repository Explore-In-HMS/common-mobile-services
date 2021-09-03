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

import android.util.Log
import com.hms.lib.commonmobileservices.auth.AuthService
import com.hms.lib.commonmobileservices.auth.AuthUser
import com.hms.lib.commonmobileservices.auth.common.Mapper
import com.hms.lib.commonmobileservices.auth.common.VerificationType
import com.hms.lib.commonmobileservices.auth.exception.ExceptionUtil
import com.hms.lib.commonmobileservices.core.Work
import com.huawei.agconnect.auth.*
import com.huawei.hmf.tasks.TaskExecutors
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

    override fun signUp(email: String, password: String, locale: Locale?): Work<VerificationType> {
        val work: Work<VerificationType> = Work()

        val settings: VerifyCodeSettings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
            .sendInterval(30)
            .locale(locale)
            .build()

        EmailAuthProvider.requestVerifyCode(email, settings)
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

        EmailAuthProvider.requestVerifyCode(email, settings)
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
        val user =agcConnectAuth.currentUser
        return if(user == null) null else mapper.map(user)
    }

    override fun signOut(): Work<Unit> {
        val work: Work<Unit> = Work()

        agcConnectAuth.signOut()
        work.onSuccess(Unit)

        return work
    }

    override fun updatePhoto(photo: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        if (AGConnectAuth.getInstance().currentUser != null && photo != null) {
            val userProfile = ProfileRequest.Builder()
                .setPhotoUrl(photo)
                .build()
            AGConnectAuth.getInstance().currentUser.updateProfile(userProfile)
                .addOnSuccessListener { Log.v("successUpdate", "successUpdate photo") }
                .addOnFailureListener { Log.e("errUpdate", "err photo")}
        } else
               Log.e("errUpdate", "Photo Empty")
        return work
    }

    override fun updateUsername(username: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        if (AGConnectAuth.getInstance().currentUser != null && username != null) {
            val userProfile = ProfileRequest.Builder()
                .setDisplayName(username)
                .build()
            AGConnectAuth.getInstance().currentUser.updateProfile(userProfile)
                .addOnSuccessListener { Log.v("successUpdate", "successUpdate username") }
                .addOnFailureListener { Log.e("errUpdate", "err username")}
        } else
            Log.e("errUpdate", "Username Empty")
        return work
    }

    override fun updateEmail(email: String?, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        if (AGConnectAuth.getInstance().currentUser != null && email != null && verifyCode != null) {
            AGConnectAuth.getInstance().currentUser.updateEmail(email, verifyCode)
                .addOnSuccessListener(
                    TaskExecutors.uiThread(),
                    { Log.v("successUpdate", "successUpdate Email") }).addOnFailureListener(
                    TaskExecutors.uiThread(),
                    { Log.e("errUpdate", "err Email:$it") })
        }
        return work
    }

    override fun updatePhone(
        countryCode: String?,
        phoneNumber: String?,
        verifyCode: String?
    ): Work<Unit> {
        val work: Work<Unit> = Work()
        if (AGConnectAuth.getInstance().currentUser != null && countryCode != null && phoneNumber != null && verifyCode != null) {
            AGConnectAuth.getInstance().currentUser.updatePhone(
                countryCode,
                phoneNumber,
                verifyCode
            ).addOnSuccessListener(TaskExecutors.uiThread(),
                { Log.v("successUpdate", "successUpdate phone") }).addOnFailureListener(
                TaskExecutors.uiThread(),
                { Log.e("errUpdate", "err phone:$it") })
        }
        return work
    }

    override fun updatePasswordwEmail(password: String?, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        if (AGConnectAuth.getInstance().currentUser != null && password != null && verifyCode != null) {
            AGConnectAuth.getInstance().currentUser.updatePassword(
                password,
                verifyCode,
                AGConnectAuthCredential.Email_Provider
            ).addOnSuccessListener {
                Log.v("successUpdate", "successUpdate password")
            }
            .addOnFailureListener {
                Log.e("errUpdate", "err password$it")
            }
        }
        return work
    }

    override fun updatePasswordwPhone(password: String?, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        if (AGConnectAuth.getInstance().currentUser != null && password != null && verifyCode != null) {
            AGConnectAuth.getInstance().currentUser.updatePassword(
                password,
                verifyCode,
                AGConnectAuthCredential.Phone_Provider
            ).addOnSuccessListener {
                Log.v("successUpdate", "successUpdate password")
            }
            .addOnFailureListener {
                Log.e("errUpdate", "err password$it")
            }
        }
        return work
    }

    override fun getCode(var1: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val settings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
            .build()
        val task = AGConnectAuth.getInstance().requestVerifyCode(var1, settings)
        task.addOnSuccessListener(
            TaskExecutors.uiThread(),
            { Log.v("successUpdate", "success getCode") }).
        addOnFailureListener(
            TaskExecutors.uiThread(),
            { Log.e("errUpdate", "err getCode:$it") })

        return work
    }

    override fun getCodePassword(var1: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val settings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_RESET_PASSWORD)
            .build()
        val task = AGConnectAuth.getInstance().requestVerifyCode(var1, settings)
        task.addOnSuccessListener(
            TaskExecutors.uiThread(),
            { Log.v("successUpdate", "success getCode") }).
        addOnFailureListener(
            TaskExecutors.uiThread(),
            { Log.e("errUpdate", "err getCode:$it") })

        return work
    }

    override fun getPhoneCode(var1: String?, var2: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val settings = VerifyCodeSettings.newBuilder()
            .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
            .sendInterval(30)
            .build()

        val task = AGConnectAuth.getInstance().requestVerifyCode(var1, var2, settings)
        task.addOnSuccessListener(
            TaskExecutors.uiThread(),
            { Log.v("successUpdate", "successUpdate getCode") }).addOnFailureListener(
            TaskExecutors.uiThread(),
            { Log.e("errUpdate", "err getCode:$it") })
        return work
    }
}