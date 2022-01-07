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
import java.lang.Exception

class Polyline(val polylineImpl : Any) {
    fun hide(){
        when (polylineImpl){
            is com.huawei.hms.maps.model.Polyline -> polylineImpl.isVisible=false
            is com.google.android.gms.maps.model.Polyline -> polylineImpl.isVisible=false
        }
    }

    fun show(){
        when (polylineImpl){
            is com.huawei.hms.maps.model.Polyline -> polylineImpl.isVisible=true
            is com.google.android.gms.maps.model.Polyline -> polylineImpl.isVisible=true
        }
    }

    fun remove() :Boolean{
        return try {
            when (polylineImpl){
                is com.huawei.hms.maps.model.Polyline -> polylineImpl.remove()
                is com.google.android.gms.maps.model.Polyline -> polylineImpl.remove()
            }
            true
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    fun getId(): String {

        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.id
            else -> (polylineImpl as GmsPolyline).id
        }
    }

    fun setPoints(points: List<LatLng?>?) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.points = points?.map { it?.toHMSLatLng() }
            is GmsPolyline -> polylineImpl.points = points?.map { it?.toGMSLatLng() } as MutableList<com.google.android.gms.maps.model.LatLng>
        }
    }

    fun getPoints(): List<LatLng> {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.points.map { it.toLatLng() }
            else -> (polylineImpl as GmsPolyline).points.map { it.toLatLng() }
        }
    }

    fun setWidth(width: Float) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.width = width
            is GmsPolyline -> polylineImpl.width = width
        }
    }

    fun getWidth(): Float {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.width
            else -> (polylineImpl as GmsPolyline).width
        }
    }

    fun setColor(color: Int) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.color = color
            is GmsPolyline -> polylineImpl.color = color
        }
    }

    fun getColor(): Int {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.color
            else -> (polylineImpl as GmsPolyline).color
        }
    }

    fun setJointType(type: Int) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.jointType = type
            is GmsPolyline -> polylineImpl.jointType = type
        }
    }

    fun getJointType(): Int {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.jointType
            else -> (polylineImpl as GmsPolyline).jointType
        }
    }

    fun setZIndex(zIndex: Float) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.zIndex = zIndex
            is GmsPolyline -> polylineImpl.zIndex = zIndex
        }
    }

    fun getZIndex(): Float {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.zIndex
            else -> (polylineImpl as GmsPolyline).zIndex
        }
    }

    fun setVisible(visible: Boolean) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.isVisible = visible
            is GmsPolyline -> polylineImpl.isVisible = visible
        }
    }

    fun isVisible(): Boolean {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.isVisible
            else -> (polylineImpl as GmsPolyline).isVisible
        }
    }

    fun setGeodesic(geodesic: Boolean) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.isGeodesic = geodesic
            is GmsPolyline -> polylineImpl.isGeodesic = geodesic
        }
    }

    fun isGeodesic(): Boolean {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.isGeodesic
            else -> (polylineImpl as GmsPolyline).isGeodesic
        }
    }

    fun setClickable(clickable: Boolean) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.isClickable = clickable
            is GmsPolyline -> polylineImpl.isClickable = clickable
        }
    }

    fun isClickable(): Boolean {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.isClickable
            else -> (polylineImpl as GmsPolyline).isClickable
        }
    }

    fun setTag(tag: Any?) {
        when(polylineImpl){
            is HmsPolyline -> polylineImpl.tag = tag
            is GmsPolyline -> polylineImpl.tag = tag
        }
    }

    fun getTag(): Any? {
        return when(polylineImpl){
            is HmsPolyline -> polylineImpl.tag
            else -> (polylineImpl as GmsPolyline).tag
        }
    }
}
