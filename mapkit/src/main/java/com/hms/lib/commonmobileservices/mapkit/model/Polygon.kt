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

class Polygon(val polygonImpl : Any) {
    fun hide(){
        when (polygonImpl){
            is com.huawei.hms.maps.model.Polygon -> polygonImpl.isVisible=false
            is com.google.android.gms.maps.model.Polygon -> polygonImpl.isVisible=false
        }
    }

    fun show(){
        when (polygonImpl){
            is com.huawei.hms.maps.model.Polygon -> polygonImpl.isVisible=true
            is com.google.android.gms.maps.model.Polygon -> polygonImpl.isVisible=true
        }
    }

    fun remove() :Boolean{
        return try {
            when (polygonImpl){
                is com.huawei.hms.maps.model.Polygon -> polygonImpl.remove()
                is com.google.android.gms.maps.model.Polygon -> polygonImpl.remove()
            }
            true
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    fun getId(): String{
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.id
            else -> (polygonImpl as GmsPolygon).id
        }
    }

    fun setPoints(points: List<LatLng?>?) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.points = points?.map { it?.toHMSLatLng() }
            is GmsPolygon -> polygonImpl.points = points?.map { it?.toGMSLatLng() }
        }
    }

    fun getPoints(): List<LatLng>? {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.points?.map { it.toLatLng() }
            else -> (polygonImpl as GmsPolygon).points?.map { it.toLatLng() }
        }
    }

    fun setHoles(holes: List<List<LatLng?>?>?) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.holes = holes?.map { list -> list?.map { item -> item?.toHMSLatLng() } }
            is GmsPolygon -> polygonImpl.holes = holes?.map { list -> list?.map { item -> item?.toGMSLatLng() }}
        }
    }

    fun getHoles(): List<List<LatLng>>? {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.holes.map { list -> list.map { item -> item.toLatLng() } }
            is GmsPolygon -> polygonImpl.holes.map { list -> list.map { item -> item.toLatLng() }}
            else -> null
        }
    }

    fun setStrokeWidth(width: Float) {

        when(polygonImpl){
            is HmsPolygon -> polygonImpl.strokeWidth = width
            is GmsPolygon -> polygonImpl.strokeWidth = width
        }
    }

    fun getStrokeWidth(): Float {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.strokeWidth
            else -> (polygonImpl as GmsPolygon).strokeWidth
        }
    }

    fun setStrokeColor(color: Int) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.strokeColor = color
            is GmsPolygon -> polygonImpl.strokeColor = color
        }
    }

    fun getStrokeColor(): Int {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.strokeColor
            else -> (polygonImpl as GmsPolygon).strokeColor
        }
    }

    fun setStrokeJointType(type: Int) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.strokeJointType = type
            is GmsPolygon -> polygonImpl.strokeJointType = type
        }
    }

    fun getStrokeJointType(): Int {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.strokeJointType
            else -> (polygonImpl as GmsPolygon).strokeJointType
        }
    }

    fun setFillColor(color: Int) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.fillColor = color
            is GmsPolygon -> polygonImpl.fillColor = color
        }
    }

    fun getFillColor(): Int {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.fillColor
            else -> (polygonImpl as GmsPolygon).fillColor
        }
    }

    fun setZIndex(zIndex: Float) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.zIndex = zIndex
            is GmsPolygon -> polygonImpl.zIndex = zIndex
        }
    }

    fun getZIndex(): Float {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.zIndex
            else -> (polygonImpl as GmsPolygon).zIndex
        }
    }

    fun setVisible(visible: Boolean) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.isVisible = visible
            is GmsPolygon -> polygonImpl.isVisible = visible
        }
    }

    fun isVisible(): Boolean {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.isVisible
            else -> (polygonImpl as GmsPolygon).isVisible
        }
    }

    fun setGeodesic(geodesic: Boolean) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.isGeodesic = geodesic
            is GmsPolygon -> polygonImpl.isGeodesic = geodesic
        }
    }

    fun isGeodesic(): Boolean {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.isGeodesic
            else -> (polygonImpl as GmsPolygon).isGeodesic
        }
    }

    fun setClickable(clickable: Boolean) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.isClickable = clickable
            is GmsPolygon -> polygonImpl.isClickable = clickable
        }
    }

    fun isClickable(): Boolean {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.isClickable
            else -> (polygonImpl as GmsPolygon).isClickable
        }
    }

    fun setTag(tag: Any?) {
        when(polygonImpl){
            is HmsPolygon -> polygonImpl.tag = tag
            is GmsPolygon -> polygonImpl.tag = tag
        }
    }

    fun getTag(): Any? {
        return when(polygonImpl){
            is HmsPolygon -> polygonImpl.tag
            is GmsPolygon -> polygonImpl.tag
            else -> null
        }
    }

}
