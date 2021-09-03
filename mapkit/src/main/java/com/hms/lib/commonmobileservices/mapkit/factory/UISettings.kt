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

interface UISettings {
    fun isCompassEnabled(): Boolean

    fun setCompassEnabled(compassEnabled: Boolean?)

    fun isIndoorLevelPickerEnabled(): Boolean

    fun setIndoorLevelPickerEnabled(indoorLevelPickerEnabled: Boolean?)

    fun isMapToolbarEnabled(): Boolean

    fun setMapToolbarEnabled(mapToolbarEnabled: Boolean?)

    fun isMyLocationButtonEnabled(): Boolean

    fun setMyLocationButtonEnabled(myLocationButtonEnabled: Boolean?)

    fun isRotateGesturesEnabled(): Boolean

    fun setRotateGesturesEnabled(rotateGesturesEnabled: Boolean?)

    fun isScrollGesturesEnabled(): Boolean

    fun setScrollGesturesEnabled(scrollGesturesEnabled: Boolean?)

    fun isScrollGesturesEnabledDuringRotateOrZoom(): Boolean

    fun setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom: Boolean?)

    fun isTiltGesturesEnabled(): Boolean

    fun setTiltGesturesEnabled(tiltGesturesEnabled: Boolean?)

    fun isZoomControlsEnabled(): Boolean

    fun setZoomControlsEnabled(zoomControlsEnabled: Boolean?)

    fun isZoomGesturesEnabled(): Boolean

    fun setZoomGesturesEnabled(zoomGesturesEnabled: Boolean?)

    fun setAllGesturesEnabled(allGestureEnable: Boolean?)
}