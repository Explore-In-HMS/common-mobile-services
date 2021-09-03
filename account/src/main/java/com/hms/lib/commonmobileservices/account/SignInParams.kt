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

class SignInParams private constructor(
    private val accessToken: Boolean,
    private val email: Boolean,
    private val idToken: String
) {

    fun accessToken(): Boolean = this.accessToken

    fun email(): Boolean = this.email

    fun idToken(): String = this.idToken

    class Builder {
        private var accessToken: Boolean = false
        private var email: Boolean = false
        private var idToken: String = ""


        /**
         * Specifies that user profile info is requested by your application.
         */
        fun requestAccessToken(): Builder {
            this.accessToken = true
            return this
        }

        /**
         * Specifies that email info is requested by your application.
         */
        fun requestEmail(): Builder {
            this.email = true
            return this
        }

        /**
         * Specifies that an ID token for authenticated users is requested.
         * Requesting an ID token requires that the server client ID be specified.
         *
         * @param serverClientId The client ID of the server that will verify the integrity of the token.
         * This parameter is used only for Google Services
         */
        fun requestIdToken(serverClientId: String): Builder {
            this.idToken = if(serverClientId.isEmpty()){
                "true"
            }else{
                serverClientId
            }
            return this
        }

        fun create(): SignInParams {
            return SignInParams(
                accessToken,
                email,
                idToken
            )
        }
    }
}