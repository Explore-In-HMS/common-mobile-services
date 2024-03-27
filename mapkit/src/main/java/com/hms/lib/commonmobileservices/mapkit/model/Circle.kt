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
 * Wrapper class for a circle overlay on a map.
 *
 * @property circleImpl The underlying implementation of the circle overlay.
 */
class Circle(private val circleImpl: Any) {
    /**
     * Hides the circle overlay.
     */
    fun hide() {
        when (circleImpl) {
            is HmsCircle -> circleImpl.isVisible = false
            is GmsCircle -> circleImpl.isVisible = false
        }
    }

    /**
     * Shows the circle overlay.
     */
    fun show() {
        when (circleImpl) {
            is HmsCircle -> circleImpl.isVisible = true
            is GmsCircle -> circleImpl.isVisible = true
        }
    }

    /**
     * Removes the circle overlay.
     *
     * @return True if the removal is successful, false otherwise.
     */
    fun remove(): Boolean {
        return try {
            when (circleImpl) {
                is HmsCircle -> circleImpl.remove()
                is GmsCircle -> circleImpl.remove()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Gets the ID of the circle overlay.
     *
     * @return The ID of the circle overlay.
     */
    fun getId(): String {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.id
            else -> (circleImpl as GmsCircle).id
        }
    }

    /**
     * Sets the center of the circle overlay.
     *
     * @param center The geographical location to set as the center of the circle overlay.
     */
    fun setCenter(center: LatLng?) {
        when (circleImpl) {
            is HmsCircle -> circleImpl.center = center?.toHMSLatLng()
            is GmsCircle -> circleImpl.center = center?.toGMSLatLng()!!
        }
    }

    /**
     * Gets the center of the circle overlay.
     *
     * @return The geographical location representing the center of the circle overlay.
     */
    fun getCenter(): LatLng {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.center.toLatLng()
            else -> (circleImpl as GmsCircle).center.toLatLng()
        }
    }

    /**
     * Sets the radius of the circle overlay.
     *
     * @param radius The radius of the circle overlay in meters.
     */
    fun setRadius(radius: Double) {
        when (circleImpl) {
            is HmsCircle -> circleImpl.radius = radius
            is GmsCircle -> circleImpl.radius = radius
        }
    }

    /**
     * Gets the radius of the circle overlay.
     *
     * @return The radius of the circle overlay in meters.
     */
    fun getRadius(): Double {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.radius
            else -> (circleImpl as GmsCircle).radius
        }
    }

    /**
     * Sets the stroke width of the circle overlay.
     *
     * @param strokeWidth The width of the circle overlay's outline in pixels.
     */
    fun setStrokeWidth(strokeWidth: Float) {
        when (circleImpl) {
            is HmsCircle -> circleImpl.strokeWidth = strokeWidth
            is GmsCircle -> circleImpl.strokeWidth = strokeWidth
        }
    }

    /**
     * Gets the stroke width of the circle overlay.
     *
     * @return The width of the circle overlay's outline in pixels.
     */
    fun getStrokeWidth(): Float {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.strokeWidth
            else -> (circleImpl as GmsCircle).strokeWidth
        }
    }

    /**
     * Sets the stroke color of the circle overlay.
     *
     * @param strokeColor The color of the circle overlay's outline in ARGB format.
     */
    fun setStrokeColor(strokeColor: Int) {
        when (circleImpl) {
            is HmsCircle -> circleImpl.strokeColor = strokeColor
            is GmsCircle -> circleImpl.strokeColor = strokeColor
        }
    }

    /**
     * Gets the stroke color of the circle overlay.
     *
     * @return The color of the circle overlay's outline in ARGB format.
     */
    fun getStrokeColor(): Int {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.strokeColor
            else -> (circleImpl as GmsCircle).strokeColor
        }
    }

    /**
     * Sets the fill color of the circle overlay.
     *
     * @param fillColor The color to fill the interior of the circle overlay in ARGB format.
     */
    fun setFillColor(fillColor: Int) {
        when (circleImpl) {
            is HmsCircle -> circleImpl.fillColor = fillColor
            is GmsCircle -> circleImpl.fillColor = fillColor
        }
    }

    /**
     * Gets the fill color of the circle overlay.
     *
     * @return The color used to fill the interior of the circle overlay in ARGB format.
     */
    fun getFillColor(): Int {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.fillColor
            else -> (circleImpl as GmsCircle).fillColor
        }
    }

    /**
     * Sets the z-index of the circle overlay.
     *
     * @param zIndex The z-index of the circle overlay. Circles with higher z-index values are drawn above those with lower values.
     */
    fun setZIndex(zIndex: Float) {
        when (circleImpl) {
            is HmsCircle -> circleImpl.zIndex = zIndex
            is GmsCircle -> circleImpl.zIndex = zIndex
        }
    }

    /**
     * Gets the z-index of the circle overlay.
     *
     * @return The z-index of the circle overlay.
     */
    fun getZIndex(): Float {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.zIndex
            else -> (circleImpl as GmsCircle).zIndex
        }
    }

    /**
     * Sets the visibility of the circle overlay.
     *
     * @param visible True to make the circle overlay visible, false otherwise.
     */
    fun setVisible(visible: Boolean) {
        when (circleImpl) {
            is HmsCircle -> circleImpl.isVisible = visible
            is GmsCircle -> circleImpl.isVisible = visible
        }
    }

    /**
     * Checks whether the circle overlay is visible.
     *
     * @return True if the circle overlay is visible, false otherwise.
     */
    fun isVisible(): Boolean {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.isVisible
            else -> (circleImpl as GmsCircle).isVisible
        }
    }

    /**
     * Sets whether the circle overlay is clickable.
     *
     * @param clickable True to make the circle overlay clickable, false otherwise.
     */
    fun setClickable(clickable: Boolean) {
        when (circleImpl) {
            is HmsCircle -> circleImpl.isClickable = clickable
            is GmsCircle -> circleImpl.isClickable = clickable
        }
    }

    /**
     * Checks whether the circle overlay is clickable.
     *
     * @return True if the circle overlay is clickable, false otherwise.
     */
    fun isClickable(): Boolean {
        return when (circleImpl) {
            is HmsCircle -> circleImpl.isClickable
            else -> (circleImpl as GmsCircle).isClickable
        }
    }
}