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