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
import com.google.android.gms.common.internal.GmsClient
import com.google.android.gms.maps.GoogleMap
import java.lang.Exception



class Circle(private val circleImpl: Any) {
    fun hide(){
        when(circleImpl){
            is HmsCircle -> circleImpl.isVisible=false
            is GmsCircle -> circleImpl.isVisible=false
        }
    }

    fun show(){
        when(circleImpl){
            is HmsCircle -> circleImpl.isVisible=true
            is GmsCircle -> circleImpl.isVisible=true
        }
    }

    fun remove():Boolean{
        return try{
            when (circleImpl){
                is HmsCircle -> circleImpl.remove()
                is GmsCircle -> circleImpl.remove()
            }
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    fun getId(): String {
        return when(circleImpl){
            is HmsCircle -> circleImpl.id
            else -> (circleImpl as GmsCircle).id
        }
    }

    fun setCenter(center: LatLng?) {
        when(circleImpl){
            is HmsCircle -> circleImpl.center = center?.toHMSLatLng()
            is GmsCircle -> circleImpl.center = center?.toGMSLatLng()
        }
    }

    fun getCenter(): LatLng {
        return when(circleImpl){
            is HmsCircle -> circleImpl.center.toLatLng()
            else -> (circleImpl as GmsCircle).center.toLatLng()
        }
    }

    fun setRadius(radius: Double) {
        when(circleImpl){
            is HmsCircle -> circleImpl.radius = radius
            is GmsCircle -> circleImpl.radius = radius
        }
    }

    fun getRadius(): Double {
        return when(circleImpl){
            is HmsCircle -> circleImpl.radius
            else -> (circleImpl as GmsCircle).radius
        }
    }

    fun setStrokeWidth(strokeWidth: Float) {
        when(circleImpl){
            is HmsCircle -> circleImpl.strokeWidth = strokeWidth
            is GmsCircle -> circleImpl.strokeWidth = strokeWidth
        }
    }

    fun getStrokeWidth(): Float {
        return when(circleImpl){
            is HmsCircle -> circleImpl.strokeWidth
            else -> (circleImpl as GmsCircle).strokeWidth
        }
    }

    fun setStrokeColor(strokeColor: Int) {
        when(circleImpl){
            is HmsCircle -> circleImpl.strokeColor = strokeColor
            is GmsCircle -> circleImpl.strokeColor = strokeColor
        }
    }

    fun getStrokeColor(): Int {
        return when(circleImpl){
            is HmsCircle -> circleImpl.strokeColor
            else -> (circleImpl as GmsCircle).strokeColor
        }
    }

    fun setFillColor(fillColor: Int) {
        when(circleImpl){
            is HmsCircle -> circleImpl.fillColor = fillColor
            is GmsCircle -> circleImpl.fillColor = fillColor
        }
    }

    fun getFillColor(): Int {
        return when(circleImpl){
            is HmsCircle -> circleImpl.fillColor
            else -> (circleImpl as GmsCircle).fillColor
        }
    }

    fun setZIndex(zIndex: Float) {
        when(circleImpl){
            is HmsCircle -> circleImpl.zIndex = zIndex
            is GmsCircle -> circleImpl.zIndex = zIndex
        }
    }

    fun getZIndex(): Float {
        return when(circleImpl){
            is HmsCircle -> circleImpl.zIndex
            else -> (circleImpl as GmsCircle).zIndex
        }
    }

    fun setVisible(visible: Boolean) {
        when(circleImpl){
            is HmsCircle -> circleImpl.isVisible = visible
            is GmsCircle -> circleImpl.isVisible = visible
        }
    }

    fun isVisible(): Boolean {
        return when(circleImpl){
            is HmsCircle -> circleImpl.isVisible
            else -> (circleImpl as GmsCircle).isVisible
        }
    }

    fun setClickable(clickable: Boolean) {
        when(circleImpl){
            is HmsCircle -> circleImpl.isClickable = clickable
            is GmsCircle -> circleImpl.isClickable = clickable
        }
    }

    fun isClickable(): Boolean {
        return when(circleImpl){
            is HmsCircle -> circleImpl.isClickable
            else -> (circleImpl as GmsCircle).isClickable
        }
    }
}