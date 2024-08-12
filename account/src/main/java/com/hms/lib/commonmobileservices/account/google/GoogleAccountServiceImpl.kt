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
package com.hms.lib.commonmobileservices.account.google

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.hms.lib.commonmobileservices.account.AccountService
import com.hms.lib.commonmobileservices.account.SignInParams
import com.hms.lib.commonmobileservices.account.SignInUser
import com.hms.lib.commonmobileservices.account.common.Mapper
import com.hms.lib.commonmobileservices.account.util.SharedPrefHelper
import com.hms.lib.commonmobileservices.core.ResultCallback
import com.hms.lib.commonmobileservices.core.Work

/**
 * Implementation of the [AccountService] interface for handling Google account operations.
 * This class provides methods to perform sign-in, sign-out, and retrieve account information.
 *
 * @property context The application context.
 * @property signInParams Parameters for signing in.
 */
internal class GoogleAccountServiceImpl(private val context: Context, signInParams: SignInParams) :
    AccountService {

    /**
     * Google sign-in client used for handling Google sign-in operations.
     */
    private var mGoogleSignInClient: GoogleSignInClient

    /**
     * Mapper used for mapping Google sign-in account to application-specific sign-in user.
     */
    private val mapper: Mapper<GoogleSignInAccount, SignInUser> = GoogleUserMapper()

    /**
     * Helper class for accessing shared preferences.
     */
    private val sharedPrefHelper = SharedPrefHelper(context)

    /**
     * Google SMS retriever client used for handling SMS retrieval.
     */
    private var mGoogleSmsRetriever: SmsRetrieverClient

    /**
     * The currently signed-in user. It is nullable as the user might not be signed in.
     */
    private var signInUser: SignInUser? = null

    /**
     * Initializes the GoogleAccountServiceImpl instance.
     *
     * @param context The application context.
     * @param signInParams Parameters for signing in.
     */
    init {
        val helper = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

        // Request email if specified in signInParams
        if (signInParams.email()) {
            helper.requestEmail()
        }

        // Request ID token if provided in signInParams
        if (signInParams.idToken().isNotEmpty()) {
            helper.requestIdToken(signInParams.idToken())
        }

        // Initialize Google SMS retriever client
        mGoogleSmsRetriever = SmsRetriever.getClient(context)

        // Initialize Google sign-in client with the configured options
        mGoogleSignInClient = GoogleSignIn.getClient(
            context,
            helper.build()
        )
    }

    /**
     * Attempts to silently sign in the user.
     *
     * If the user is already signed in, their information is retrieved and the callback's onSuccess method is called.
     * If the user is not signed in, the onFailure method of the callback is called.
     *
     * @param callback Callback to handle the result of the silent sign-in operation.
     */
    override fun silentSignIn(callback: ResultCallback<SignInUser>) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account == null) {
            callback.onFailure(Exception("Failed to get last signed account"))
        } else {
            account.email?.let { sharedPrefHelper.setEmail(it) }
            signInUser = mapper.map(account)
            callback.onSuccess(signInUser)
        }
    }

    /**
     * Retrieves the sign-in intent from Google sign-in client.
     *
     * @param intent Callback to receive the sign-in intent.
     */
    override fun getSignInIntent(intent: (Intent) -> Unit) {
        intent.invoke(mGoogleSignInClient.signInIntent)
    }

    /**
     * Handles the result of a sign-in activity.
     *
     * @param intent The intent containing the result data.
     * @param callback Callback to handle the result of the sign-in operation.
     */
    override fun onSignInActivityResult(intent: Intent, callback: ResultCallback<SignInUser>) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        task.addOnSuccessListener {
            it.email?.let { it1 -> sharedPrefHelper.setEmail(it1) }
            signInUser = mapper.map(task.result)
            callback.onSuccess(signInUser)
        }
        task.addOnFailureListener { callback.onFailure(task.exception!!) }
        task.addOnCanceledListener { callback.onCancelled() }
    }

    /**
     * Signs the user out.
     *
     * @return A [Work] instance representing the sign-out operation.
     */
    override fun signOut(): Work<Unit> {
        val worker: Work<Unit> = Work()
        mGoogleSignInClient.signOut()
            .addOnSuccessListener { worker.onSuccess(Unit) }
            .addOnFailureListener { worker.onFailure(it) }
            .addOnCanceledListener { worker.onCanceled() }

        return worker
    }

    /**
     * Cancels the user's authorization.
     *
     * @return A [Work] instance representing the cancel authorization operation.
     */
    override fun cancelAuthorization(): Work<Unit> {
        val worker: Work<Unit> = Work()
        mGoogleSignInClient.revokeAccess()
            .addOnSuccessListener { worker.onSuccess(Unit) }
            .addOnFailureListener { worker.onFailure(it) }
            .addOnCanceledListener { worker.onCanceled() }

        return worker
    }

    /**
     * Retrieves the stored email address.
     *
     * @return The stored email address, or null if not found.
     */
    override fun getEmail(): String? {
        val email = sharedPrefHelper.getEmail()
        return email.ifEmpty {
            null
        }
    }

    /**
     * Retrieves the signed-in user account information.
     *
     * @return The signed-in user account information, or null if not signed in.
     */
    override fun getSignAccountId(): SignInUser? {
        return signInUser
    }
}