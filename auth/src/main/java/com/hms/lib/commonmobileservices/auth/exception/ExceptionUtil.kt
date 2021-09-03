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

object ExceptionUtil {
    fun get(exception: Throwable): AuthException {
        return when {
            exception.message!!.contains("203818130") ||
                    exception.message!!.contains("The email address is already in use by another account")
            -> AuthException("This email address is already in use.")
            exception.message!!.contains("203818037") ||
                    exception.message!!.contains("The password is invalid or the user does not have a password")
            -> AuthException("The password is invalid")
            exception.message!!.contains("203818087") ||
                    exception.message!!.contains("There is no existing user record corresponding to the provided identifier. ")
            -> AuthException("There is no existing user.")
            exception.message!!.contains("203816961") ||
                    exception.message!!.contains("We have blocked all requests from this device due to unusual activity")
            -> AuthException("Unusual activity has been detected. All Requests are blocked from this device. Try later")
            else -> AuthException(exception.message!!)
        }
    }
}