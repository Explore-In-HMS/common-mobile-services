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
 * Represents the visible region on a map view, defined by the four corners of the viewport
 * (near left, near right, far left, and far right) and the bounds enclosing the visible area.
 *
 * @property nearLeft The geographical point at the top-left corner of the visible region.
 * @property nearRight The geographical point at the top-right corner of the visible region.
 * @property farLeft The geographical point at the bottom-left corner of the visible region.
 * @property farRight The geographical point at the bottom-right corner of the visible region.
 * @property bounds The bounds of the visible area, enclosing all visible geographical points.
 */
data class VisibleRegion(
    val nearLeft: LatLng,
    val nearRight: LatLng,
    var farLeft: LatLng,
    var farRight: LatLng,
    var bounds: LatLngBounds
)