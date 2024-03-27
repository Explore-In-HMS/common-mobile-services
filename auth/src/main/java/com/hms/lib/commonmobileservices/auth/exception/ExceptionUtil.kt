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

package com.hms.lib.commonmobileservices.auth.exception

/**
 * Utility object for handling exceptions related to authentication.
 */
object ExceptionUtil {
    /**
     * Get an [AuthException] based on the provided [Throwable].
     * @param exception the throwable representing the exception.
     * @return an [AuthException] with a relevant error message.
     */
    fun get(exception: Throwable): AuthException {
        exception.message?.let { errorMessage ->
            return when {
                errorMessage.contains("203818130") ||
                        errorMessage.contains("The email address is already in use by another account")
                -> AuthException("This email address is already in use.")
                errorMessage.contains("203818037") ||
                        errorMessage.contains("The password is invalid or the user does not have a password")
                -> AuthException("The password is invalid")
                errorMessage.contains("203818087") ||
                        errorMessage.contains("There is no existing user record corresponding to the provided identifier.")
                -> AuthException("There is no existing user.")
                errorMessage.contains("203816961") ||
                        errorMessage.contains("We have blocked all requests from this device due to unusual activity")
                -> AuthException("Unusual activity has been detected. All requests are blocked from this device. Try again later.")
                else -> AuthException(errorMessage)
            }
        } ?: run { return AuthException("Unexpected authentication error") }
    }
}