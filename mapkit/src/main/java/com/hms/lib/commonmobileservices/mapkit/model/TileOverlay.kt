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
 * Represents a tile overlay on the map.
 *
 * @property tileOverlayImpl The underlying tile overlay implementation.
 */
class TileOverlay(private val tileOverlayImpl: Any) {
    /**
     * Hides the tile overlay.
     */
    fun hide() {
        when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.isVisible = false
            is GmsTileOverlay -> tileOverlayImpl.isVisible = false
        }
    }

    /**
     * Shows the tile overlay.
     */
    fun show() {
        when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.isVisible = true
            is GmsTileOverlay -> tileOverlayImpl.isVisible = true
        }
    }

    /**
     * Removes the tile overlay from the map.
     *
     * @return True if the removal was successful, false otherwise.
     */
    fun remove(): Boolean {
        return try {
            when (tileOverlayImpl) {
                is HmsTileOverlay -> tileOverlayImpl.remove()
                is GmsTileOverlay -> tileOverlayImpl.remove()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Clears the tile cache for the tile overlay.
     */
    fun clearTileCache() {
        when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.clearTileCache()
            else -> (tileOverlayImpl as GmsTileOverlay).clearTileCache()
        }
    }

    /**
     * Retrieves the ID of the tile overlay.
     *
     * @return The ID of the tile overlay.
     */
    fun getId(): String {
        return when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.id
            else -> (tileOverlayImpl as GmsTileOverlay).id
        }
    }

    /**
     * Sets the z-index of the tile overlay.
     *
     * @param zIndex The z-index value to set.
     */
    fun setZIndex(zIndex: Float) {
        when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.zIndex = zIndex
            is GmsTileOverlay -> tileOverlayImpl.zIndex = zIndex
        }
    }

    /**
     * Retrieves the z-index of the tile overlay.
     *
     * @return The z-index of the tile overlay.
     */
    fun getZIndex(): Float {
        return when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.zIndex
            else -> (tileOverlayImpl as GmsTileOverlay).zIndex
        }
    }

    /**
     * Sets the visibility of the tile overlay.
     *
     * @param visible True to make the tile overlay visible, false to hide it.
     */
    fun setVisible(visible: Boolean) {
        when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.isVisible = visible
            is GmsTileOverlay -> tileOverlayImpl.isVisible = visible
        }
    }

    /**
     * Checks if the tile overlay is visible.
     *
     * @return True if the tile overlay is visible, otherwise false.
     */
    fun isVisible(): Boolean {
        return when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.isVisible
            else -> (tileOverlayImpl as GmsTileOverlay).isVisible
        }
    }

    /**
     * Sets whether the tile overlay should fade in.
     *
     * @param fadeIn True to enable fade in, false otherwise.
     */
    fun setFadeIn(fadeIn: Boolean) {
        when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.fadeIn = fadeIn
            is GmsTileOverlay -> tileOverlayImpl.fadeIn = fadeIn
        }
    }

    /**
     * Checks if the tile overlay should fade in.
     *
     * @return True if the tile overlay should fade in, otherwise false.
     */
    fun getFadeIn(): Boolean {
        return when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.fadeIn
            else -> (tileOverlayImpl as GmsTileOverlay).fadeIn
        }
    }

    /**
     * Sets the transparency of the tile overlay.
     *
     * @param transparency The transparency value to set.
     */
    fun setTransparency(transparency: Float) {
        when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.transparency = transparency
            is GmsTileOverlay -> tileOverlayImpl.transparency = transparency
        }
    }

    /**
     * Retrieves the transparency of the tile overlay.
     *
     * @return The transparency of the tile overlay.
     */
    fun getTransparency(): Float {
        return when (tileOverlayImpl) {
            is HmsTileOverlay -> tileOverlayImpl.transparency
            else -> (tileOverlayImpl as GmsTileOverlay).transparency
        }
    }
}