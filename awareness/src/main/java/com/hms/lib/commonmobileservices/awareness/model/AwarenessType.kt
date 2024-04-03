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

package com.hms.lib.commonmobileservices.awareness.model

/**
 * Enumerates different types of awareness.
 */
enum class AwarenessType {
    /**
     * Represents awareness related to weather conditions.
     */
    WEATHER,

    /**
     * Represents awareness related to user behavior.
     */
    BEHAVIOUR,

    /**
     * Represents awareness related to time.
     */
    TIME,

    /**
     * Represents awareness related to headset usage.
     */
    HEADSET,

    /**
     * Represents awareness related to location.
     */
    LOCATION
}