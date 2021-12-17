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

class GroundOverlayOptions {
    private var anchorU: Float = 0.5F
    private var anchorV: Float = 0.5F
    private var bearing: Float = 0.0F
    private var bounds: LatLngBounds? = null
    private var height: Float = 0.0F
    private var location: LatLng? = null
    private var transparency: Float = 0.0F
    private var width: Float = 0.0F
    private var zIndex: Float = 0.0F
    private var isClickable: Boolean = false
    private var isVisible: Boolean = true

    fun getAnchorU(): Float = this.anchorU
    fun getAnchorV(): Float = this.anchorV
    fun getBearing(): Float = this.bearing
    fun getBounds(): LatLngBounds? = this.bounds
    fun getHeight(): Float = this.height
    fun getLocation(): LatLng? = this.location
    fun getTransparency(): Float = this.transparency
    fun getWidth(): Float = this.width
    fun getZIndex(): Float = this.zIndex
    fun isClickable(): Boolean = this.isClickable
    fun isVisible(): Boolean = this.isVisible

    fun anchor(anchorU: Float, anchorV: Float): GroundOverlayOptions{
        this.anchorU = anchorU
        this.anchorV = anchorV
        return this
    }

    fun bearing(bearing: Float): GroundOverlayOptions{
        this.bearing = bearing
        return this
    }

    fun clickable(isClickable: Boolean): GroundOverlayOptions{
        this.isClickable = isClickable
        return this
    }

    fun position(location: LatLng, width: Float): GroundOverlayOptions{
        this.location = location
        this.width = width
        return this
    }

    fun position(location: LatLng, width: Float, height: Float): GroundOverlayOptions{
        this.location = location
        this.width = width
        this.height = height
        return this
    }

    fun positionFromBounds(bounds: LatLngBounds): GroundOverlayOptions{
        this.bounds = bounds
        return this
    }

    fun transparency(transparency: Float): GroundOverlayOptions{
        this.transparency = transparency
        return this
    }

    fun visible(visible: Boolean): GroundOverlayOptions{
        this.isVisible = visible
        return this
    }

    fun zIndex(zIndex: Float): GroundOverlayOptions{
        this.zIndex = zIndex
        return this
    }

}