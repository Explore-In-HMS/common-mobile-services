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
 * Options for configuring the appearance and behavior of a polyline on a map.
 *
 * @property points The list of geographical points defining the vertices of the polyline.
 * @property color The color of the polyline in ARGB format. Default is black.
 * @property width The width of the polyline in pixels. Default is 10 pixels.
 * @property zIndex The z-index of the polyline. Polylines with higher z-index values are drawn above those with lower values.
 * @property isVisible Determines whether the polyline is visible on the map. Default is true.
 * @property isGeodesic Determines whether the polyline should be drawn as a geodesic line. Default is false.
 * @property isClickable Determines whether the polyline is clickable. If true, the polyline can be clicked to trigger events. Default is false.
 * @property jointType The joint type for all line segments of the polyline. Default is 0 (DEFAULT).
 */
class PolylineOptions {
    var points = ArrayList<LatLng>()
    var color: Int? = -16777216
    var width: Float? = 10.0F
    var zIndex: Float? = 0.0F
    var isVisible: Boolean? = true
    var isGeodesic: Boolean? = false
    var isClickable: Boolean? = false
    var jointType: Int? = 0
}
