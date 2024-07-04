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

/**
 * Interface defining user interface settings for a map. It allows enabling or disabling various UI components and gestures.
 */
interface UISettings {

    /**
     * Checks if the compass is enabled.
     * @return True if the compass is enabled, false otherwise.
     */
    fun isCompassEnabled(): Boolean

    /**
     * Enables or disables the compass.
     * @param compassEnabled True to enable, false to disable.
     */
    fun setCompassEnabled(compassEnabled: Boolean?)

    /**
     * Checks if the indoor level picker is enabled.
     * @return True if enabled, false otherwise.
     */
    fun isIndoorLevelPickerEnabled(): Boolean

    /**
     * Enables or disables the indoor level picker.
     * @param indoorLevelPickerEnabled True to enable, false to disable.
     */
    fun setIndoorLevelPickerEnabled(indoorLevelPickerEnabled: Boolean?)

    /**
     * Checks if the map toolbar is enabled.
     * @return True if enabled, false otherwise.
     */
    fun isMapToolbarEnabled(): Boolean

    /**
     * Enables or disables the map toolbar.
     * @param mapToolbarEnabled True to enable, false to disable.
     */
    fun setMapToolbarEnabled(mapToolbarEnabled: Boolean?)

    /**
     * Checks if the "My Location" button is enabled.
     * @return True if enabled, false otherwise.
     */
    fun isMyLocationButtonEnabled(): Boolean

    /**
     * Enables or disables the "My Location" button.
     * @param myLocationButtonEnabled True to enable, false to disable.
     */
    fun setMyLocationButtonEnabled(myLocationButtonEnabled: Boolean?)

    /**
     * Checks if rotate gestures are enabled.
     * @return True if enabled, false otherwise.
     */
    fun isRotateGesturesEnabled(): Boolean

    /**
     * Enables or disables rotate gestures.
     * @param rotateGesturesEnabled True to enable, false to disable.
     */
    fun setRotateGesturesEnabled(rotateGesturesEnabled: Boolean?)

    /**
     * Checks if scroll gestures are enabled.
     * @return True if enabled, false otherwise.
     */
    fun isScrollGesturesEnabled(): Boolean

    /**
     * Enables or disables scroll gestures.
     * @param scrollGesturesEnabled True to enable, false to disable.
     */
    fun setScrollGesturesEnabled(scrollGesturesEnabled: Boolean?)

    /**
     * Checks if scroll gestures are enabled during rotate or zoom.
     * @return True if enabled, false otherwise.
     */
    fun isScrollGesturesEnabledDuringRotateOrZoom(): Boolean

    /**
     * Enables or disables scroll gestures during rotate or zoom.
     * @param scrollGesturesEnabledDuringRotateOrZoom True to enable, false to disable.
     */
    fun setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom: Boolean?)

    /**
     * Checks if tilt gestures are enabled.
     * @return True if enabled, false otherwise.
     */
    fun isTiltGesturesEnabled(): Boolean

    /**
     * Enables or disables tilt gestures.
     * @param tiltGesturesEnabled True to enable, false to disable.
     */
    fun setTiltGesturesEnabled(tiltGesturesEnabled: Boolean?)

    /**
     * Checks if zoom controls are enabled.
     * @return True if enabled, false otherwise.
     */
    fun isZoomControlsEnabled(): Boolean

    /**
     * Enables or disables zoom controls.
     * @param zoomControlsEnabled True to enable, false to disable.
     */
    fun setZoomControlsEnabled(zoomControlsEnabled: Boolean?)

    /**
     * Checks if zoom gestures are enabled.
     * @return True if enabled, false otherwise.
     */
    fun isZoomGesturesEnabled(): Boolean

    /**
     * Enables or disables zoom gestures.
     * @param zoomGesturesEnabled True to enable, false to disable.
     */
    fun setZoomGesturesEnabled(zoomGesturesEnabled: Boolean?)

    /**
     * Enables or disables all gestures.
     * @param allGestureEnable True to enable all gestures, false to disable.
     */
    fun setAllGesturesEnabled(allGestureEnable: Boolean?)
}
