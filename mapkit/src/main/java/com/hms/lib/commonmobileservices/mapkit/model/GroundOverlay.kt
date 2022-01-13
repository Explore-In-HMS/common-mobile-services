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

    fun getId(): String?{
        return try{
            when(groundOverlayImpl){
                is HmsGroundOverlay -> groundOverlayImpl.id
                is GmsGroundOverlay -> groundOverlayImpl.id
                else -> null
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    fun setPosition(position: LatLng){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.position = position.toHMSLatLng()
            is GmsGroundOverlay -> groundOverlayImpl.position = position.toGMSLatLng()
        }
    }

    fun getPosition(): LatLng?{
        return try{
            when(groundOverlayImpl){
                is HmsGroundOverlay -> groundOverlayImpl.position.toLatLng()
                is GmsGroundOverlay -> groundOverlayImpl.position.toLatLng()
                else -> null
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    fun setDimensions(var1: Float){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.setDimensions(var1)
            is GmsGroundOverlay -> groundOverlayImpl.setDimensions(var1)
        }
    }

    fun setDimensions(var1: Float, var2: Float){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.setDimensions(var1, var2)
            is GmsGroundOverlay -> groundOverlayImpl.setDimensions(var1, var2)
        }
    }

    fun getWidth(): Float?{
        return try{
            when(groundOverlayImpl){
                is HmsGroundOverlay -> groundOverlayImpl.width
                is GmsGroundOverlay -> groundOverlayImpl.width
                else -> null
            }

        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    fun getHeight(): Float?{
        return try{
            when(groundOverlayImpl){
                is HmsGroundOverlay -> groundOverlayImpl.height
                is GmsGroundOverlay -> groundOverlayImpl.height
                else -> null
            }

        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    fun setPositionsFromBounds(bounds: LatLngBounds){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.setPositionFromBounds(bounds.toHmsLatLngBounds())
            is GmsGroundOverlay -> groundOverlayImpl.setPositionFromBounds(bounds.toGmsLatLngBounds())
        }
    }

    fun setBearing(bearing: Float){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.bearing = bearing
            is GmsGroundOverlay -> groundOverlayImpl.bearing = bearing
        }
    }

    fun setZIndex(zIndex: Float){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.zIndex = zIndex
            is GmsGroundOverlay -> groundOverlayImpl.zIndex = zIndex
        }
    }

    fun getZIndex(): Float?{
        return try{
            when(groundOverlayImpl){
                is HmsGroundOverlay -> groundOverlayImpl.zIndex
                is GmsGroundOverlay -> groundOverlayImpl.zIndex
                else -> null
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    fun setVisible(visible: Boolean){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.isVisible = visible
            is GmsGroundOverlay -> groundOverlayImpl.isVisible = visible
        }
    }

    fun isVisible(): Boolean{
        return when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.isVisible
            else -> (groundOverlayImpl as GmsGroundOverlay).isVisible
        }
    }

    fun setClickable(clickable: Boolean){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.isClickable = clickable
            is GmsGroundOverlay -> groundOverlayImpl.isClickable = clickable
        }
    }

    fun isClickable(): Boolean{
        return when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.isClickable
            else -> (groundOverlayImpl as GmsGroundOverlay).isClickable
        }
    }

    fun setTransparency(transparency: Float){
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.transparency = transparency
            is GmsGroundOverlay -> groundOverlayImpl.transparency = transparency
        }
    }

    fun getTransparency():Float{
        return when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.transparency
            else -> (groundOverlayImpl as GmsGroundOverlay).transparency
        }
    }

    fun setTag(var1: Any) {
        when(groundOverlayImpl){
            is HmsGroundOverlay -> groundOverlayImpl.tag = var1
            is GmsGroundOverlay -> groundOverlayImpl.tag = var1
        }
    }

    fun getTag(): Any?{
        return try{
            when(groundOverlayImpl){
                is HmsGroundOverlay -> groundOverlayImpl.tag
                is GmsGroundOverlay -> groundOverlayImpl.tag
                else -> null
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}