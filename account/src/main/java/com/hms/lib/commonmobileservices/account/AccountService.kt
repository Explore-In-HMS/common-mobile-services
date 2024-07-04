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

/**
 * Interface defining common methods for handling account-related operations.
 */
interface AccountService {
    /**
     * Attempts to silently sign in the user.
     *
     * @param callback Callback to handle the result of the silent sign-in operation.
     */
    fun silentSignIn(callback: ResultCallback<SignInUser>)

    /**
     * Retrieves the sign-in intent.
     *
     * @param intent Callback to receive the sign-in intent.
     */
    fun getSignInIntent(intent: (Intent) -> Unit)

    /**
     * Handles the result of a sign-in activity.
     *
     * @param intent The intent containing the result data.
     * @param callback Callback to handle the result of the sign-in operation.
     */
    fun onSignInActivityResult(intent: Intent, callback: ResultCallback<SignInUser>)

    /**
     * Signs the user out.
     *
     * @return A [Work] instance representing the sign-out operation.
     */
    fun signOut(): Work<Unit>

    /**
     * Cancels the user's authorization.
     *
     * @return A [Work] instance representing the cancel authorization operation.
     */
    fun cancelAuthorization(): Work<Unit>

    /**
     * Retrieves the stored email address.
     *
     * @return The stored email address, or null if not found.
     */
    fun getEmail(): String?

    /**
     * Retrieves the signed-in user account information.
     *
     * @return The signed-in user account information, or null if not signed in.
     */
    fun getSignAccountId(): SignInUser?

    /**
     * Factory object for creating instances of AccountService.
     */
    object Factory {
        /**
         * Creates an instance of AccountService based on the context and sign-in parameters.
         *
         * @param context The application context.
         * @param signInParams Parameters for signing in.
         * @return An instance of AccountService.
         * @throws Exception If neither Google Mobile Services nor Huawei Mobile Services are installed.
         */
        fun create(context: Context, signInParams: SignInParams): AccountService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleAccountServiceImpl(context, signInParams)
                }
                MobileServiceType.HMS -> {
                    HuaweiAccountServiceImpl(context, signInParams)
                }
                else -> {
                    throw Exception("In order to use this SDK, Google Mobile Services or Huawei Mobile Services must be installed on your device.")
                }
            }
        }
    }
}