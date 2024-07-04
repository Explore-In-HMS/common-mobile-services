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

import android.net.Uri
import com.hms.lib.commonmobileservices.account.common.Scope

/**
 * Data class representing a signed-in user.
 *
 * @property familyName The family name of the user.
 * @property givenName The given name of the user.
 * @property email The email address of the user, can be null if not available.
 * @property displayName The display name of the user.
 * @property id The unique ID of the user.
 * @property photoUrl The URI of the user's profile photo, can be null if not available.
 * @property authServiceToken The authentication service token associated with the user.
 * @property idToken The ID token associated with the user.
 * @property accessToken The access token associated with the user.
 * @property scopes The set of scopes granted to the user.
 */
data class SignInUser(
    val familyName: String,
    val givenName: String,
    val email: String?,
    val displayName: String,
    val id: String,
    val photoUrl: Uri?,
    val authServiceToken: String,
    val idToken: String,
    val accessToken: String,
    val scopes: Set<Scope>
)