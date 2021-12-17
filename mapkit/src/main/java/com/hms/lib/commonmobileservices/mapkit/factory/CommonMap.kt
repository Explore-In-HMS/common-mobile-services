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
package com.hms.lib.commonmobileservices.mapkit.factory

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.hms.lib.commonmobileservices.mapkit.model.*

interface CommonMap : UISettings {

    companion object{
        const val MAP_TYPE_NONE = 0
        const val MAP_TYPE_NORMAL = 1
        const val MAP_TYPE_SATELLITE = 2
        const val MAP_TYPE_TERRAIN = 3
        const val MAP_TYPE_HYBRID = 4
    }

    fun getMapView(): View
    fun onCreate(bundle: Bundle?)
    fun getMapAsync(onMapReadyListener: (map: CommonMap) -> Unit)
    fun addMarker(
        title: String? = null,
        snippet: String? = null,
        latitude: Double,
        longitude: Double,
        iconBitmap: Bitmap? = null,
        anchor: Pair<Float, Float>? = null
    ): Marker

    fun addPolygon(polygonOptions: PolygonOptions): Polygon
    fun addPolyline(polylineOptions: PolylineOptions): Polyline

    fun setOnInfoWindowClickListener(
        markerClickCallback: (
            markerTitle: String?,
            markerSnippet: String?,
            latLng: LatLng
        ) -> Unit
    )

    fun setOnMapClickListener(onClick: (latLng: LatLng) -> Unit)
    fun moveCamera(latitude: Double, longitude: Double, zoomRatio: Float)
    fun animateCamera(latitude: Double, longitude: Double, zoomRatio: Float)
    fun setMyLocationEnabled(myLocationEnabled: Boolean?, context: Context): Boolean
    fun clear()
    fun onSaveInstanceState(bundle: Bundle)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
    fun onLowMemory()
    fun calculateDistanceBetweenPoints(p1: LatLng, p2: LatLng): Double
    fun getCameraPosition(): CameraPosition
    fun setOnCameraIdleListener(listener: () -> Unit)
    fun setOnCameraMoveStartedListener(listener: () -> Unit)
    fun getMaxZoomLevel(): Float
    fun getMinZoomLevel(): Float
    fun stopAnimation()
    fun addCircle(circleOptions: CircleOptions): Circle
    fun addGroundOverlay(groundOverlayOptions: GroundOverlayOptions): GroundOverlay
    fun addTileOverlay(tileOverlayOptions: TileOverlayOptions): TileOverlay
    fun setMapType(type: Int)
    fun getMapType():Int
    fun isTrafficEnabled(): Boolean
    fun setTrafficEnabled(enabled: Boolean)
    fun isIndoorEnabled(): Boolean
    fun setIndoorEnabled(enabled: Boolean)
}