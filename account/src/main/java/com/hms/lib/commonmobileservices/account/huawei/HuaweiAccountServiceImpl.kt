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
package com.hms.lib.commonmobileservices.account.huawei

import android.content.Context
import android.content.Intent
import com.hms.lib.commonmobileservices.account.AccountService
import com.hms.lib.commonmobileservices.account.SignInParams
import com.hms.lib.commonmobileservices.account.SignInUser
import com.hms.lib.commonmobileservices.account.common.Mapper
import com.hms.lib.commonmobileservices.account.util.SharedPrefHelper
import com.hms.lib.commonmobileservices.core.ResultCallback
import com.hms.lib.commonmobileservices.core.Work
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService

/**
 * Implementation of the [AccountService] interface for handling Huawei account operations.
 * This class provides methods to perform sign-in, sign-out, and retrieve account information.
 *
 * @property context The application context.
 * @property signInParams Parameters for signing in.
 */
internal class HuaweiAccountServiceImpl(context: Context, signInParams: SignInParams) :
    AccountService {

    private var mHuaweiIdAuthService: HuaweiIdAuthService
    private var mapper: Mapper<AuthHuaweiId, SignInUser> = HuaweiUserMapper()
    private val sharedPrefHelper = SharedPrefHelper(context)
    private var signInUser: SignInUser? = null

    /**
     * Initializes the HuaweiAccountServiceImpl instance.
     */
    init {
        val helper = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)

        // Configure authentication parameters based on signInParams
        if (signInParams.accessToken()) helper.setAccessToken()
        if (signInParams.email()) helper.setEmail()
        if (signInParams.idToken().isNotEmpty()) helper.setIdToken()

        // Get Huawei ID authentication service
        mHuaweiIdAuthService = HuaweiIdAuthManager.getService(context, helper.createParams())
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
        val task = mHuaweiIdAuthService.silentSignIn()
        task.addOnCanceledListener { callback.onCancelled() }
        task.addOnFailureListener { callback.onFailure(it) }
        task.addOnSuccessListener {
            sharedPrefHelper.setEmail(it.email)
            signInUser = mapper.map(it)
            callback.onSuccess(signInUser)
        }
    }

    /**
     * Retrieves the sign-in intent from Huawei ID authentication service.
     *
     * @param intent Callback to receive the sign-in intent.
     */
    override fun getSignInIntent(intent: (Intent) -> Unit) {
        intent.invoke(mHuaweiIdAuthService.signInIntent)
    }

    /**
     * Handles the result of a sign-in activity.
     *
     * @param intent The intent containing the result data.
     * @param callback Callback to handle the result of the sign-in operation.
     */
    override fun onSignInActivityResult(intent: Intent, callback: ResultCallback<SignInUser>) {
        val task = HuaweiIdAuthManager.parseAuthResultFromIntent(intent)
        task.addOnCanceledListener { callback.onCancelled() }
        task.addOnFailureListener { callback.onFailure(it) }
        task.addOnSuccessListener {
            if (it.email != null) {
                sharedPrefHelper.setEmail(it.email)
            } else {
                sharedPrefHelper.setEmail("")
            }
            signInUser = mapper.map(it)
            callback.onSuccess(signInUser)
        }
    }

    /**
     * Signs the user out.
     *
     * @return A [Work] instance representing the sign-out operation.
     */
    override fun signOut(): Work<Unit> {
        val worker: Work<Unit> = Work()

        mHuaweiIdAuthService.signOut()
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

        mHuaweiIdAuthService.cancelAuthorization()
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