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
 * Represents awareness data related to user behavior.
 *
 * @property behaviourDataArray A mutable list containing integer values representing behavior data.
 * @constructor Creates an instance of [BehaviourAwarenessData] with the provided behavior data array.
 */
data class BehaviourAwarenessData(
    val behaviourDataArray: MutableList<Int>
) : Serializable

/**
 * Enumerates different values representing user behavior.
 *
 * @property value The integer value associated with the behavior.
 */
enum class BehaviourDataValue(val value: Int) {
    /**
     * Represents behavior while in a vehicle.
     */
    BEHAVIOR_IN_VEHICLE(0),

    /**
     * Represents behavior while on a bicycle.
     */
    BEHAVIOR_ON_BICYCLE(1),

    /**
     * Represents behavior while on foot.
     */
    BEHAVIOR_ON_FOOT(2),

    /**
     * Represents behavior while stationary.
     */
    BEHAVIOR_STILL(3),

    /**
     * Represents unknown behavior.
     */
    BEHAVIOR_UNKNOWN(4),

    /**
     * Represents behavior of walking.
     */
    BEHAVIOR_WALKING(7),

    /**
     * Represents behavior of running.
     */
    BEHAVIOR_RUNNING(8);

    /**
     * Provides a way to retrieve a [BehaviourDataValue] enum instance based on its integer value.
     *
     * @param value The integer value associated with the behavior.
     * @return The [BehaviourDataValue] enum instance corresponding to the given value, or null if not found.
     */
    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }
    }
}