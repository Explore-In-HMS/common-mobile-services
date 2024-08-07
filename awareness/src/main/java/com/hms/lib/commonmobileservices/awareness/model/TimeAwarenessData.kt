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
 * Represents awareness data related to time.
 *
 * @property timeDataArray A mutable list containing integer values representing time data.
 * @constructor Creates an instance of [TimeAwarenessData] with the provided time data array.
 */
data class TimeAwarenessData(
    val timeDataArray: MutableList<Int>
) : Serializable

/**
 * Enumerates different values representing time categories.
 *
 * @property value The integer value associated with the time category.
 */
enum class TimeDataValue(val value: Int) {
    /**
     * Represents the morning time category.
     */
    TIME_CATEGORY_MORNING(1),

    /**
     * Represents the afternoon time category.
     */
    TIME_CATEGORY_AFTERNOON(2),

    /**
     * Represents the evening time category.
     */
    TIME_CATEGORY_EVENING(3),

    /**
     * Represents the night time category.
     */
    TIME_CATEGORY_NIGHT(4),

    /**
     * Represents the holiday time category.
     */
    TIME_CATEGORY_HOLIDAY(5),

    /**
     * Represents the weekday time category.
     */
    TIME_CATEGORY_WEEKDAY(6),

    /**
     * Represents the weekend time category.
     */
    TIME_CATEGORY_WEEKEND(7),

    /**
     * Represents the time category that is not a holiday.
     */
    TIME_CATEGORY_NOT_HOLIDAY(8);

    /**
     * Provides a way to retrieve a [TimeDataValue] enum instance based on its integer value.
     *
     * @param value The integer value associated with the time category.
     * @return The [TimeDataValue] enum instance corresponding to the given value, or null if not found.
     */
    companion object {
        fun valueOf(value: Int) = values().find { it.value == value }
    }
}
