package com.hms.lib.commonmobileservices.mapkit.model

class TileOverlayOptions {
    var fadeIn: Boolean = true
    var tileProvider: TileProvider? = null
    var transparency: Float = 0.0F
    var zIndex: Float = 0.0F
    var isVisible: Boolean = true

    fun fadeIn(fadeIn: Boolean): TileOverlayOptions{
        this.fadeIn = fadeIn
        return this
    }

    fun tileProvider(tileProvider: TileProvider): TileOverlayOptions{
        this.tileProvider = tileProvider
        return this
    }

    fun transparency(transparency: Float): TileOverlayOptions{
        this.transparency = transparency
        return this
    }

    fun visible(visible: Boolean): TileOverlayOptions{
        this.isVisible = visible
        return this
    }

    fun zIndex(zIndex: Float): TileOverlayOptions{
        this.zIndex = zIndex
        return this
    }
}