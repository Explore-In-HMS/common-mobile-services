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

import android.location.Location

/**
 * Data class representing geofencing data.
 *
 * This class encapsulates information related to geofencing events, including error code, conversion type,
 * geofences involved in the conversion, converting location, and whether the conversion is a failure.
 *
 * @property errorCode The error code associated with the geofencing event.
 * @property conversion The conversion type of the geofencing event.
 * @property convertingGeofenceList The list of geofences involved in the conversion.
 * @property convertingLocation The location where the conversion occurred.
 * @property isFailure Indicates whether the geofencing conversion is a failure.
 */
data class GeofencingData(
    val errorCode: Int,
    val conversion: Int,
    val convertingGeofenceList: List<Geofence>,
    val convertingLocation: Location,
    val isFailure: Boolean
)



