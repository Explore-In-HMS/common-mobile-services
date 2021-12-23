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
import android.location.Location
import android.os.Bundle
import android.view.View
import com.hms.lib.commonmobileservices.mapkit.LocationSource
import com.hms.lib.commonmobileservices.mapkit.Projection
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
    fun isBuildingEnabled(): Boolean
    fun setBuildingEnabled(enabled: Boolean)
    fun isMyLocationEnabled(): Boolean
    fun setMyLocationEnabled(enabled: Boolean)
    fun setLocationSource(locationSource: LocationSource)
    fun getProjection(): Projection
    fun setOnCameraMoveListener(listener: OnCameraMoveListener)
    fun setOnCameraMoveStartedListener(listener: OnCameraMoveStartedListener)
    fun setOnCameraMoveCancelledListener(listener: OnCameraMoveCancelledListener)
    fun setOnCameraIdleListener(listener: OnCameraIdleListener)
    fun setOnMapClickListener(listener: OnMapClickListener)
    fun setOnMapLongClickListener(listener: OnMapLongClickListener)
    fun setOnMarkerClickListener(listener: OnMarkerClickListener)
    fun setOnMarkerDragListener(listener: OnMarkerDragListener)
    fun setOnInfoWindowClickListener(listener: OnInfoWindowClickListener)
    fun setOnInfoWindowLongClickListener(listener: OnInfoWindowLongClickListener)
    fun setOnInfoWindowCloseListener(listener: OnInfoWindowCloseListener)
    fun setInfoWindowAdapter(adapter: InfoWindowAdapter)
    fun setOnMyLocationClickListener(listener: OnMyLocationClickListener)
    fun setOnMyLocationButtonClickListener(listener: OnMyLocationButtonClickListener)
    fun setOnMapLoadedCallback(callback: OnMapLoadedCallback)
    fun setOnGroundOverlayClickListener(listener: OnGroundOverlayClickListener)
    fun setOnCircleClickListener(listener: OnCircleClickListener)
    fun setOnPolygonClickListener(listener: OnPolygonClickListener)
    fun setOnPolylineClickListener(listener: OnPolylineClickListener)
    fun snapshot(callback: SnapshotReadyCallback)
    fun snapshot(callback: SnapshotReadyCallback, bitmap: Bitmap)

    interface OnCameraMoveListener{
        fun onCameraMove()
    }

    abstract class OnCameraMoveStartedListener{
        companion object{
            const val REASON_GESTURE: Int = 1
            const val REASON_API_ANIMATION: Int = 2
            const val REASON_DEVELOPER_ANIMATION: Int = 3
        }

        abstract fun onCameraMoveStarted(var1: Int)

    }

    interface OnCameraMoveCancelledListener{
        fun onCameraMoveCancelled()
    }

    interface OnCameraIdleListener{
        fun onCameraIdle()
    }

    interface OnMapClickListener{
        fun onMapClick(latLng: LatLng)
    }

    interface OnMapLongClickListener{
        fun onMapLongClick(latLng: LatLng)
    }

    interface OnMarkerClickListener{
        fun onMarkerClick(marker: Marker?): Boolean
    }

    interface OnMarkerDragListener{
        fun onMarkerDragStart(marker: Marker?)
        fun onMarkerDrag(marker: Marker?)
        fun onMarkerDragEnd(marker: Marker?)
    }

    interface OnInfoWindowClickListener {
        fun onInfoWindowClick(marker: Marker?)
    }

    interface OnInfoWindowLongClickListener {
        fun onInfoWindowLongClick(marker: Marker?)
    }

    interface OnInfoWindowCloseListener {
        fun onInfoWindowClose(marker: Marker?)
    }

    interface InfoWindowAdapter{
        fun getInfoContents(marker: Marker?): View
        fun getInfoWindow(marker: Marker?): View
    }

    interface OnMyLocationClickListener {
        fun onMyLocationClick(location: Location)
    }

    interface OnMyLocationButtonClickListener {
        fun onMyLocationButtonClick(): Boolean
    }

    interface OnMapLoadedCallback {
        fun onMapLoaded()
    }

    interface OnGroundOverlayClickListener {
        fun onGroundOverlayClick(groundOverlay: GroundOverlay)
    }

    interface OnCircleClickListener {
        fun onCircleClick(circle: Circle)
    }

    interface OnPolygonClickListener {
        fun onPolygonClick(polygon: Polygon)
    }

    interface OnPolylineClickListener {
        fun onPolylineClick(polyline: Polyline)
    }

    interface SnapshotReadyCallback {
        fun onSnapshotReady(bitmap: Bitmap?)
    }
}