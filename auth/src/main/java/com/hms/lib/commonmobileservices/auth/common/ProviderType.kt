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

package com.hms.lib.commonmobileservices.auth.common

/**
 * Enumeration class representing different types of providers.
 * Each provider type has a corresponding value.
 * @property value the string representation of the provider type.
 */
enum class ProviderType(val value: String) {
    /**
     * No provider type.
     */
    NoProvider("non"),

    /**
     * Huawei provider type.
     */
    Huawei("huawei"),

    /**
     * Google provider type.
     */
    Google("google"),

    /**
     * Facebook provider type.
     */
    Facebook("facebook"),

    /**
     * Email provider type.
     */
    Email("email"),

    /**
     * Twitter provider type.
     */
    Twitter("twitter"),

    /**
     * Google Game provider type.
     */
    GoogleGame("googlegame")
}