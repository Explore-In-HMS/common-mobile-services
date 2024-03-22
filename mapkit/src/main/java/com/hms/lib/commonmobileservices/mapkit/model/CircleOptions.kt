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
 * Options for configuring the appearance and behavior of a circle on a map.
 *
 * @property center The geographical location of the center of the circle.
 * @property radius The radius of the circle in meters.
 * @property strokeWidth The width of the circle's outline in pixels.
 * @property strokeColor The color of the circle's outline in ARGB format.
 * @property fillColor The color to fill the interior of the circle in ARGB format.
 * @property zIndex The z-index of the circle. Circles with higher z-index values are drawn above those with lower values.
 * @property isVisible Determines whether the circle is visible on the map.
 * @property isClickable Determines whether the circle is clickable. If true, the circle can be clicked to trigger events.
 */
class CircleOptions {
    var center: LatLng? = null
    var radius: Double? = 0.0
    var strokeWidth: Float? = 10.0F
    var strokeColor: Int? = -16777216
    var fillColor: Int? = 0
    var zIndex: Float? = 0.0F
    var isVisible: Boolean? = true
    var isClickable: Boolean? = false
}