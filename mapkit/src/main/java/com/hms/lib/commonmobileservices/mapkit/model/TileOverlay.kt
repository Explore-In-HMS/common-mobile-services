package com.hms.lib.commonmobileservices.mapkit.model

import java.lang.Exception

class TileOverlay (private val tileOverlayImpl: Any) {
    fun hide(){
        when (tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.isVisible=false
            is GmsTileOverlay -> tileOverlayImpl.isVisible=false
        }
    }

    fun show(){
        when (tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.isVisible=true
            is GmsTileOverlay -> tileOverlayImpl.isVisible=true
        }
    }

    fun remove() :Boolean{
        return try {
            when (tileOverlayImpl){
                is HmsTileOverlay -> tileOverlayImpl.remove()
                is GmsTileOverlay -> tileOverlayImpl.remove()
            }
            true
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}