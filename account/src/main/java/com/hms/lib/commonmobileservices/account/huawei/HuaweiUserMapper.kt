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

import com.huawei.hms.support.hwid.result.AuthHuaweiId
import com.hms.lib.commonmobileservices.account.SignInUser
import com.hms.lib.commonmobileservices.account.common.Mapper
import com.hms.lib.commonmobileservices.account.common.Scope

private typealias HuaweiScope = com.huawei.hms.support.api.entity.auth.Scope

internal class HuaweiUserMapper : Mapper<AuthHuaweiId, SignInUser>() {
    override fun map(from: AuthHuaweiId): SignInUser = SignInUser(
        familyName = from.familyName,
        givenName = from.givenName,
        email = if (from.email == null) "" else from.email,
        displayName = from.displayName,
        id = from.unionId,
        photoUrl = from.avatarUri,
        authServiceToken = from.accessToken,
        idToken = from.idToken,
        accessToken = from.accessToken,
        scopes = getGrantedScopes(from)
    )

    private fun getGrantedScopes(account: AuthHuaweiId): Set<Scope> {
        val scopes = HashSet<Scope>()
        if (account.authorizedScopes.contains(HuaweiScope("email"))) {
            scopes.add(Scope.EMAIL)
        }
        return scopes
    }
}

