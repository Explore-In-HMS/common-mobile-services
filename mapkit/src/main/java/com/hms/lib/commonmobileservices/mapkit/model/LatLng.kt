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
package com.hms.lib.commonmobileservices.mapkit.model

/**
 * Represents a geographical point defined by its latitude and longitude coordinates.
 *
 * @property lat The latitude coordinate of the geographical point, in degrees.
 * @property lng The longitude coordinate of the geographical point, in degrees.
 */
data class LatLng(
    val lat: Double,
    val lng: Double
)
