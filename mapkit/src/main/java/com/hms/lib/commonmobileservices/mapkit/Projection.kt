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
package com.hms.lib.commonmobileservices.mapkit

import android.graphics.Point
import com.hms.lib.commonmobileservices.mapkit.model.*
import java.lang.Exception

/**
 * A utility class for converting between screen coordinates and geographic coordinates.
 *
 * @property projectionImpl The underlying implementation of the projection.
 */
class Projection(private val projectionImpl: Any) {
    /**
     * Converts a screen point to a geographical location.
     *
     * @param point The screen point to convert.
     * @return The geographical location corresponding to the screen point, or null if conversion fails.
     */
    fun fromScreenLocation(point: Point): LatLng? {
        return try {
            when (projectionImpl) {
                is HmsProjection -> projectionImpl.fromScreenLocation(point).toLatLng()
                is GmsProjection -> projectionImpl.fromScreenLocation(point).toLatLng()
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Converts a geographical location to a screen point.
     *
     * @param latLng The geographical location to convert.
     * @return The screen point corresponding to the geographical location, or null if conversion fails.
     */
    fun toScreenLocation(latLng: LatLng): Point? {
        return try {
            when (projectionImpl) {
                is HmsProjection -> projectionImpl.toScreenLocation(latLng.toHMSLatLng())
                is GmsProjection -> projectionImpl.toScreenLocation(latLng.toGMSLatLng())
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Retrieves the visible region on the screen.
     *
     * @return The visible region on the screen, or null if retrieval fails.
     */
    fun getVisibleRegion(): VisibleRegion? {
        return try {
            when (projectionImpl) {
                is HmsProjection -> projectionImpl.visibleRegion.toVisibleRegion()
                is GmsProjection -> projectionImpl.visibleRegion.toVisibleRegion()
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}