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
 * Represents the position of the camera (the view) in a map view, including its target geographical
 * location, zoom level, tilt angle, and bearing (rotation) angle.
 *
 * @property target The geographical location that the camera is pointing to.
 * @property zoom The zoom level of the camera. This value represents the scale of the view.
 * @property tilt The tilt angle of the camera in degrees. A tilt of 0 degrees indicates a
 *     straight-down view (from top to bottom), while higher tilt angles provide oblique views.
 * @property bearing The bearing angle of the camera in degrees (clockwise rotation from the north
 *     direction). A bearing of 0 degrees indicates that the camera is pointing north.
 */
data class CameraPosition(
    val target: LatLng,
    val zoom: Float,
    val tilt: Float,
    val bearing: Float
)