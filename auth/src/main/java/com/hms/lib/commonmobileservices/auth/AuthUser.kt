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

package com.hms.lib.commonmobileservices.auth

import com.hms.lib.commonmobileservices.auth.common.ProviderType
import com.hms.lib.commonmobileservices.auth.common.ServiceType

/**
 * Data class representing an authenticated user.
 *
 * @property id The unique identifier of the user.
 * @property displayName The display name of the user.
 * @property email The email address of the user.
 * @property phone The phone number of the user.
 * @property photoUrl The URL of the user's profile photo.
 * @property serviceType The type of mobile service used for authentication (GMS or HMS).
 * @property providerType The type of authentication provider used (Google, Huawei, Facebook, Twitter, Email, Phone, etc.).
 */
data class AuthUser(
    val id: String,
    val displayName: String,
    val email: String,
    val phone: String,
    val photoUrl: String,
    val serviceType: ServiceType,
    val providerType: ProviderType
)