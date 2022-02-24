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

class Geofence {

    companion object{
        const val ENTER_GEOFENCE_CONVERSION = 1
        const val EXIT_GEOFENCE_CONVERSION = 2
        const val DWELL_GEOFENCE_CONVERSION = 4
        const val GEOFENCE_NEVER_EXPIRE = -1L
    }

    var uniqueId: String? = null
    var conversions: Int? = null
    var validDuration: Long? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var radius: Float? = null
    var notificationInterval: Int? = null
    var dwellDelayTime: Int? = null

}
