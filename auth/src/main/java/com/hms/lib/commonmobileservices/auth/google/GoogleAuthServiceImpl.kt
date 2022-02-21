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
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.hms.lib.commonmobileservices.auth.AuthService
import com.hms.lib.commonmobileservices.auth.AuthUser
import com.hms.lib.commonmobileservices.auth.common.Mapper
import com.hms.lib.commonmobileservices.auth.common.VerificationType
import com.hms.lib.commonmobileservices.auth.exception.ExceptionUtil
import com.hms.lib.commonmobileservices.core.Work
import java.util.*
import java.util.concurrent.TimeUnit


class GoogleAuthServiceImpl(private val context: Context) : AuthService {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mapper: Mapper<FirebaseUser, AuthUser> = FirebaseUserMapper()
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    companion object {
        private const val CMS_SHARED_PREF = "CMS_Shared_Pref"
        private const val VERIFICATION_ID = "verificationId"
    }


    init {


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.d("GFG", "onVerificationCompleted Success")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d("GFG", "onVerificationFailed  $p0")
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


            }

        }

    }


    override fun signInWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInWithCredential(FacebookAuthProvider.getCredential(accessToken))
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun signInWithTwitter(token: String, secret: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.signInWithCredential(TwitterAuthProvider.getCredential(token, secret))
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

    override fun signInWithPhone(
        countryCode: String,
        phoneNumber: String,
        password: String,
        verifyCode: String
    ): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val preferences = context.getSharedPreferences(CMS_SHARED_PREF, Context.MODE_PRIVATE)
        val storedVerificationId = preferences.getString(VERIFICATION_ID, null)

        val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, verifyCode)
        firebaseAuth.signInWithCredential(credential)
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
        val user = FirebaseAuth.getInstance().currentUser

        user!!.sendEmailVerification()
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }


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
        verifyCode: String?
    ): Work<Unit> {

        val work: Work<Unit> = Work()
        val preferences = context.getSharedPreferences(CMS_SHARED_PREF, Context.MODE_PRIVATE)
        val storedVerificationId = preferences.getString(VERIFICATION_ID, null)

        if (firebaseAuth.currentUser != null && storedVerificationId != null && verifyCode != null) {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, verifyCode)
            firebaseAuth.currentUser!!.updatePhoneNumber(credential)
                .addOnSuccessListener { work.onSuccess(Unit) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }
        }
        return work
    }

    override fun updatePasswordWithEmail(password: String?, verifyCode: String?): Work<Unit> {
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

    override fun updatePasswordWithPhone(password: String?, verifyCode: String?): Work<Unit> {
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

    override fun getCode(email: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        work.addOnFailureListener { ExceptionUtil.get(Exception("This method cannot be used with Firebase Auth Service")) }
        return work
    }

    override fun getCodePassword(email: String?): Work<Unit> {
        val work: Work<Unit> = Work()
        work.addOnFailureListener { ExceptionUtil.get(Exception("This method cannot be used with Firebase Auth Service")) }
        return work
    }

    override fun getPhoneCode(
        countryCode: String?,
        phoneNumber: String?,
        activity: Activity
    ): Work<Unit> {
        val work: Work<Unit> = Work()

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        return work
    }

    override fun deleteUser(): Work<Unit> {
        val work: Work<Unit> = Work()
        firebaseAuth.currentUser!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
        return work
    }

    override fun reAuthenticate(email: String, password: String): Work<Unit> {
        val work: Work<Unit> = Work()

        val credential = EmailAuthProvider.getCredential(email, password)
        firebaseAuth.currentUser!!.reauthenticate(credential)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun linkWithTwitter(token: String, secret: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = TwitterAuthProvider.getCredential(token, secret)
        firebaseAuth.currentUser!!.linkWithCredential(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun linkWithFacebook(accessToken: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        val credential = FacebookAuthProvider.getCredential(accessToken)
        firebaseAuth.currentUser!!.linkWithCredential(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
            .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
            .addOnCanceledListener { work.onCanceled() }

        return work
    }

    override fun unlink(provider: String): Work<AuthUser> {
        val work: Work<AuthUser> = Work()

        firebaseAuth.currentUser!!.unlink(provider)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
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

        val credential = EmailAuthProvider.getCredential(email, password)
        firebaseAuth.currentUser!!.linkWithCredential(credential)
            .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
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

        val preferences = context.getSharedPreferences(CMS_SHARED_PREF, Context.MODE_PRIVATE)
        val storedVerificationId = preferences.getString(VERIFICATION_ID, null)

        if (storedVerificationId != null) {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, verifyCode)
            firebaseAuth.currentUser!!.linkWithCredential(credential)
                .addOnSuccessListener { work.onSuccess(mapper.map(it.user!!)) }
                .addOnFailureListener { work.onFailure(ExceptionUtil.get(it)) }
                .addOnCanceledListener { work.onCanceled() }

        }
        return work
    }
}
