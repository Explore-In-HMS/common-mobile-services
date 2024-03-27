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

import android.graphics.Bitmap

/**
 * Options for configuring the appearance and behavior of a ground overlay on a map.
 *
 * @property anchorU The horizontal anchor point of the ground overlay image, specified as a ratio of the image width.
 * @property anchorV The vertical anchor point of the ground overlay image, specified as a ratio of the image height.
 * @property bearing The bearing (rotation) of the ground overlay image in degrees clockwise from north.
 * @property bounds The geographical bounding box that defines the position and size of the ground overlay.
 * @property height The height of the ground overlay image in meters.
 * @property location The geographical location of the center of the ground overlay.
 * @property transparency The transparency of the ground overlay image, specified as a value between 0.0 (fully opaque) and 1.0 (fully transparent).
 * @property width The width of the ground overlay image in meters.
 * @property zIndex The z-index of the ground overlay. Ground overlays with higher z-index values are drawn above those with lower values.
 * @property isClickable Determines whether the ground overlay is clickable. If true, the ground overlay can be clicked to trigger events.
 * @property isVisible Determines whether the ground overlay is visible on the map.
 * @property imageResource The resource ID of the image to be used for the ground overlay.
 * @property imageAsset The asset file name of the image to be used for the ground overlay.
 * @property imageFile The file path of the image to be used for the ground overlay.
 * @property imagePath The path of the image to be used for the ground overlay.
 * @property imageBitmap The bitmap image to be used for the ground overlay.
 * @property defaultMarker Determines whether the ground overlay should use the default marker icon. If true, the image specified by other properties is ignored.
 */
class GroundOverlayOptions {
    var anchorU: Float? = 0.5F
    var anchorV: Float? = 0.5F
    var bearing: Float? = 0.0F
    var bounds: LatLngBounds? = null
    var height: Float? = 0.0F
    var location: LatLng? = null
    var transparency: Float? = 0.0F
    var width: Float? = 0.0F
    var zIndex: Float? = 0.0F
    var isClickable: Boolean? = false
    var isVisible: Boolean? = true
    var imageResource: Int? = 0
    var imageAsset: String? = null
    var imageFile: String? = null
    var imagePath: String? = null
    var imageBitmap: Bitmap? = null
    var defaultMarker: Boolean? = false
}