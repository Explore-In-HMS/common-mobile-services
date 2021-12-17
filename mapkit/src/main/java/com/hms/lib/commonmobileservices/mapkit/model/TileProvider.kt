package com.hms.lib.commonmobileservices.mapkit.model

abstract class TileProvider {
    val NO_TILE: Tile = Tile(-1, -1, null)

    abstract fun getTile(var1: Int, var2: Int, var3: Int): Tile
}