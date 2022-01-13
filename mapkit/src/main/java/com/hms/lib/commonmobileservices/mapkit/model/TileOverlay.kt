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

import android.os.RemoteException
import com.google.android.gms.maps.model.RuntimeRemoteException
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

    fun clearTileCache() {
        when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.clearTileCache()
            else -> (tileOverlayImpl as GmsTileOverlay).clearTileCache()
        }
    }

    fun getId(): String {
        return when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.id
            else -> (tileOverlayImpl as GmsTileOverlay).id
        }
    }

    fun setZIndex(zIndex: Float) {
        when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.zIndex = zIndex
            is GmsTileOverlay -> tileOverlayImpl.zIndex = zIndex
        }
    }

    fun getZIndex(): Float {
        return when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.zIndex
            else -> (tileOverlayImpl as GmsTileOverlay).zIndex
        }
    }

    fun setVisible(visible: Boolean) {
        when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.isVisible = visible
            is GmsTileOverlay -> tileOverlayImpl.isVisible = visible
        }
    }

    fun isVisible(): Boolean {
        return when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.isVisible
            else -> (tileOverlayImpl as GmsTileOverlay).isVisible
        }
    }

    fun setFadeIn(fadeIn: Boolean) {
        when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.fadeIn = fadeIn
            is GmsTileOverlay -> tileOverlayImpl.fadeIn = fadeIn
        }
    }

    fun getFadeIn(): Boolean {
        return when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.fadeIn
            else -> (tileOverlayImpl as GmsTileOverlay).fadeIn
        }
    }

    fun setTransparency(transparency: Float) {
        when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.transparency = transparency
            is GmsTileOverlay -> tileOverlayImpl.transparency = transparency
        }
    }

    fun getTransparency(): Float {
        return when(tileOverlayImpl){
            is HmsTileOverlay -> tileOverlayImpl.transparency
            else -> (tileOverlayImpl as GmsTileOverlay).transparency
        }
    }
}