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
package com.hms.lib.commonmobileservices.mapkit.helper

import com.google.maps.android.SphericalUtil
import com.hms.lib.commonmobileservices.mapkit.model.LatLng
import com.hms.lib.commonmobileservices.mapkit.model.toGMSLatLng

/**
 * Utility class for calculating distances between geographical points.
 */
class DistanceUtil {
    /**
     * Calculates the distance between two geographical points using the Haversine formula.
     *
     * @param p1 The first geographical point.
     * @param p2 The second geographical point.
     * @return The distance between the two points in meters.
     */
    fun calculateDistance(p1: LatLng, p2: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(p1.toGMSLatLng(), p2.toGMSLatLng())
    }
}