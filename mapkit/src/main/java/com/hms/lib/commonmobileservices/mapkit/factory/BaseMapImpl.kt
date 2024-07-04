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
package com.hms.lib.commonmobileservices.mapkit.factory

import com.hms.lib.commonmobileservices.mapkit.helper.DistanceUtil
import com.hms.lib.commonmobileservices.mapkit.model.LatLng

/**
 * BaseMapImpl is an abstract class that implements the CommonMap interface.
 * It serves as a foundation for various map implementations by providing common functionality.
 */
abstract class BaseMapImpl : CommonMap {
    /**
     * Calculates the distance between two points on the map.
     *
     * @param p1 The first LatLng representing the starting point.
     * @param p2 The second LatLng representing the destination point.
     * @return The distance between the two points in meters.
     */
    override fun calculateDistanceBetweenPoints(p1: LatLng, p2: LatLng): Double {
        return DistanceUtil().calculateDistance(p1, p2)
    }
}