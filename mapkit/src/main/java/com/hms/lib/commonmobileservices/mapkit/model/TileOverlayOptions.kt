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
 * Options for configuring a tile overlay on the map.
 */
class TileOverlayOptions {
    /**
     * Indicates whether the tile overlay should fade in.
     */
    var fadeIn: Boolean? = true

    /**
     * The tile provider for the overlay.
     */
    var tileProvider: TileProvider? = null

    /**
     * The transparency of the tile overlay. Value should be in the range [0, 1], where 0 means fully opaque and 1 means fully transparent.
     */
    var transparency: Float? = 0.0F

    /**
     * The z-index of the tile overlay.
     */
    var zIndex: Float? = 0.0F

    /**
     * Indicates whether the tile overlay is visible.
     */
    var isVisible: Boolean? = true
}