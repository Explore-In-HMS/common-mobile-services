package com.hms.lib.commonmobileservices.mapkit.model

import java.lang.Exception

class GroundOverlay(private val groundOverlayImpl: Any) {
    fun hide(){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.isVisible=false
            is GmsGroundOverlay -> groundOverlayImpl.isVisible=false
        }
    }

    fun show(){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.isVisible=true
            is GmsGroundOverlay -> groundOverlayImpl.isVisible=true
        }
    }

    fun remove():Boolean{
        return try{
            when (groundOverlayImpl){
                is HmsGroundOverlay -> groundOverlayImpl.remove()
                is GmsGroundOverlay -> groundOverlayImpl.remove()
            }
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}