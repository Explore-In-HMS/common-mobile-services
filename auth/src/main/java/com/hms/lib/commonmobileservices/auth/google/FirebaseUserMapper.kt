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

package com.hms.lib.commonmobileservices.auth.google

import com.google.firebase.auth.*
import com.hms.lib.commonmobileservices.auth.AuthUser
import com.hms.lib.commonmobileservices.auth.common.Mapper
import com.hms.lib.commonmobileservices.auth.common.ProviderType
import com.hms.lib.commonmobileservices.auth.common.ServiceType

/**
 * Mapper class for mapping [FirebaseUser] instances to [AuthUser] instances.
 */
class FirebaseUserMapper : Mapper<FirebaseUser, AuthUser> {
    /**
     * Maps a [FirebaseUser] instance to an [AuthUser] instance.
     * @param from the source [FirebaseUser] instance to be mapped.
     * @return the mapped [AuthUser] instance.
     */
    override fun map(from: FirebaseUser): AuthUser = AuthUser(
        id = from.uid,
        displayName = from.displayName ?: "",
        email = from.email ?: "",
        phone = from.phoneNumber ?: "",
        photoUrl = from.photoUrl?.toString() ?: "",
        serviceType = ServiceType.Google,
        providerType = if (!from.isAnonymous) getProvider(from) else ProviderType.NoProvider
    )

    /**
     * Determines the provider type of the given [FirebaseUser].
     * @param user the [FirebaseUser] instance.
     * @return the corresponding [ProviderType].
     */
    private fun getProvider(user: FirebaseUser): ProviderType {
        return when (user.providerData[1].providerId) {
            GoogleAuthProvider.PROVIDER_ID -> ProviderType.Google
            FacebookAuthProvider.PROVIDER_ID -> ProviderType.Facebook
            TwitterAuthProvider.PROVIDER_ID -> ProviderType.Twitter
            PlayGamesAuthProvider.PROVIDER_ID -> ProviderType.GoogleGame
            EmailAuthProvider.PROVIDER_ID -> ProviderType.Email
            else -> ProviderType.NoProvider
        }
    }
}