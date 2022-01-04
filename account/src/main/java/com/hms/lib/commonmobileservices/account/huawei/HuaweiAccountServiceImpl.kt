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

import android.app.Activity
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
import com.huawei.hms.support.hwid.result.HuaweiIdAuthResult
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.huawei.hms.support.sms.ReadSmsManager

internal class HuaweiAccountServiceImpl(context: Context, signInParams: SignInParams) :
    AccountService {

    private var mHuaweiIdAuthService: HuaweiIdAuthService
    private var mapper: Mapper<AuthHuaweiId, SignInUser> = HuaweiUserMapper()
    private val sharedPrefHelper = SharedPrefHelper(context)

    init {
        val helper = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
        if (signInParams.accessToken()) helper.setAccessToken()
        if (signInParams.email()) helper.setEmail()
        if (signInParams.idToken().isNotEmpty()) helper.setIdToken()
        mHuaweiIdAuthService = HuaweiIdAuthManager.getService(context, helper.createParams())

    }
    override fun silentSignIn(callback: ResultCallback<SignInUser>) {
        val task = mHuaweiIdAuthService.silentSignIn()
        task.addOnCanceledListener { callback.onCancelled() }
        task.addOnFailureListener { callback.onFailure(it) }
        task.addOnSuccessListener {
            sharedPrefHelper.setEmail(it.email)
            val loginResult = mapper.map(it)
            callback.onSuccess(loginResult)
        }
    }

    override fun getSignInIntent(intent: (Intent) -> Unit) {
        intent.invoke(mHuaweiIdAuthService.signInIntent)
    }

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
            val loginResult = mapper.map(it)
            callback.onSuccess(loginResult)
        }
    }

    override fun signOut(): Work<Unit> {
        val worker: Work<Unit> = Work()

        mHuaweiIdAuthService.signOut()
            .addOnSuccessListener { worker.onSuccess(Unit) }
            .addOnFailureListener { worker.onFailure(it) }
            .addOnCanceledListener { worker.onCanceled() }

        return worker
    }

    override fun cancelAuthorization(): Work<Unit> {
        val worker: Work<Unit> = Work()

        mHuaweiIdAuthService.cancelAuthorization()
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

    override fun startSmsRetriver(context: Context): Work<Unit> {
        val worker: Work<Unit> = Work()

        ReadSmsManager.start(context)
            .addOnSuccessListener { worker.onSuccess(Unit) }
            .addOnFailureListener { worker.onFailure(it) }
            .addOnCanceledListener { worker.onCanceled() }

        return worker
    }

    override fun startConsent(activity: Activity, phonenumber: String): Work<Unit> {
        val worker: Work<Unit> = Work()

        ReadSmsManager.startConsent(activity, phonenumber)
            .addOnSuccessListener { worker.onSuccess(Unit) }
            .addOnFailureListener { worker.onFailure(it) }
            .addOnCanceledListener { worker.onCanceled() }

        return worker
    }

    override fun getSignAccountId(): SignInUser? {
        return mapper.map(HuaweiIdAuthResult().huaweiId)
    }


    override fun isSuccesful(): Boolean {
        return HuaweiIdAuthResult().isSuccess
    }

    companion object {

    }
}