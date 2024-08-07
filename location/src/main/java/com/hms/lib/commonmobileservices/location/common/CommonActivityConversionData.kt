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

/**
 * A data class representing common activity conversion data.
 *
 * This class encapsulates information related to activity type, conversion type, and elapsed time from device reboot.
 *
 * @property getActivityType The type of activity.
 * @property getConversionType The type of conversion.
 * @property getElapsedTimeFromReboot The elapsed time in milliseconds since the device reboot.
 */
class CommonActivityConversionData {
    var getActivityType: Int? = null
    var getConversionType: Int? = null
    var getElapsedTimeFromReboot: Long? = null
}