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
 * Represents a rectangular bounding box in geographical coordinates, defined by its southwest
 * and northeast corners.
 *
 * @property southwest The geographical point representing the southwest corner of the bounding box.
 * @property northeast The geographical point representing the northeast corner of the bounding box.
 */
data class LatLngBounds(
    val southwest: LatLng,
    val northeast: LatLng
)