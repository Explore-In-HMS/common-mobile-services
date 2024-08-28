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
package com.hms.lib.commonmobileservices.location.common

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType

/**
 * A data class representing common activity identification data.
 *
 * This class encapsulates information about identified activities and their corresponding possibilities.
 *
 * @property possibility The possibility of the identified activity.
 * @property identificationActivity The type of identified activity.
 */
class CommonActivityIdentificationData {

    companion object {
        var VEHICLE = "VEHICLE"
        var BIKE = "BIKE"
        var FOOT = "FOOT"
        var STILL = "STILL"
        var OTHERS = "OTHERS"
        var WALKING = "WALKING"
        var RUNNING = "RUNNING"
    }

    /**
     * Maps the activity type to its corresponding identifier.
     *
     * @param ctx The context used for accessing resources and services.
     * @param type The type of activity to be mapped.
     * @return The identifier corresponding to the activity type.
     */
    fun activityType(ctx: Context, type: String): Int {
        return if (Device.getMobileServiceType(ctx) == MobileServiceType.HMS) {
            when (type) {
                "VEHICLE" -> 100
                "BIKE" -> 101
                "FOOT" -> 102
                "STILL" -> 103
                "OTHERS" -> 104
                "WALKING" -> 107
                "RUNNING" -> 108
                else -> -1
            }
        } else {
            when (type) {
                "VEHICLE" -> 0
                "BIKE" -> 1
                "FOOT" -> 2
                "STILL" -> 3
                "OTHERS" -> 4
                "WALKING" -> 7
                "RUNNING" -> 8
                else -> -1
            }
        }
    }

    var possibility: Int? = null
    var identificationActivity: Int? = null
}