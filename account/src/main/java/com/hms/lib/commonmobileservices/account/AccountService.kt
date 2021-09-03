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
package com.hms.lib.commonmobileservices.account

import android.content.Context
import android.content.Intent
import com.hms.lib.commonmobileservices.account.google.GoogleAccountServiceImpl
import com.hms.lib.commonmobileservices.account.huawei.HuaweiAccountServiceImpl
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultCallback
import com.hms.lib.commonmobileservices.core.Work

interface AccountService {
    fun silentSignIn(callback: ResultCallback<SignInUser>)
    fun getSignInIntent(intent: (Intent) -> Unit)
    fun onSignInActivityResult(intent: Intent, callback: ResultCallback<SignInUser>)
    fun signOut(): Work<Unit>
    fun cancelAuthorization(): Work<Unit>
    fun getEmail(): String?

    object Factory {
        fun create(context: Context, signInParams: SignInParams): AccountService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleAccountServiceImpl(context, signInParams)
                }
                MobileServiceType.HMS -> {
                    HuaweiAccountServiceImpl(context, signInParams)
                }
                else -> {
                    throw Exception("In order to use this SDK, google mobile services or huawei mobile services must be installed on your device.")
                }
            }
        }
    }
}