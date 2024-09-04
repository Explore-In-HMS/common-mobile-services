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

import java.lang.Exception

/**
 * A wrapper class for a polyline on the map.
 *
 * @property polylineImpl The underlying implementation of the polyline.
 */
class Polyline(val polylineImpl: Any) {

    /**
     * Hides the polyline.
     */
    fun hide() {
        when (polylineImpl) {
            is com.huawei.hms.maps.model.Polyline -> polylineImpl.isVisible = false
            is com.google.android.gms.maps.model.Polyline -> polylineImpl.isVisible = false
        }
    }

    /**
     * Shows the polyline.
     */
    fun show() {
        when (polylineImpl) {
            is com.huawei.hms.maps.model.Polyline -> polylineImpl.isVisible = true
            is com.google.android.gms.maps.model.Polyline -> polylineImpl.isVisible = true
        }
    }

    /**
     * Removes the polyline from the map.
     *
     * @return True if the polyline was successfully removed, false otherwise.
     */
    fun remove(): Boolean {
        return try {
            when (polylineImpl) {
                is com.huawei.hms.maps.model.Polyline -> polylineImpl.remove()
                is com.google.android.gms.maps.model.Polyline -> polylineImpl.remove()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Returns the ID of the polyline.
     *
     * @return The ID of the polyline.
     */
    fun getId(): String {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.id
            else -> (polylineImpl as GmsPolyline).id
        }
    }

    /**
     * Sets the list of points for the polyline.
     *
     * @param points The list of points to set.
     */
    fun setPoints(points: List<LatLng?>?) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.points = points?.map { it?.toHMSLatLng() }
            is GmsPolyline -> polylineImpl.points =
                points?.map { it?.toGMSLatLng() }?.toMutableList()
                    ?: mutableListOf()
        }
    }

    /**
     * Returns the list of points for the polyline.
     *
     * @return The list of points for the polyline.
     */
    fun getPoints(): List<LatLng> {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.points.map { it.toLatLng() }
            else -> (polylineImpl as GmsPolyline).points.map { it.toLatLng() }
        }
    }

    /**
     * Sets the width of the polyline.
     *
     * @param width The width to set.
     */
    fun setWidth(width: Float) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.width = width
            is GmsPolyline -> polylineImpl.width = width
        }
    }

    /**
     * Returns the width of the polyline.
     *
     * @return The width of the polyline.
     */
    fun getWidth(): Float {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.width
            else -> (polylineImpl as GmsPolyline).width
        }
    }

    /**
     * Sets the color of the polyline.
     *
     * @param color The color to set.
     */
    fun setColor(color: Int) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.color = color
            is GmsPolyline -> polylineImpl.color = color
        }
    }

    /**
     * Returns the color of the polyline.
     *
     * @return The color of the polyline.
     */
    fun getColor(): Int {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.color
            else -> (polylineImpl as GmsPolyline).color
        }
    }

    /**
     * Sets the joint type of the polyline.
     *
     * @param type The joint type to set.
     */
    fun setJointType(type: Int) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.jointType = type
            is GmsPolyline -> polylineImpl.jointType = type
        }
    }

    /**
     * Returns the joint type of the polyline.
     *
     * @return The joint type of the polyline.
     */
    fun getJointType(): Int {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.jointType
            else -> (polylineImpl as GmsPolyline).jointType
        }
    }

    /**
     * Sets the zIndex of the polyline.
     *
     * @param zIndex The new zIndex value.
     */
    fun setZIndex(zIndex: Float) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.zIndex = zIndex
            is GmsPolyline -> polylineImpl.zIndex = zIndex
        }
    }

    /**
     * Retrieves the zIndex of the polyline.
     *
     * @return The zIndex of the polyline.
     */
    fun getZIndex(): Float {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.zIndex
            else -> (polylineImpl as GmsPolyline).zIndex
        }
    }

    /**
     * Sets the visibility of the polyline.
     *
     * @param visible True to make the polyline visible, false to hide it.
     */
    fun setVisible(visible: Boolean) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.isVisible = visible
            is GmsPolyline -> polylineImpl.isVisible = visible
        }
    }

    /**
     * Checks if the polyline is visible.
     *
     * @return True if the polyline is visible, otherwise false.
     */
    fun isVisible(): Boolean {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.isVisible
            else -> (polylineImpl as GmsPolyline).isVisible
        }
    }

    /**
     * Sets whether the polyline should be drawn as a geodesic.
     *
     * @param geodesic True to draw the polyline as a geodesic, false otherwise.
     */
    fun setGeodesic(geodesic: Boolean) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.isGeodesic = geodesic
            is GmsPolyline -> polylineImpl.isGeodesic = geodesic
        }
    }

    /**
     * Checks if the polyline is drawn as a geodesic.
     *
     * @return True if the polyline is drawn as a geodesic, otherwise false.
     */
    fun isGeodesic(): Boolean {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.isGeodesic
            else -> (polylineImpl as GmsPolyline).isGeodesic
        }
    }

    /**
     * Sets whether the polyline should be clickable.
     *
     * @param clickable True to make the polyline clickable, false otherwise.
     */
    fun setClickable(clickable: Boolean) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.isClickable = clickable
            is GmsPolyline -> polylineImpl.isClickable = clickable
        }
    }

    /**
     * Checks if the polyline is clickable.
     *
     * @return True if the polyline is clickable, otherwise false.
     */
    fun isClickable(): Boolean {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.isClickable
            else -> (polylineImpl as GmsPolyline).isClickable
        }
    }

    /**
     * Sets a tag for the polyline.
     *
     * @param tag The tag to be set for the polyline.
     */
    fun setTag(tag: Any?) {
        when (polylineImpl) {
            is HmsPolyline -> polylineImpl.tag = tag
            is GmsPolyline -> polylineImpl.tag = tag
        }
    }

    /**
     * Retrieves the tag assigned to the polyline.
     *
     * @return The tag assigned to the polyline.
     */
    fun getTag(): Any? {
        return when (polylineImpl) {
            is HmsPolyline -> polylineImpl.tag
            else -> (polylineImpl as GmsPolyline).tag
        }
    }
}
