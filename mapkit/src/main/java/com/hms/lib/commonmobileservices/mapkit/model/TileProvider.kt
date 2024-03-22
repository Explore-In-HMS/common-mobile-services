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
 * Abstract class for providing tiles to a tile overlay on a map.
 *
 * @property NO_TILE A default empty tile used to signify that no tile is available.
 */
abstract class TileProvider {
    val NO_TILE: Tile = Tile(-1, -1, null)

    /**
     * Retrieves the tile for the specified tile coordinates and zoom level.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @param zoom The zoom level of the tile.
     * @return The tile at the specified coordinates and zoom level.
     */
    abstract fun getTile(x: Int, y: Int, zoom: Int): Tile
}