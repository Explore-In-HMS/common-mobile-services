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
 * Represents a polygon on the map.
 *
 * @property polygonImpl The underlying polygon implementation, which could be either a Huawei or Google Maps polygon.
 * @constructor Creates a Polygon with the specified polygon implementation.
 */
class Polygon(val polygonImpl: Any) {
    /**
     * Hides the polygon from the map.
     */
    fun hide() {
        when (polygonImpl) {
            is com.huawei.hms.maps.model.Polygon -> polygonImpl.isVisible = false
            is com.google.android.gms.maps.model.Polygon -> polygonImpl.isVisible = false
        }
    }

    /**
     * Shows the polygon on the map.
     */
    fun show() {
        when (polygonImpl) {
            is com.huawei.hms.maps.model.Polygon -> polygonImpl.isVisible = true
            is com.google.android.gms.maps.model.Polygon -> polygonImpl.isVisible = true
        }
    }

    /**
     * Removes the polygon from the map.
     *
     * @return true if the polygon was successfully removed, false otherwise.
     */
    fun remove(): Boolean {
        return try {
            when (polygonImpl) {
                is com.huawei.hms.maps.model.Polygon -> polygonImpl.remove()
                is com.google.android.gms.maps.model.Polygon -> polygonImpl.remove()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Gets the ID of the polygon.
     *
     * @return The ID of the polygon.
     */
    fun getId(): String {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.id
            else -> (polygonImpl as GmsPolygon).id
        }
    }

    /**
     * Sets the points of the polygon.
     *
     * @param points The list of points to set. Each point is represented by a LatLng object.
     */
    fun setPoints(points: List<LatLng?>?) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.points = points?.map { it?.toHMSLatLng() }
            is GmsPolygon -> polygonImpl.points =
                points?.map { it?.toGMSLatLng() } as MutableList<com.google.android.gms.maps.model.LatLng>
        }
    }

    /**
     * Gets the points of the polygon.
     *
     * @return The list of points of the polygon, where each point is represented by a LatLng object.
     */
    fun getPoints(): List<LatLng>? {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.points?.map { it.toLatLng() }
            else -> (polygonImpl as GmsPolygon).points?.map { it.toLatLng() }
        }
    }

    /**
     * Sets the holes of the polygon.
     *
     * @param holes The list of lists of points to set as holes. Each hole is represented by a list of LatLng objects.
     */
    fun setHoles(holes: List<List<LatLng?>?>?) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.holes =
                holes?.map { list -> list?.map { item -> item?.toHMSLatLng() } }
            is GmsPolygon -> polygonImpl.holes =
                holes?.map { list -> list?.map { item -> item?.toGMSLatLng() } } as MutableList<MutableList<com.google.android.gms.maps.model.LatLng>>
        }
    }

    /**
     * Gets the holes of the polygon.
     *
     * @return The list of lists of points representing the holes of the polygon. Each hole is represented by a list of LatLng objects.
     */
    fun getHoles(): List<List<LatLng>>? {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.holes.map { list -> list.map { item -> item.toLatLng() } }
            is GmsPolygon -> polygonImpl.holes.map { list -> list.map { item -> item.toLatLng() } }
            else -> null
        }
    }

    /**
     * Sets the stroke width of the polygon.
     *
     * @param width The width of the stroke in pixels.
     */
    fun setStrokeWidth(width: Float) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.strokeWidth = width
            is GmsPolygon -> polygonImpl.strokeWidth = width
        }
    }

    /**
     * Gets the stroke width of the polygon.
     *
     * @return The width of the stroke in pixels.
     */
    fun getStrokeWidth(): Float {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.strokeWidth
            else -> (polygonImpl as GmsPolygon).strokeWidth
        }
    }

    /**
     * Sets the stroke color of the polygon.
     *
     * @param color The color of the stroke as an integer value.
     */
    fun setStrokeColor(color: Int) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.strokeColor = color
            is GmsPolygon -> polygonImpl.strokeColor = color
        }
    }

    /**
     * Gets the stroke color of the polygon.
     *
     * @return The color of the stroke as an integer value.
     */
    fun getStrokeColor(): Int {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.strokeColor
            else -> (polygonImpl as GmsPolygon).strokeColor
        }
    }

    /**
     * Sets the joint type for all vertices of the polygon's outline.
     *
     * @param type The joint type for all vertices of the polygon's outline.
     */
    fun setStrokeJointType(type: Int) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.strokeJointType = type
            is GmsPolygon -> polygonImpl.strokeJointType = type
        }
    }

    /**
     * Gets the joint type for all vertices of the polygon's outline.
     *
     * @return The joint type for all vertices of the polygon's outline.
     */
    fun getStrokeJointType(): Int {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.strokeJointType
            else -> (polygonImpl as GmsPolygon).strokeJointType
        }
    }

    /**
     * Sets the fill color of the polygon.
     *
     * @param color The color of the fill as an integer value.
     */
    fun setFillColor(color: Int) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.fillColor = color
            is GmsPolygon -> polygonImpl.fillColor = color
        }
    }

    /**
     * Gets the fill color of the polygon.
     *
     * @return The color of the fill as an integer value.
     */
    fun getFillColor(): Int {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.fillColor
            else -> (polygonImpl as GmsPolygon).fillColor
        }
    }

    /**
     * Sets the zIndex of the polygon.
     *
     * @param zIndex The zIndex of the polygon.
     */
    fun setZIndex(zIndex: Float) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.zIndex = zIndex
            is GmsPolygon -> polygonImpl.zIndex = zIndex
        }
    }

    /**
     * Gets the zIndex of the polygon.
     *
     * @return The zIndex of the polygon.
     */
    fun getZIndex(): Float {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.zIndex
            else -> (polygonImpl as GmsPolygon).zIndex
        }
    }

    /**
     * Sets the visibility of the polygon.
     *
     * @param visible True if the polygon is visible, false otherwise.
     */
    fun setVisible(visible: Boolean) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.isVisible = visible
            is GmsPolygon -> polygonImpl.isVisible = visible
        }
    }

    /**
     * Returns whether the polygon is visible.
     *
     * @return True if the polygon is visible, false otherwise.
     */
    fun isVisible(): Boolean {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.isVisible
            else -> (polygonImpl as GmsPolygon).isVisible
        }
    }

    /**
     * Sets whether the polygon should be drawn as a geodesic shape.
     *
     * @param geodesic True if the polygon should be drawn as a geodesic shape, false otherwise.
     */
    fun setGeodesic(geodesic: Boolean) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.isGeodesic = geodesic
            is GmsPolygon -> polygonImpl.isGeodesic = geodesic
        }
    }

    /**
     * Returns whether the polygon is drawn as a geodesic shape.
     *
     * @return True if the polygon is drawn as a geodesic shape, false otherwise.
     */
    fun isGeodesic(): Boolean {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.isGeodesic
            else -> (polygonImpl as GmsPolygon).isGeodesic
        }
    }

    /**
     * Sets whether the polygon is clickable.
     *
     * @param clickable True if the polygon is clickable, false otherwise.
     */
    fun setClickable(clickable: Boolean) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.isClickable = clickable
            is GmsPolygon -> polygonImpl.isClickable = clickable
        }
    }

    /**
     * Returns whether the polygon is clickable.
     *
     * @return True if the polygon is clickable, false otherwise.
     */
    fun isClickable(): Boolean {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.isClickable
            else -> (polygonImpl as GmsPolygon).isClickable
        }
    }

    /**
     * Sets the tag of the polygon.
     *
     * @param tag The tag to set.
     */
    fun setTag(tag: Any?) {
        when (polygonImpl) {
            is HmsPolygon -> polygonImpl.tag = tag
            is GmsPolygon -> polygonImpl.tag = tag
        }
    }

    /**
     * Returns the tag of the polygon.
     *
     * @return The tag of the polygon.
     */
    fun getTag(): Any? {
        return when (polygonImpl) {
            is HmsPolygon -> polygonImpl.tag
            is GmsPolygon -> polygonImpl.tag
            else -> null
        }
    }
}
