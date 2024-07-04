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

import java.io.Serializable

/**
 * Represents awareness data related to headset usage.
 *
 * @property headsetDataArray A mutable list containing integer values representing headset data.
 * @constructor Creates an instance of [HeadsetAwarenessData] with the provided headset data array.
 */
data class HeadsetAwarenessData(
    val headsetDataArray: MutableList<Int>
) : Serializable

/**
 * Enumerates different values representing headset usage.
 *
 * @property value The integer value associated with the headset usage.
 */
enum class HeadsetDataValue(val value: Int) {
    /**
     * Represents headset usage as false.
     */
    HEADSET_FALSE(0),

    /**
     * Represents headset usage as true.
     */
    HEADSET_TRUE(1);

    /**
     * Provides a way to retrieve a [HeadsetDataValue] enum instance based on its integer value.
     *
     * @param value The integer value associated with the headset usage.
     * @return The [HeadsetDataValue] enum instance corresponding to the given value, or null if not found.
     */
    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }
    }
}