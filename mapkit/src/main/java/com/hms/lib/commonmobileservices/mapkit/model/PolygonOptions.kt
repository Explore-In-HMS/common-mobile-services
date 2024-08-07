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

import kotlin.collections.ArrayList

/**
 * Options for configuring the appearance and behavior of a polygon on a map.
 *
 * @property points The list of geographical points defining the vertices of the polygon.
 * @property holes An optional iterable of geographical points defining the vertices of holes within the polygon.
 * @property strokeColor The color of the polygon's outline in ARGB format. Default is black.
 * @property strokeWidth The width of the polygon's outline in pixels. Default is 10 pixels.
 * @property fillColor The color to fill the interior of the polygon in ARGB format. Default is transparent.
 * @property zIndex The z-index of the polygon. Polygons with higher z-index values are drawn above those with lower values.
 * @property isVisible Determines whether the polygon is visible on the map. Default is true.
 * @property isGeodesic Determines whether the edges of the polygon should be drawn as geodesic lines. Default is false.
 * @property isClickable Determines whether the polygon is clickable. If true, the polygon can be clicked to trigger events. Default is false.
 * @property strokeJointType The joint type for all line segments of the polygon's outline. Default is 0 (DEFAULT).
 */
class PolygonOptions {
    var points = ArrayList<LatLng>()
    var holes: Iterable<LatLng?>? = null
    var strokeColor: Int? = -16777216
    var strokeWidth: Float? = 10.0F
    var fillColor: Int? = 0
    var zIndex: Float? = 0.0F
    var isVisible: Boolean? = true
    var isGeodesic: Boolean? = false
    var isClickable: Boolean? = false
    var strokeJointType: Int? = 0
}


