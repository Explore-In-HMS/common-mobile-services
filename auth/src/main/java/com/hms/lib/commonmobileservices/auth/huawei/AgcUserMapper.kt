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

package com.hms.lib.commonmobileservices.auth.huawei

import com.hms.lib.commonmobileservices.auth.AuthUser
import com.hms.lib.commonmobileservices.auth.common.Mapper
import com.hms.lib.commonmobileservices.auth.common.ProviderType
import com.hms.lib.commonmobileservices.auth.common.ServiceType
import com.huawei.agconnect.auth.AGConnectAuthCredential
import com.huawei.agconnect.auth.AGConnectUser

class AgcUserMapper : Mapper<AGConnectUser, AuthUser>() {
    override fun map(from: AGConnectUser): AuthUser =
        AuthUser(
            id = from.uid,
            displayName = from.displayName ?: "",
            email = if (from.email == null) "" else from.email,
            photoUrl = from.photoUrl ?: "",
            serviceType = ServiceType.Huawei,
            providerType = getProvider(from)
        )

    private fun getProvider(user: AGConnectUser): ProviderType {
        return when (user.providerId) {
            AGConnectAuthCredential.HMS_Provider.toString() -> ProviderType.Huawei
            AGConnectAuthCredential.Facebook_Provider.toString() -> ProviderType.Facebook
            AGConnectAuthCredential.Twitter_Provider.toString() -> ProviderType.Twitter
            AGConnectAuthCredential.Email_Provider.toString() -> ProviderType.Email
            AGConnectAuthCredential.GoogleGame_Provider.toString() -> ProviderType.GoogleGame
            else -> ProviderType.NoProvider
        }
    }
}