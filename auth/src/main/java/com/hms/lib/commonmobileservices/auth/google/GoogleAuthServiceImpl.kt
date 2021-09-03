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

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.*
import com.hms.lib.commonmobileservices.auth.AuthService
import com.hms.lib.commonmobileservices.auth.AuthUser
import com.hms.lib.commonmobileservices.auth.common.Mapper
import com.hms.lib.commonmobileservices.auth.common.VerificationType
import com.hms.lib.commonmobileservices.auth.exception.ExceptionUtil
import com.hms.lib.commonmobileservices.core.Work
import java.util.*


class GoogleAuthServiceImpl : AuthService {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mapper: Mapper<FirebaseUser, AuthUser> = FirebaseUserMapper()

    override fun signInWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInWithCredential(FacebookAuthProvider.getCredential(accessToken))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun signInWithGoogleOrHuawei(token: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(token, null))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun signInWithEmail(email: String, password: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun signUp(email: String, password: String, locale: Locale?): Work<VerificationType> {
        val work: Work<VerificationType> = Work()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { work.onSuccess(VerificationType.NON) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun verifyCode(email: String, password: String, verifyCode: String): Work<Unit> {
        val work: Work<Unit> = Work()
        work.addOnFailureListener { ExceptionUtil.get(Exception("This method cannot be used with Firebase Auth Service")) }
        return work
    }

    override fun resetPassword(email: String, locale: Locale?): Work<VerificationType> {
        val work: Work<VerificationType> = Work()

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener { work.onSuccess(VerificationType.LINK) }
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
        work.addOnFailureListener { ExceptionUtil.get(Exception("This method cannot be used with Firebase Auth Service")) }
        return work
    }

    override fun anonymousSignIn(): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInAnonymously()
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }
        return work
    }

    override fun getUser(): AuthUser? {
        val user = firebaseAuth.currentUser
        return if (user == null) null else mapper.map(user)
    }

    override fun signOut(): Work<Unit> {
        val work: Work<Unit> = Work()

        firebaseAuth.signOut()
        work.onSuccess(Unit)

        return work
    }

    override fun updatePhoto(photo: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val user = FirebaseAuth.getInstance().currentUser

        if (photo != null) {
            val profileUpdates =
                UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(photo)).build()
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile picture updated.")
                    }
                }
        }
        return work
    }

    override fun updateUsername(username: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val user = FirebaseAuth.getInstance().currentUser

        if (username != null) {
            val profileUpdates =
                UserProfileChangeRequest.Builder().setDisplayName(username).build()
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Username updated.")
                    }
                }
        }
        return work
    }

    override fun updateEmail(email: String?, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val user = FirebaseAuth.getInstance().currentUser

        if (email != null) {
            user!!.updateEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                }
            }
        }
        return work
    }

    override fun updatePhone(
        countryCode: String?,
        phoneNumber: String?,
        verifyCode: String?,
        ): Work<Unit> {
        TODO("Not yet implemented")
    }

    override fun updatePasswordwEmail(password: String?,verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val user = FirebaseAuth.getInstance().currentUser

        if (password != null) {
            user!!.updatePassword(password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                }
            }
        }
        return work
    }

    override fun updatePasswordwPhone(password: String?, verifyCode: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        val user = FirebaseAuth.getInstance().currentUser

        if (password != null) {
            user!!.updatePassword(password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                }
            }
        }
        return work
    }

    override fun getCode(var1: String?): Work<Unit> {
        TODO("Not yet implemented")
    }

    override fun getCodePassword(var1: String?): Work<Unit> {
        TODO("Not yet implemented")
    }

    override fun getPhoneCode(var1: String?, var2: String?): Work<Unit> {
        TODO("Not yet implemented")
    }
}
