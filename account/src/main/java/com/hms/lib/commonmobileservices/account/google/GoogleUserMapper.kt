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

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.hms.lib.commonmobileservices.account.SignInUser
import com.hms.lib.commonmobileservices.account.common.Mapper
import com.hms.lib.commonmobileservices.account.common.Scope

private typealias GoogleScope = com.google.android.gms.common.api.Scope

internal class GoogleUserMapper : Mapper<GoogleSignInAccount, SignInUser>() {
    override fun map(from: GoogleSignInAccount): SignInUser = SignInUser(
        familyName = from.familyName!!,
        givenName = from.givenName!!,
        email = if (from.email == null) "" else from.email,
        displayName = from.displayName!!,
        id = from.id!!,
        photoUrl = from.photoUrl!!,
        authServiceToken = if (from.idToken == null) "" else from.idToken!!,
        idToken = if (from.idToken == null) "" else from.idToken!!,
        accessToken = "",
        scopes = getGrantedScopes(from),
    )

    private fun getGrantedScopes(account: GoogleSignInAccount): Set<Scope> {
        val scopes = HashSet<Scope>()
        if (account.grantedScopes.contains(GoogleScope("email"))) {
            scopes.add(Scope.EMAIL)
        }
        return scopes
    }
}