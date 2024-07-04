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
 * Wrapper class for a ground overlay on a map.
 *
 * @property groundOverlayImpl The underlying implementation of the ground overlay.
 */
class GroundOverlay(private val groundOverlayImpl: Any) {
    /**
     * Hides the ground overlay.
     */
    fun hide() {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.isVisible = false
            is GmsGroundOverlay -> groundOverlayImpl.isVisible = false
        }
    }

    /**
     * Shows the ground overlay.
     */
    fun show() {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.isVisible = true
            is GmsGroundOverlay -> groundOverlayImpl.isVisible = true
        }
    }

    /**
     * Removes the ground overlay.
     *
     * @return True if the removal is successful, false otherwise.
     */
    fun remove(): Boolean {
        return try {
            when (groundOverlayImpl) {
                is HmsGroundOverlay -> groundOverlayImpl.remove()
                is GmsGroundOverlay -> groundOverlayImpl.remove()
                else -> false
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Gets the ID of the ground overlay.
     *
     * @return The ID of the ground overlay, or null if the ID cannot be retrieved.
     */
    fun getId(): String? {
        return try {
            when (groundOverlayImpl) {
                is HmsGroundOverlay -> groundOverlayImpl.id
                is GmsGroundOverlay -> groundOverlayImpl.id
                else -> null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Sets the position of the ground overlay.
     *
     * @param position The geographical location to set as the position of the ground overlay.
     */
    fun setPosition(position: LatLng) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.position = position.toHMSLatLng()
            is GmsGroundOverlay -> groundOverlayImpl.position = position.toGMSLatLng()
        }
    }

    /**
     * Gets the position of the ground overlay.
     *
     * @return The geographical location representing the position of the ground overlay, or null if the position cannot be retrieved.
     */
    fun getPosition(): LatLng? {
        return try {
            when (groundOverlayImpl) {
                is HmsGroundOverlay -> groundOverlayImpl.position.toLatLng()
                is GmsGroundOverlay -> groundOverlayImpl.position.toLatLng()
                else -> null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Sets the dimensions of the ground overlay.
     *
     * @param var1 The dimension value to set for the ground overlay (in meters if only one value is provided).
     * @param var2 The second dimension value to set for the ground overlay (in meters). This parameter is optional.
     */
    fun setDimensions(var1: Float, var2: Float) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.setDimensions(var1, var2)
            is GmsGroundOverlay -> groundOverlayImpl.setDimensions(var1, var2)
        }
    }

    /**
     * Sets the dimensions of the ground overlay.
     *
     * @param var1 The dimension value to set for the ground overlay (in meters).
     */
    fun setDimensions(var1: Float) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.setDimensions(var1)
            is GmsGroundOverlay -> groundOverlayImpl.setDimensions(var1)
        }
    }

    /**
     * Gets the width of the ground overlay.
     *
     * @return The width of the ground overlay in meters, or null if the width cannot be retrieved.
     */
    fun getWidth(): Float? {
        return try {
            when (groundOverlayImpl) {
                is HmsGroundOverlay -> groundOverlayImpl.width
                is GmsGroundOverlay -> groundOverlayImpl.width
                else -> null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Gets the height of the ground overlay.
     *
     * @return The height of the ground overlay in meters, or null if the height cannot be retrieved.
     */
    fun getHeight(): Float? {
        return try {
            when (groundOverlayImpl) {
                is HmsGroundOverlay -> groundOverlayImpl.height
                is GmsGroundOverlay -> groundOverlayImpl.height
                else -> null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Sets the position of the ground overlay based on the provided bounds.
     *
     * @param bounds The geographical bounds to set as the position of the ground overlay.
     */
    fun setPositionsFromBounds(bounds: LatLngBounds) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.setPositionFromBounds(bounds.toHmsLatLngBounds())
            is GmsGroundOverlay -> groundOverlayImpl.setPositionFromBounds(bounds.toGmsLatLngBounds())
        }
    }

    /**
     * Sets the bearing (rotation angle) of the ground overlay.
     *
     * @param bearing The bearing angle to set for the ground overlay.
     */
    fun setBearing(bearing: Float) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.bearing = bearing
            is GmsGroundOverlay -> groundOverlayImpl.bearing = bearing
        }
    }

    /**
     * Sets the z-index of the ground overlay.
     *
     * @param zIndex The z-index to set for the ground overlay.
     */
    fun setZIndex(zIndex: Float) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.zIndex = zIndex
            is GmsGroundOverlay -> groundOverlayImpl.zIndex = zIndex
        }
    }

    /**
     * Gets the z-index of the ground overlay.
     *
     * @return The z-index of the ground overlay, or null if the z-index cannot be retrieved.
     */
    fun getZIndex(): Float? {
        return try {
            when (groundOverlayImpl) {
                is HmsGroundOverlay -> groundOverlayImpl.zIndex
                is GmsGroundOverlay -> groundOverlayImpl.zIndex
                else -> null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Sets the visibility of the ground overlay.
     *
     * @param visible True to make the ground overlay visible, false to hide it.
     */
    fun setVisible(visible: Boolean) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.isVisible = visible
            is GmsGroundOverlay -> groundOverlayImpl.isVisible = visible
        }
    }

    /**
     * Checks whether the ground overlay is visible.
     *
     * @return True if the ground overlay is visible, false otherwise.
     */
    fun isVisible(): Boolean {
        return when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.isVisible
            else -> (groundOverlayImpl as GmsGroundOverlay).isVisible
        }
    }

    /**
     * Sets whether the ground overlay is clickable.
     *
     * @param clickable True to make the ground overlay clickable, false otherwise.
     */
    fun setClickable(clickable: Boolean) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.isClickable = clickable
            is GmsGroundOverlay -> groundOverlayImpl.isClickable = clickable
        }
    }

    /**
     * Checks whether the ground overlay is clickable.
     *
     * @return True if the ground overlay is clickable, false otherwise.
     */
    fun isClickable(): Boolean {
        return when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.isClickable
            else -> (groundOverlayImpl as GmsGroundOverlay).isClickable
        }
    }

    /**
     * Sets the transparency of the ground overlay.
     *
     * @param transparency The transparency value to set for the ground overlay, ranging from 0.0 (fully opaque) to 1.0 (fully transparent).
     */
    fun setTransparency(transparency: Float) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.transparency = transparency
            is GmsGroundOverlay -> groundOverlayImpl.transparency = transparency
        }
    }

    /**
     * Gets the transparency of the ground overlay.
     *
     * @return The transparency value of the ground overlay, ranging from 0.0 (fully opaque) to 1.0 (fully transparent).
     */
    fun getTransparency(): Float {
        return when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.transparency
            else -> (groundOverlayImpl as GmsGroundOverlay).transparency
        }
    }

    /**
     * Sets a tag for the ground overlay.
     *
     * @param var1 The tag object to set for the ground overlay.
     */
    fun setTag(var1: Any) {
        when (groundOverlayImpl) {
            is HmsGroundOverlay -> groundOverlayImpl.tag = var1
            is GmsGroundOverlay -> groundOverlayImpl.tag = var1
        }
    }

    /**
     * Gets the tag of the ground overlay.
     *
     * @return The tag object associated with the ground overlay, or null if no tag has been set.
     */
    fun getTag(): Any? {
        return try {
            when (groundOverlayImpl) {
                is HmsGroundOverlay -> groundOverlayImpl.tag
                is GmsGroundOverlay -> groundOverlayImpl.tag
                else -> null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}