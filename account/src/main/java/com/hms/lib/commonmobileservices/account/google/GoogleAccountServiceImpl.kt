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

internal class GoogleAccountServiceImpl(private val context: Context, signInParams: SignInParams) :
    AccountService {

    private var mGoogleSignInClient: GoogleSignInClient
    private val mapper: Mapper<GoogleSignInAccount, SignInUser> = GoogleUserMapper()
    private val sharedPrefHelper = SharedPrefHelper(context)

    init {
        val helper = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        if (signInParams.email()) helper.requestEmail()
        if (signInParams.idToken().isNotEmpty()) helper.requestIdToken(signInParams.idToken())

        mGoogleSignInClient = GoogleSignIn.getClient(
            context,
            helper.build()
        )
    }

    override fun silentSignIn(callback: ResultCallback<SignInUser>) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account == null) {
            callback.onFailure(Exception("Get last signed account failed"))
        } else {
            sharedPrefHelper.setEmail(account.email)
            callback.onSuccess(mapper.map(account))
        }
    }

    override fun getSignInIntent(intent: (Intent) -> Unit) {
        intent.invoke(mGoogleSignInClient.signInIntent)
    }

    override fun onSignInActivityResult(intent: Intent, callback: ResultCallback<SignInUser>) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        task.addOnSuccessListener {
            sharedPrefHelper.setEmail(it.email)
            callback.onSuccess(mapper.map(task.result!!))
        }
        task.addOnFailureListener { callback.onFailure(task.exception!!) }
        task.addOnCanceledListener { callback.onCancelled() }
    }

    override fun signOut(): Work<Unit> {
        val worker: Work<Unit> = Work()
        mGoogleSignInClient.signOut()
            .addOnSuccessListener { worker.onSuccess(Unit) }
            .addOnFailureListener { worker.onFailure(it) }
            .addOnCanceledListener { worker.onCanceled() }

        return worker
    }

    override fun cancelAuthorization(): Work<Unit> {
        val worker: Work<Unit> = Work()
        mGoogleSignInClient.revokeAccess()
            .addOnSuccessListener { worker.onSuccess(Unit) }
            .addOnFailureListener { worker.onFailure(it) }
            .addOnCanceledListener { worker.onCanceled() }
        return worker
    }

    override fun getEmail(): String? {
        val email = sharedPrefHelper.getEmail()
        return if (email.isEmpty()) {
            null
        } else {
            email
        }
    }

}