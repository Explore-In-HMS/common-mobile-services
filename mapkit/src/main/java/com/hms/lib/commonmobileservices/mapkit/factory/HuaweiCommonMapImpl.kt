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

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.hms.lib.commonmobileservices.mapkit.LocationSource
import com.hms.lib.commonmobileservices.mapkit.Projection
import com.hms.lib.commonmobileservices.mapkit.model.*
import com.hms.lib.commonmobileservices.mapkit.model.LatLng as CommonLatLng
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.MapStyleOptions
import com.huawei.hms.maps.model.MarkerOptions

class HuaweiCommonMapImpl(
    private val context: Context,
    apiKey: String? = null
) : BaseMapImpl() {

    private var mapView: MapView = MapView(context)

    private lateinit var map: HuaweiMap

    init {
        apiKey?.let {
            MapsInitializer.setApiKey(it)
        }
    }

    override fun getMapView(): View {
        return mapView
    }

    override fun onCreate(bundle: Bundle?) {
        mapView.onCreate(bundle)
    }

    override fun getMapAsync(onMapReadyListener: (map: CommonMap) -> Unit) {
        mapView.getMapAsync {
            map = it
            onMapReadyListener.invoke(this)
        }
    }

    override fun addPolygon(polygonOptions: PolygonOptions): Polygon {
        return map.addPolygon(polygonOptions.toHMSPolygonOptions()).toPolygon()
    }

    override fun addPolyline(polylineOptions: PolylineOptions): Polyline {
        return map.addPolyline(polylineOptions.toHMSPolylineOptions()).toPolyline()
    }

    override fun addMarker(
        title: String?,
        snippet: String?,
        latitude: Double,
        longitude: Double,
        iconBitmap: Bitmap?,
        anchor: Pair<Float, Float>?
    ): Marker {
        val markerOptions = MarkerOptions().apply {
            position(LatLng(latitude, longitude))
            title?.let { title(it) }
            snippet?.let { snippet(it) }
            iconBitmap?.let { icon(BitmapDescriptorFactory.fromBitmap(it)) }
            anchor?.let { anchor(it.first, it.second) }
        }
        return Marker(map.addMarker(markerOptions))
    }

    override fun setOnInfoWindowClickListener(
        markerClickCallback: (
            markerTitle: String?,
            markerSnippet: String?,
            latLng: CommonLatLng
        ) -> Unit
    ) {
        map.setOnInfoWindowClickListener { marker ->
            markerClickCallback.invoke(
                marker.title,
                marker.snippet,
                marker.position.toLatLng()
            )
        }
    }

    override fun setOnMapClickListener(onClick: (latLng: CommonLatLng) -> Unit) {
        map.setOnMapClickListener {
            onClick.invoke(CommonLatLng(it.latitude, it.longitude))
        }
    }

    override fun moveCamera(latitude: Double, longitude: Double, zoomRatio: Float) {
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoomRatio)
        )
    }

    override fun animateCamera(latitude: Double, longitude: Double, zoomRatio: Float) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoomRatio)
        )
    }

    override fun setMyLocationEnabled(myLocationEnabled: Boolean?): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        myLocationEnabled?.let {
            map.isMyLocationEnabled = it
            return true
        }?: run { return false }
    }

    override fun clear() {
        map.clear()
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        mapView.onSaveInstanceState(bundle)
    }

    override fun onStart() {
        mapView.onStart()
    }

    override fun onResume() {
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
    }

    override fun onStop() {
        mapView.onStop()
    }

    override fun onDestroy() {
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        mapView.onLowMemory()
    }

    override fun getCameraPosition(): CameraPosition {
        println("huawei map: $map")
        println("camera position huawei: ${map.cameraPosition.target}")
        return CameraPosition(
            CommonLatLng(map.cameraPosition.target.latitude, map.cameraPosition.target.longitude),
            map.cameraPosition.zoom,
            map.cameraPosition.tilt,
            map.cameraPosition.bearing
        )
    }

    override fun setOnCameraIdleListener(listener: CommonMap.OnCameraIdleListener) {
        map.setOnCameraIdleListener { listener.onCameraIdle() }
    }

    override fun setOnCameraMoveStartedListener(listener: CommonMap.OnCameraMoveStartedListener) {
        map.setOnCameraMoveStartedListener { listener.onCameraMoveStarted(it) }
    }

    override fun getMaxZoomLevel(): Float {
        return map.maxZoomLevel
    }

    override fun isCompassEnabled(): Boolean {
        return map.uiSettings.isCompassEnabled
    }

    override fun setCompassEnabled(compassEnabled: Boolean?) {
        compassEnabled?.let { map.uiSettings.isCompassEnabled = it }
    }

    override fun isIndoorLevelPickerEnabled(): Boolean {
        return map.uiSettings.isIndoorLevelPickerEnabled
    }

    override fun setIndoorLevelPickerEnabled(indoorLevelPickerEnabled: Boolean?) {
        indoorLevelPickerEnabled?.let { map.uiSettings.isIndoorLevelPickerEnabled = it }
    }

    override fun isMapToolbarEnabled(): Boolean {
        return map.uiSettings.isMapToolbarEnabled
    }

    override fun setMapToolbarEnabled(mapToolbarEnabled: Boolean?) {
        mapToolbarEnabled?.let { map.uiSettings.isMapToolbarEnabled = it }
    }

    override fun isMyLocationButtonEnabled(): Boolean {
        return map.uiSettings.isMyLocationButtonEnabled
    }

    override fun setMyLocationButtonEnabled(myLocationButtonEnabled: Boolean?) {
        myLocationButtonEnabled?.let { map.uiSettings.isMyLocationButtonEnabled = it }
    }

    override fun isRotateGesturesEnabled(): Boolean {
        return map.uiSettings.isRotateGesturesEnabled
    }

    override fun setRotateGesturesEnabled(rotateGesturesEnabled: Boolean?) {
        rotateGesturesEnabled?.let { map.uiSettings.isRotateGesturesEnabled = it }
    }

    override fun isScrollGesturesEnabled(): Boolean {
        return map.uiSettings.isScrollGesturesEnabled
    }

    override fun setScrollGesturesEnabled(scrollGesturesEnabled: Boolean?) {
        scrollGesturesEnabled?.let {
            map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = it
        }
    }

    override fun isScrollGesturesEnabledDuringRotateOrZoom(): Boolean {
        return map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom
    }

    override fun setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom: Boolean?) {
        scrollGesturesEnabledDuringRotateOrZoom?.let {
            map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = it
        }
    }

    override fun isTiltGesturesEnabled(): Boolean {
        return map.uiSettings.isTiltGesturesEnabled
    }

    override fun setTiltGesturesEnabled(tiltGesturesEnabled: Boolean?) {
        tiltGesturesEnabled?.let { map.uiSettings.isTiltGesturesEnabled = it }
    }

    override fun isZoomControlsEnabled(): Boolean {
        return map.uiSettings.isZoomControlsEnabled
    }

    override fun setZoomControlsEnabled(zoomControlsEnabled: Boolean?) {
        zoomControlsEnabled?.let { map.uiSettings.isZoomControlsEnabled = it }
    }

    override fun isZoomGesturesEnabled(): Boolean {
        return map.uiSettings.isZoomGesturesEnabled
    }

    override fun setZoomGesturesEnabled(zoomGesturesEnabled: Boolean?) {
        map.uiSettings.isZoomGesturesEnabled = true
    }

    override fun setAllGesturesEnabled(allGestureEnable: Boolean?) {
        allGestureEnable?.let { map.uiSettings.setAllGesturesEnabled(it) }
    }

    override fun getMinZoomLevel(): Float {
        return map.minZoomLevel
    }

    override fun stopAnimation() {
        map.stopAnimation()
    }

    override fun addCircle(circleOptions: CircleOptions): Circle {
        return map.addCircle(circleOptions.toHMSCircleOptions()).toCircle()
    }

    override fun addGroundOverlay(groundOverlayOptions: GroundOverlayOptions): GroundOverlay {
        return map.addGroundOverlay(groundOverlayOptions.toHmsGroundOverlayOptions())
            .toGroundOverlay()
    }

    override fun addTileOverlay(tileOverlayOptions: TileOverlayOptions): TileOverlay {
        return map.addTileOverlay(tileOverlayOptions.toHmsTileOverlayOptions()).toTileOverlay()
    }

    override fun setMapType(type: Int) {
        map.mapType = type
    }

    override fun getMapType(): Int {
        return map.mapType
    }

    override fun isTrafficEnabled(): Boolean {
        return map.isTrafficEnabled
    }

    override fun setTrafficEnabled(enabled: Boolean) {
        map.isTrafficEnabled = enabled
    }

    override fun isIndoorEnabled(): Boolean {
        return map.isIndoorEnabled
    }

    override fun setIndoorEnabled(enabled: Boolean) {
        map.isIndoorEnabled = enabled
    }

    override fun isBuildingEnabled(): Boolean {
        return map.isBuildingsEnabled
    }

    override fun setBuildingEnabled(enabled: Boolean) {
        map.isIndoorEnabled = enabled
    }

    override fun isMyLocationEnabled(): Boolean {
        return map.isMyLocationEnabled
    }

    override fun setMyLocationEnabled(enabled: Boolean) {
        map.isMyLocationEnabled = enabled
    }

    override fun setLocationSource(locationSource: LocationSource) {
        map.setLocationSource(object : com.huawei.hms.maps.LocationSource {
            override fun activate(p0: com.huawei.hms.maps.LocationSource.OnLocationChangedListener?) {
                locationSource.activate(object : LocationSource.OnLocationChangedListener {
                    override fun onLocationChanged(location: Location) {
                        p0?.onLocationChanged(location)
                    }
                })
            }

            override fun deactivate() {
                locationSource.deactivate()
            }

        })
    }

    override fun getProjection(): Projection {
        return map.projection.toProjection()
    }

    override fun setOnCameraMoveListener(listener: CommonMap.OnCameraMoveListener) {
        map.setOnCameraMoveListener {
            listener.onCameraMove()
        }
    }

    override fun setOnCameraMoveCancelledListener(listener: CommonMap.OnCameraMoveCancelledListener) {
        map.setOnCameraMoveCanceledListener { listener.onCameraMoveCancelled() }
    }

    override fun setOnMapClickListener(listener: CommonMap.OnMapClickListener) {
        map.setOnMapClickListener { listener.onMapClick(it.toLatLng()) }
    }

    override fun setOnMapLongClickListener(listener: CommonMap.OnMapLongClickListener) {
        map.setOnMapLongClickListener { listener.onMapLongClick(it.toLatLng()) }
    }

    override fun setOnMarkerClickListener(listener: CommonMap.OnMarkerClickListener) {
        map.setOnMarkerClickListener { listener.onMarkerClick(it.toMarker()) }
    }

    override fun setOnMarkerDragListener(listener: CommonMap.OnMarkerDragListener) {
        map.setOnMarkerDragListener(object : HuaweiMap.OnMarkerDragListener {
            override fun onMarkerDragStart(p0: HmsMarker?) {
                listener.onMarkerDragStart(p0?.toMarker())
            }

            override fun onMarkerDrag(p0: HmsMarker?) {
                listener.onMarkerDrag(p0?.toMarker())
            }

            override fun onMarkerDragEnd(p0: HmsMarker?) {
                listener.onMarkerDragEnd(p0?.toMarker())
            }

        })
    }

    override fun setOnInfoWindowClickListener(listener: CommonMap.OnInfoWindowClickListener) {
        map.setOnInfoWindowClickListener { listener.onInfoWindowClick(it?.toMarker()) }
    }

    override fun setOnInfoWindowLongClickListener(listener: CommonMap.OnInfoWindowLongClickListener) {
        map.setOnInfoWindowLongClickListener { listener.onInfoWindowLongClick(it?.toMarker()) }
    }

    override fun setOnInfoWindowCloseListener(listener: CommonMap.OnInfoWindowCloseListener) {
        map.setOnInfoWindowCloseListener { listener.onInfoWindowClose(it?.toMarker()) }
    }

    override fun setInfoWindowAdapter(adapter: CommonMap.InfoWindowAdapter) {
        map.setInfoWindowAdapter(object : HuaweiMap.InfoWindowAdapter {
            override fun getInfoContents(p0: HmsMarker?): View {
                return adapter.getInfoContents(p0?.toMarker())
            }

            override fun getInfoWindow(p0: HmsMarker?): View {
                return adapter.getInfoWindow(p0?.toMarker())
            }

        })
    }

    override fun setOnMyLocationClickListener(listener: CommonMap.OnMyLocationClickListener) {
        map.setOnMyLocationClickListener { listener.onMyLocationClick(it) }
    }

    override fun setOnMyLocationButtonClickListener(listener: CommonMap.OnMyLocationButtonClickListener) {
        map.setOnMyLocationButtonClickListener { listener.onMyLocationButtonClick() }
    }

    override fun setOnMapLoadedCallback(callback: CommonMap.OnMapLoadedCallback) {
        map.setOnMapLoadedCallback { callback.onMapLoaded() }
    }

    override fun setOnGroundOverlayClickListener(listener: CommonMap.OnGroundOverlayClickListener) {
        map.setOnGroundOverlayClickListener { listener.onGroundOverlayClick(it.toGroundOverlay()) }
    }

    override fun setOnCircleClickListener(listener: CommonMap.OnCircleClickListener) {
        map.setOnCircleClickListener { listener.onCircleClick(it.toCircle()) }
    }

    override fun setOnPolygonClickListener(listener: CommonMap.OnPolygonClickListener) {
        map.setOnPolygonClickListener { listener.onPolygonClick(it.toPolygon()) }
    }

    override fun setOnPolylineClickListener(listener: CommonMap.OnPolylineClickListener) {
        map.setOnPolylineClickListener { listener.onPolylineClick(it.toPolyline()) }
    }

    override fun snapshot(callback: CommonMap.SnapshotReadyCallback) {
        map.snapshot { callback.onSnapshotReady(it) }
    }

    override fun snapshot(callback: CommonMap.SnapshotReadyCallback, bitmap: Bitmap) {
        map.snapshot({ callback.onSnapshotReady(bitmap) }, bitmap)
    }

    override fun setPadding(var1: Int, var2: Int, var3: Int, var4: Int) {
        map.setPadding(var1, var2, var3, var4)
    }

    override fun setContentDescription(description: String) {
        map.setContentDescription(description)
    }

    override fun setMinZoomPreference(preference: Float) {
        map.setMinZoomPreference(preference)
    }

    override fun setMaxZoomPreference(preference: Float) {
        map.setMaxZoomPreference(preference)
    }

    override fun resetMinMaxZoomPreference() {
        map.resetMinMaxZoomPreference()
    }

    override fun setLatLngBoundsForCameraTarget(bounds: LatLngBounds) {
        map.setLatLngBoundsForCameraTarget(bounds.toHmsLatLngBounds())
    }

    override fun setMapStyle(json: String) {
        map.setMapStyle(MapStyleOptions(json))
    }

    override fun setMapStyleFromRawResource(resourceId: Int) {
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, resourceId))
    }
}
