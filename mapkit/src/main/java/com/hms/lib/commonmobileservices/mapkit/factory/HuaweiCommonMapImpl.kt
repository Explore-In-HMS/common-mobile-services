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

/**
 * Implementation of a common map using Huawei Maps SDK.
 *
 * @param context The context in which the map will be displayed.
 * @param apiKey The API key for accessing Huawei Maps services. Can be null if not provided.
 */
class HuaweiCommonMapImpl(
    private val context: Context,
    apiKey: String? = null
) : BaseMapImpl() {

    /**
     * The view representing the map.
     */
    private var mapView: MapView = MapView(context)

    /**
     * The HuaweiMap object for interacting with the map.
     */
    private lateinit var map: HuaweiMap

    init {
        // Initialize the API key if provided
        apiKey?.let {
            MapsInitializer.setApiKey(it)
        }
    }

    /**
     * Get the map view.
     *
     * @return The MapView object representing the map view.
     */
    override fun getMapView(): View {
        return mapView
    }

    /**
     * Called when the map is created.
     *
     * @param bundle If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onCreate(bundle: Bundle?) {
        mapView.onCreate(bundle)
    }

    /**
     * Asynchronously get the map object.
     *
     * @param onMapReadyListener Callback to be invoked when the map is ready.
     *                           It provides the ready HuaweiMap object.
     */
    override fun getMapAsync(onMapReadyListener: (map: CommonMap) -> Unit) {
        mapView.getMapAsync {
            map = it
            onMapReadyListener.invoke(this)
        }
    }

    /**
     * Adds a polygon to the map.
     *
     * @param polygonOptions Options for configuring the polygon.
     * @return The added Polygon object.
     */
    override fun addPolygon(polygonOptions: PolygonOptions): Polygon {
        return map.addPolygon(polygonOptions.toHMSPolygonOptions()).toPolygon()
    }

    /**
     * Adds a polyline to the map.
     *
     * @param polylineOptions Options for configuring the polyline.
     * @return The added Polyline object.
     */
    override fun addPolyline(polylineOptions: PolylineOptions): Polyline {
        return map.addPolyline(polylineOptions.toHMSPolylineOptions()).toPolyline()
    }

    /**
     * Adds a marker to the map.
     *
     * @param title The title of the marker.
     * @param snippet Additional text shown in the marker's info window when clicked.
     * @param latitude The latitude coordinate of the marker's position.
     * @param longitude The longitude coordinate of the marker's position.
     * @param iconBitmap The bitmap representing the marker icon.
     * @param anchor The anchor point of the marker's icon.
     * @return The added Marker object.
     */
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

    /**
     * Sets a callback for when an info window of a marker is clicked.
     *
     * @param markerClickCallback The callback to be invoked when an info window of a marker is clicked.
     */
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


    /**
     * Sets a callback for when the map is clicked.
     *
     * @param onClick The callback to be invoked when the map is clicked.
     */
    override fun setOnMapClickListener(onClick: (latLng: CommonLatLng) -> Unit) {
        map.setOnMapClickListener {
            onClick.invoke(CommonLatLng(it.latitude, it.longitude))
        }
    }

    /**
     * Moves the camera to the specified position with a certain zoom level.
     *
     * @param latitude The latitude coordinate of the target position.
     * @param longitude The longitude coordinate of the target position.
     * @param zoomRatio The zoom level of the camera.
     */
    override fun moveCamera(latitude: Double, longitude: Double, zoomRatio: Float) {
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoomRatio)
        )
    }

    /**
     * Animates the camera to the specified position with a certain zoom level.
     *
     * @param latitude The latitude coordinate of the target position.
     * @param longitude The longitude coordinate of the target position.
     * @param zoomRatio The zoom level of the camera.
     */
    override fun animateCamera(latitude: Double, longitude: Double, zoomRatio: Float) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoomRatio)
        )
    }

    /**
     * Enables or disables the display of the user's location on the map.
     *
     * @param myLocationEnabled Whether to enable or disable the display of the user's location.
     * @return True if the operation was successful, false otherwise.
     */
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
        } ?: run { return false }
    }

    /**
     * Clears the map.
     */
    override fun clear() {
        map.clear()
    }

    /**
     * Saves the current state of the map view.
     *
     * @param bundle The bundle in which to place the saved state.
     */
    override fun onSaveInstanceState(bundle: Bundle) {
        mapView.onSaveInstanceState(bundle)
    }

    /**
     * Called when the activity is starting.
     */
    override fun onStart() {
        mapView.onStart()
    }

    /**
     * Called when the activity is resumed from a paused state.
     */
    override fun onResume() {
        mapView.onResume()
    }

    /**
     * Called when the activity is paused.
     */
    override fun onPause() {
        mapView.onPause()
    }

    /**
     * Called when the activity is no longer visible to the user.
     */
    override fun onStop() {
        mapView.onStop()
    }

    /**
     * Called before the activity is destroyed.
     */
    override fun onDestroy() {
        mapView.onDestroy()
    }

    /**
     * Called when the overall system is running low on memory.
     */
    override fun onLowMemory() {
        mapView.onLowMemory()
    }

    /**
     * Retrieves the current camera position.
     *
     * @return The [CameraPosition] representing the current camera position.
     */
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

    /**
     * Sets a listener to be invoked when the camera movement has ended.
     *
     * @param listener The listener to set.
     */
    override fun setOnCameraIdleListener(listener: CommonMap.OnCameraIdleListener) {
        map.setOnCameraIdleListener { listener.onCameraIdle() }
    }

    /**
     * Sets a listener to be invoked when the camera movement has started.
     *
     * @param listener The listener to set.
     */
    override fun setOnCameraMoveStartedListener(listener: CommonMap.OnCameraMoveStartedListener) {
        map.setOnCameraMoveStartedListener { listener.onCameraMoveStarted(it) }
    }

    /**
     * Retrieves the maximum zoom level supported by the map.
     *
     * @return The maximum zoom level.
     */
    override fun getMaxZoomLevel(): Float {
        return map.maxZoomLevel
    }

    /**
     * Returns whether the compass is enabled on the map.
     *
     * @return `true` if the compass is enabled, `false` otherwise.
     */
    override fun isCompassEnabled(): Boolean {
        return map.uiSettings.isCompassEnabled
    }

    /**
     * Sets whether the compass is enabled on the map.
     *
     * @param compassEnabled `true` to enable the compass, `false` to disable it, or `null` to leave it unchanged.
     */
    override fun setCompassEnabled(compassEnabled: Boolean?) {
        compassEnabled?.let { map.uiSettings.isCompassEnabled = it }
    }

    /**
     * Returns whether the indoor level picker is enabled on the map.
     *
     * @return `true` if the indoor level picker is enabled, `false` otherwise.
     */
    override fun isIndoorLevelPickerEnabled(): Boolean {
        return map.uiSettings.isIndoorLevelPickerEnabled
    }

    /**
     * Sets whether the indoor level picker is enabled on the map.
     *
     * @param indoorLevelPickerEnabled `true` to enable the indoor level picker, `false` to disable it, or `null` to leave it unchanged.
     */
    override fun setIndoorLevelPickerEnabled(indoorLevelPickerEnabled: Boolean?) {
        indoorLevelPickerEnabled?.let { map.uiSettings.isIndoorLevelPickerEnabled = it }
    }

    /**
     * Returns whether the map toolbar is enabled on the map.
     *
     * @return `true` if the map toolbar is enabled, `false` otherwise.
     */
    override fun isMapToolbarEnabled(): Boolean {
        return map.uiSettings.isMapToolbarEnabled
    }

    /**
     * Sets whether the map toolbar is enabled on the map.
     *
     * @param mapToolbarEnabled `true` to enable the map toolbar, `false` to disable it, or `null` to leave it unchanged.
     */
    override fun setMapToolbarEnabled(mapToolbarEnabled: Boolean?) {
        mapToolbarEnabled?.let { map.uiSettings.isMapToolbarEnabled = it }
    }

    /**
     * Returns whether the "My Location" button is enabled on the map.
     *
     * @return `true` if the "My Location" button is enabled, `false` otherwise.
     */
    override fun isMyLocationButtonEnabled(): Boolean {
        return map.uiSettings.isMyLocationButtonEnabled
    }

    /**
     * Sets whether the "My Location" button is enabled on the map.
     *
     * @param myLocationButtonEnabled `true` to enable the "My Location" button, `false` to disable it, or `null` to leave it unchanged.
     */
    override fun setMyLocationButtonEnabled(myLocationButtonEnabled: Boolean?) {
        myLocationButtonEnabled?.let { map.uiSettings.isMyLocationButtonEnabled = it }
    }

    /**
     * Returns whether rotate gestures are enabled on the map.
     *
     * @return `true` if rotate gestures are enabled, `false` otherwise.
     */
    override fun isRotateGesturesEnabled(): Boolean {
        return map.uiSettings.isRotateGesturesEnabled
    }

    /**
     * Sets whether rotate gestures are enabled on the map.
     *
     * @param rotateGesturesEnabled `true` to enable rotate gestures, `false` to disable them, or `null` to leave them unchanged.
     */
    override fun setRotateGesturesEnabled(rotateGesturesEnabled: Boolean?) {
        rotateGesturesEnabled?.let { map.uiSettings.isRotateGesturesEnabled = it }
    }

    /**
     * Returns whether scroll gestures are enabled on the map.
     *
     * @return `true` if scroll gestures are enabled, `false` otherwise.
     */
    override fun isScrollGesturesEnabled(): Boolean {
        return map.uiSettings.isScrollGesturesEnabled
    }

    /**
     * Sets whether scroll gestures are enabled on the map.
     *
     * @param scrollGesturesEnabled `true` to enable scroll gestures, `false` to disable them, or `null` to leave them unchanged.
     */
    override fun setScrollGesturesEnabled(scrollGesturesEnabled: Boolean?) {
        scrollGesturesEnabled?.let {
            map.uiSettings.isScrollGesturesEnabled = it
        }
    }

    /**
     * Returns whether scroll gestures are enabled during rotate or zoom on the map.
     *
     * @return `true` if scroll gestures are enabled during rotate or zoom, `false` otherwise.
     */
    override fun isScrollGesturesEnabledDuringRotateOrZoom(): Boolean {
        return map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom
    }

    /**
     * Sets whether scroll gestures are enabled during rotate or zoom on the map.
     *
     * @param scrollGesturesEnabledDuringRotateOrZoom `true` to enable scroll gestures during rotate or zoom, `false` to disable them, or `null` to leave them unchanged.
     */
    override fun setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom: Boolean?) {
        scrollGesturesEnabledDuringRotateOrZoom?.let {
            map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = it
        }
    }

    /**
     * Returns whether tilt gestures are enabled on the map.
     *
     * @return `true` if tilt gestures are enabled, `false` otherwise.
     */
    override fun isTiltGesturesEnabled(): Boolean {
        return map.uiSettings.isTiltGesturesEnabled
    }

    /**
     * Sets whether tilt gestures are enabled on the map.
     *
     * @param tiltGesturesEnabled `true` to enable tilt gestures, `false` to disable them, or `null` to leave them unchanged.
     */
    override fun setTiltGesturesEnabled(tiltGesturesEnabled: Boolean?) {
        tiltGesturesEnabled?.let { map.uiSettings.isTiltGesturesEnabled = it }
    }

    /**
     * Returns whether zoom controls are enabled on the map.
     *
     * @return `true` if zoom controls are enabled, `false` otherwise.
     */
    override fun isZoomControlsEnabled(): Boolean {
        return map.uiSettings.isZoomControlsEnabled
    }

    /**
     * Sets whether zoom controls are enabled on the map.
     *
     * @param zoomControlsEnabled `true` to enable zoom controls, `false` to disable them, or `null` to leave them unchanged.
     */
    override fun setZoomControlsEnabled(zoomControlsEnabled: Boolean?) {
        zoomControlsEnabled?.let { map.uiSettings.isZoomControlsEnabled = it }
    }

    /**
     * Returns whether zoom gestures are enabled on the map.
     *
     * @return `true` if zoom gestures are enabled, `false` otherwise.
     */
    override fun isZoomGesturesEnabled(): Boolean {
        return map.uiSettings.isZoomGesturesEnabled
    }

    /**
     * Sets whether zoom gestures are enabled on the map.
     *
     * @param zoomGesturesEnabled `true` to enable zoom gestures, `false` to disable them, or `null` to leave them unchanged.
     */
    override fun setZoomGesturesEnabled(zoomGesturesEnabled: Boolean?) {
        zoomGesturesEnabled?.let { map.uiSettings.isZoomGesturesEnabled = it }
    }

    /**
     * Sets whether all gestures are enabled on the map.
     *
     * @param allGestureEnable `true` to enable all gestures, `false` to disable them, or `null` to leave them unchanged.
     */
    override fun setAllGesturesEnabled(allGestureEnable: Boolean?) {
        allGestureEnable?.let { map.uiSettings.setAllGesturesEnabled(it) }
    }

    /**
     * Returns the minimum zoom level supported by the map.
     *
     * @return The minimum zoom level.
     */
    override fun getMinZoomLevel(): Float {
        return map.minZoomLevel
    }

    /**
     * Stops any ongoing animations on the map.
     */
    override fun stopAnimation() {
        map.stopAnimation()
    }

    /**
     * Adds a circle to the map with the specified options.
     *
     * @param circleOptions The options for the circle.
     * @return The added Circle object.
     */
    override fun addCircle(circleOptions: CircleOptions): Circle {
        return map.addCircle(circleOptions.toHMSCircleOptions()).toCircle()
    }

    /**
     * Adds a ground overlay to the map with the specified options.
     *
     * @param groundOverlayOptions The options for the ground overlay.
     * @return The added GroundOverlay object.
     */
    override fun addGroundOverlay(groundOverlayOptions: GroundOverlayOptions): GroundOverlay {
        return map.addGroundOverlay(groundOverlayOptions.toHmsGroundOverlayOptions())
            .toGroundOverlay()
    }

    /**
     * Adds a tile overlay to the map with the specified options.
     *
     * @param tileOverlayOptions The options for the tile overlay.
     * @return The added TileOverlay object.
     */
    override fun addTileOverlay(tileOverlayOptions: TileOverlayOptions): TileOverlay {
        return map.addTileOverlay(tileOverlayOptions.toHmsTileOverlayOptions()).toTileOverlay()
    }

    /**
     * Sets the map type.
     *
     * @param type The type of the map to be set.
     */
    override fun setMapType(type: Int) {
        map.mapType = type
    }

    /**
     * Retrieves the current map type.
     *
     * @return The current map type.
     */
    override fun getMapType(): Int {
        return map.mapType
    }

    /**
     * Returns whether traffic information is enabled on the map.
     *
     * @return `true` if traffic information is enabled, `false` otherwise.
     */
    override fun isTrafficEnabled(): Boolean {
        return map.isTrafficEnabled
    }

    /**
     * Sets whether to enable traffic information on the map.
     *
     * @param enabled `true` to enable traffic information, `false` to disable it.
     */
    override fun setTrafficEnabled(enabled: Boolean) {
        map.isTrafficEnabled = enabled
    }

    /**
     * Returns whether indoor maps are enabled on the map.
     *
     * @return `true` if indoor maps are enabled, `false` otherwise.
     */
    override fun isIndoorEnabled(): Boolean {
        return map.isIndoorEnabled
    }

    /**
     * Sets whether to enable indoor maps on the map.
     *
     * @param enabled `true` to enable indoor maps, `false` to disable them.
     */
    override fun setIndoorEnabled(enabled: Boolean) {
        map.isIndoorEnabled = enabled
    }

    /**
     * Returns whether building information is enabled on the map.
     *
     * @return `true` if building information is enabled, `false` otherwise.
     */
    override fun isBuildingEnabled(): Boolean {
        return map.isBuildingsEnabled
    }

    /**
     * Sets whether to enable building information on the map.
     *
     * @param enabled `true` to enable building information, `false` to disable it.
     */
    override fun setBuildingEnabled(enabled: Boolean) {
        map.isBuildingsEnabled = enabled
    }

    /**
     * Returns whether the "My Location" feature is enabled on the map.
     *
     * @return `true` if the "My Location" feature is enabled, `false` otherwise.
     */
    override fun isMyLocationEnabled(): Boolean {
        return map.isMyLocationEnabled
    }

    /**
     * Sets whether to enable the "My Location" feature on the map.
     *
     * @param enabled `true` to enable the "My Location" feature, `false` to disable it.
     */
    override fun setMyLocationEnabled(enabled: Boolean) {
        map.isMyLocationEnabled = enabled
    }

    /**
     * Sets the location source for the map.
     *
     * @param locationSource The location source to be set.
     */
    override fun setLocationSource(locationSource: LocationSource) {
        map.setLocationSource(object : com.huawei.hms.maps.LocationSource {
            override fun activate(listener: com.huawei.hms.maps.LocationSource.OnLocationChangedListener?) {
                locationSource.activate(object : LocationSource.OnLocationChangedListener {
                    override fun onLocationChanged(location: Location) {
                        listener?.onLocationChanged(location)
                    }
                })
            }

            override fun deactivate() {
                locationSource.deactivate()
            }
        })
    }

    /**
     * Retrieves the projection of the map.
     *
     * @return The Projection object representing the projection of the map.
     */
    override fun getProjection(): Projection {
        return map.projection.toProjection()
    }

    /**
     * Sets a listener to be invoked when the camera starts moving.
     *
     * @param listener The listener to set.
     */
    override fun setOnCameraMoveListener(listener: CommonMap.OnCameraMoveListener) {
        map.setOnCameraMoveListener {
            listener.onCameraMove()
        }
    }

    /**
     * Sets a listener to be invoked when the camera movement is cancelled.
     *
     * @param listener The listener to set.
     */
    override fun setOnCameraMoveCancelledListener(listener: CommonMap.OnCameraMoveCancelledListener) {
        map.setOnCameraMoveCanceledListener { listener.onCameraMoveCancelled() }
    }

    /**
     * Sets a listener to be invoked when the map is clicked.
     *
     * @param listener The listener to set.
     */
    override fun setOnMapClickListener(listener: CommonMap.OnMapClickListener) {
        map.setOnMapClickListener { listener.onMapClick(it.toLatLng()) }
    }

    /**
     * Sets a listener to be invoked when the map is long-clicked.
     *
     * @param listener The listener to set.
     */
    override fun setOnMapLongClickListener(listener: CommonMap.OnMapLongClickListener) {
        map.setOnMapLongClickListener { listener.onMapLongClick(it.toLatLng()) }
    }

    /**
     * Sets a listener to be invoked when a marker is clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnMarkerClickListener(listener: CommonMap.OnMarkerClickListener) {
        map.setOnMarkerClickListener { listener.onMarkerClick(it.toMarker()) }
    }

    /**
     * Sets a listener to be invoked when a marker drag event occurs on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnMarkerDragListener(listener: CommonMap.OnMarkerDragListener) {
        map.setOnMarkerDragListener(object : HuaweiMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: HmsMarker?) {
                listener.onMarkerDragStart(marker?.toMarker())
            }

            override fun onMarkerDrag(marker: HmsMarker?) {
                listener.onMarkerDrag(marker?.toMarker())
            }

            override fun onMarkerDragEnd(marker: HmsMarker?) {
                listener.onMarkerDragEnd(marker?.toMarker())
            }
        })
    }

    /**
     * Sets a listener to be invoked when an info window is clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnInfoWindowClickListener(listener: CommonMap.OnInfoWindowClickListener) {
        map.setOnInfoWindowClickListener { listener.onInfoWindowClick(it?.toMarker()) }
    }

    /**
     * Sets a listener to be invoked when an info window is long-clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnInfoWindowLongClickListener(listener: CommonMap.OnInfoWindowLongClickListener) {
        map.setOnInfoWindowLongClickListener { listener.onInfoWindowLongClick(it?.toMarker()) }
    }

    /**
     * Sets a listener to be invoked when an info window is closed on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnInfoWindowCloseListener(listener: CommonMap.OnInfoWindowCloseListener) {
        map.setOnInfoWindowCloseListener { listener.onInfoWindowClose(it?.toMarker()) }
    }

    /**
     * Sets a custom info window adapter for the map.
     *
     * @param adapter The adapter to set.
     */
    override fun setInfoWindowAdapter(adapter: CommonMap.InfoWindowAdapter) {
        map.setInfoWindowAdapter(object : HuaweiMap.InfoWindowAdapter {
            override fun getInfoContents(marker: HmsMarker?): View {
                return adapter.getInfoContents(marker?.toMarker())
            }

            override fun getInfoWindow(marker: HmsMarker?): View {
                return adapter.getInfoWindow(marker?.toMarker())
            }
        })
    }

    /**
     * Sets a listener to be invoked when the "My Location" button is clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnMyLocationClickListener(listener: CommonMap.OnMyLocationClickListener) {
        map.setOnMyLocationClickListener { listener.onMyLocationClick(it) }
    }

    /**
     * Sets a listener to be invoked when the "My Location" button is clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnMyLocationButtonClickListener(listener: CommonMap.OnMyLocationButtonClickListener) {
        map.setOnMyLocationButtonClickListener { listener.onMyLocationButtonClick() }
    }

    /**
     * Sets a callback to be invoked when the map has finished loading.
     *
     * @param callback The callback to set.
     */
    override fun setOnMapLoadedCallback(callback: CommonMap.OnMapLoadedCallback) {
        map.setOnMapLoadedCallback { callback.onMapLoaded() }
    }

    /**
     * Sets a listener to be invoked when a ground overlay is clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnGroundOverlayClickListener(listener: CommonMap.OnGroundOverlayClickListener) {
        map.setOnGroundOverlayClickListener { listener.onGroundOverlayClick(it.toGroundOverlay()) }
    }

    /**
     * Sets a listener to be invoked when a circle is clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnCircleClickListener(listener: CommonMap.OnCircleClickListener) {
        map.setOnCircleClickListener { listener.onCircleClick(it.toCircle()) }
    }

    /**
     * Sets a listener to be invoked when a polygon is clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnPolygonClickListener(listener: CommonMap.OnPolygonClickListener) {
        map.setOnPolygonClickListener { listener.onPolygonClick(it.toPolygon()) }
    }

    /**
     * Sets a listener to be invoked when a polyline is clicked on the map.
     *
     * @param listener The listener to set.
     */
    override fun setOnPolylineClickListener(listener: CommonMap.OnPolylineClickListener) {
        map.setOnPolylineClickListener { listener.onPolylineClick(it.toPolyline()) }
    }

    /**
     * Takes a snapshot of the map.
     *
     * @param callback The callback to be invoked when the snapshot is ready.
     */
    override fun snapshot(callback: CommonMap.SnapshotReadyCallback) {
        map.snapshot { callback.onSnapshotReady(it) }
    }

    /**
     * Takes a snapshot of the map with a custom bitmap overlay.
     *
     * @param callback The callback to be invoked when the snapshot is ready.
     * @param bitmap The bitmap overlay to be applied to the snapshot.
     */
    override fun snapshot(callback: CommonMap.SnapshotReadyCallback, bitmap: Bitmap) {
        map.snapshot({ callback.onSnapshotReady(bitmap) }, bitmap)
    }

    /**
     * Sets padding for the map.
     *
     * @param var1 The left padding.
     * @param var2 The top padding.
     * @param var3 The right padding.
     * @param var4 The bottom padding.
     */
    override fun setPadding(var1: Int, var2: Int, var3: Int, var4: Int) {
        map.setPadding(var1, var2, var3, var4)
    }

    /**
     * Sets the content description for the map.
     *
     * @param description The content description to set.
     */
    override fun setContentDescription(description: String) {
        map.setContentDescription(description)
    }

    /**
     * Sets the minimum zoom preference for the map.
     *
     * @param preference The minimum zoom preference to set.
     */
    override fun setMinZoomPreference(preference: Float) {
        map.setMinZoomPreference(preference)
    }

    /**
     * Sets the maximum zoom preference for the map.
     *
     * @param preference The maximum zoom preference to set.
     */
    override fun setMaxZoomPreference(preference: Float) {
        map.setMaxZoomPreference(preference)
    }

    /**
     * Resets the minimum and maximum zoom preferences for the map.
     */
    override fun resetMinMaxZoomPreference() {
        map.resetMinMaxZoomPreference()
    }

    /**
     * Sets a LatLngBounds for constraining the camera target.
     *
     * @param bounds The bounds to set.
     */
    override fun setLatLngBoundsForCameraTarget(bounds: LatLngBounds) {
        map.setLatLngBoundsForCameraTarget(bounds.toHmsLatLngBounds())
    }

    /**
     * Sets the map style using a JSON string.
     *
     * @param json The JSON string representing the map style.
     */
    override fun setMapStyle(json: String) {
        map.setMapStyle(MapStyleOptions(json))
    }

    /**
     * Sets the map style using a raw resource ID.
     *
     * @param resourceId The resource ID of the raw JSON file representing the map style.
     */
    override fun setMapStyleFromRawResource(resourceId: Int) {
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, resourceId))
    }
}
