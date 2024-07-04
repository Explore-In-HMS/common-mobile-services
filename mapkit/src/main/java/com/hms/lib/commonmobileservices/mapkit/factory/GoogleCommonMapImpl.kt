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
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.hms.lib.commonmobileservices.mapkit.LocationSource
import com.hms.lib.commonmobileservices.mapkit.Projection
import com.hms.lib.commonmobileservices.mapkit.model.*

/**
 * Implementation of [BaseMapImpl] for Google Maps.
 * This class provides functionalities to interact with Google Maps.
 *
 * @param context The context used to instantiate the class.
 */
class GoogleCommonMapImpl(private val context: Context) : BaseMapImpl() {

    /**
     * Private property representing the MapView used in the implementation.
     * Initialized with the provided context.
     */
    private var mapView: MapView = MapView(context)

    /**
     * Late-initialized property representing the GoogleMap object.
     * It will be assigned when the map is ready asynchronously.
     */
    private lateinit var map: GoogleMap

    /**
     * Retrieves the MapView associated with this implementation.
     *
     * @return The MapView object.
     */
    override fun getMapView(): View {
        return mapView
    }

    /**
     * Initializes the MapView, typically called from the activity's onCreate method.
     *
     * @param bundle The Bundle object passed from the activity's onCreate method.
     */
    override fun onCreate(bundle: Bundle?) {
        mapView.onCreate(bundle)
    }

    /**
     * Asynchronously retrieves the GoogleMap object associated with the MapView.
     * Triggers the provided callback once the map is ready.
     *
     * @param onMapReadyListener The callback to be invoked when the GoogleMap object is ready.
     */
    override fun getMapAsync(onMapReadyListener: (map: CommonMap) -> Unit) {
        mapView.getMapAsync {
            map = it
            onMapReadyListener.invoke(this)
        }
    }

    /**
     * Adds a polygon to the map with the specified options.
     *
     * @param polygonOptions The options for the polygon to be added.
     * @return The Polygon object representing the added polygon.
     */
    override fun addPolygon(polygonOptions: PolygonOptions): Polygon {
        return map.addPolygon(polygonOptions.toGMSPolygonOptions()).toPolygon()
    }

    /**
     * Adds a polyline to the map with the specified options.
     *
     * @param polylineOptions The options for the polyline to be added.
     * @return The Polyline object representing the added polyline.
     */
    override fun addPolyline(polylineOptions: PolylineOptions): Polyline {
        return map.addPolyline(polylineOptions.toGMSPolylineOptions()).toPolyline()
    }

    /**
     * Adds a marker to the map with the specified options.
     *
     * @param title The title of the marker, or null if no title is set.
     * @param snippet The snippet of the marker, or null if no snippet is set.
     * @param latitude The latitude of the marker's position.
     * @param longitude The longitude of the marker's position.
     * @param iconBitmap The bitmap representing the icon of the marker, or null to use the default marker icon.
     * @param anchor The anchor point of the marker's icon, specified as a pair of floats representing the horizontal
     * and vertical offsets relative to the marker's position. If null, the default anchor is used.
     * @return The Marker object representing the added marker.
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
     * Sets a callback to be invoked when an info window is clicked on the map.
     *
     * @param markerClickCallback The callback function to be invoked when an info window is clicked.
     * This function takes three parameters:
     * - markerTitle: The title of the clicked marker, or null if no title is set.
     * - markerSnippet: The snippet of the clicked marker, or null if no snippet is set.
     * - latLng: The geographical position of the clicked marker.
     */
    override fun setOnInfoWindowClickListener(
        markerClickCallback: (
            markerTitle: String?,
            markerSnippet: String?,
            latLng: com.hms.lib.commonmobileservices.mapkit.model.LatLng
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
     * Moves the camera to a specified position and zoom level instantly.
     *
     * @param latitude The latitude of the camera position.
     * @param longitude The longitude of the camera position.
     * @param zoomRatio The zoom level of the camera position.
     */
    override fun moveCamera(latitude: Double, longitude: Double, zoomRatio: Float) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoomRatio))
    }

    /**
     * Animates the camera to a specified position and zoom level.
     *
     * @param latitude The latitude of the camera position.
     * @param longitude The longitude of the camera position.
     * @param zoomRatio The zoom level of the camera position.
     */
    override fun animateCamera(latitude: Double, longitude: Double, zoomRatio: Float) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoomRatio))
    }

    /**
     * Enables or disables the display of the user's location on the map.
     *
     * @param myLocationEnabled True to enable displaying the user's location, false otherwise.
     * @return True if the operation was successful or if the location permission is not granted, false otherwise.
     */
    override fun setMyLocationEnabled(myLocationEnabled: Boolean?): Boolean {
        // Check if the necessary location permissions are granted
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

        // Enable or disable the display of the user's location based on the provided parameter
        myLocationEnabled?.let {
            map.isMyLocationEnabled = it
            return true
        } ?: run { return false }
    }

    /**
     * Clears all markers, polylines, polygons, and other overlays from the map.
     */
    override fun clear() {
        map.clear()
    }

    /**
     * Called to save the current state of the map when the activity's onSaveInstanceState method is called.
     *
     * @param bundle The bundle in which to save the map's state.
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
     * Called when the activity is resumed.
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
     * Called when the activity is stopped.
     */
    override fun onStop() {
        mapView.onStop()
    }

    /**
     * Called when the activity is destroyed.
     */
    override fun onDestroy() {
        mapView.onDestroy()
    }

    /**
     * Called when the system needs to reclaim memory.
     */
    override fun onLowMemory() {
        mapView.onLowMemory()
    }

    /**
     * Retrieves the current position of the camera.
     *
     * @return The current camera position.
     */
    override fun getCameraPosition(): CameraPosition = CameraPosition(
        com.hms.lib.commonmobileservices.mapkit.model.LatLng(
            map.cameraPosition.target.latitude,
            map.cameraPosition.target.longitude
        ),
        map.cameraPosition.zoom,
        map.cameraPosition.tilt,
        map.cameraPosition.bearing
    )

    /**
     * Sets a listener to be notified when the camera becomes idle after a movement.
     *
     * @param listener The listener to be notified.
     */
    override fun setOnCameraIdleListener(listener: CommonMap.OnCameraIdleListener) {
        map.setOnCameraIdleListener { listener.onCameraIdle() }
    }

    /**
     * Sets a listener to be notified when the camera movement has started.
     *
     * @param listener The listener to be notified.
     */
    override fun setOnCameraMoveStartedListener(listener: CommonMap.OnCameraMoveStartedListener) {
        map.setOnCameraMoveStartedListener { reason ->
            listener.onCameraMoveStarted(reason)
        }
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
     * Retrieves the minimum zoom level supported by the map.
     *
     * @return The minimum zoom level.
     */
    override fun getMinZoomLevel(): Float {
        return map.minZoomLevel
    }

    /**
     * Checks if the compass is enabled on the map.
     *
     * @return `true` if the compass is enabled, `false` otherwise.
     */
    override fun isCompassEnabled(): Boolean {
        return map.uiSettings.isCompassEnabled
    }

    /**
     * Sets whether the compass should be enabled on the map.
     *
     * @param compassEnabled A boolean indicating whether the compass should be enabled.
     */
    override fun setCompassEnabled(compassEnabled: Boolean?) {
        compassEnabled?.let { map.uiSettings.isCompassEnabled = it }
    }

    /**
     * Checks if the indoor level picker is enabled on the map.
     *
     * @return `true` if the indoor level picker is enabled, `false` otherwise.
     */
    override fun isIndoorLevelPickerEnabled(): Boolean {
        return map.uiSettings.isIndoorLevelPickerEnabled
    }

    /**
     * Sets whether the indoor level picker should be enabled on the map.
     *
     * @param indoorLevelPickerEnabled A boolean indicating whether the indoor level picker should be enabled.
     */
    override fun setIndoorLevelPickerEnabled(indoorLevelPickerEnabled: Boolean?) {
        indoorLevelPickerEnabled?.let { map.uiSettings.isIndoorLevelPickerEnabled = it }
    }

    /**
     * Checks if the map toolbar is enabled on the map.
     *
     * @return `true` if the map toolbar is enabled, `false` otherwise.
     */
    override fun isMapToolbarEnabled(): Boolean {
        return map.uiSettings.isMapToolbarEnabled
    }

    /**
     * Sets whether the map toolbar should be enabled on the map.
     *
     * @param mapToolbarEnabled A boolean indicating whether the map toolbar should be enabled.
     */
    override fun setMapToolbarEnabled(mapToolbarEnabled: Boolean?) {
        mapToolbarEnabled?.let { map.uiSettings.isMapToolbarEnabled = it }
    }

    /**
     * Checks if the "My Location" button is enabled on the map.
     *
     * @return `true` if the "My Location" button is enabled, `false` otherwise.
     */
    override fun isMyLocationButtonEnabled(): Boolean {
        return map.uiSettings.isMyLocationButtonEnabled
    }

    /**
     * Sets whether the "My Location" button should be enabled on the map.
     *
     * @param myLocationButtonEnabled A boolean indicating whether the "My Location" button should be enabled.
     */
    override fun setMyLocationButtonEnabled(myLocationButtonEnabled: Boolean?) {
        myLocationButtonEnabled?.let { map.uiSettings.isMyLocationButtonEnabled = it }
    }

    /**
     * Checks if rotate gestures are enabled on the map.
     *
     * @return `true` if rotate gestures are enabled, `false` otherwise.
     */
    override fun isRotateGesturesEnabled(): Boolean {
        return map.uiSettings.isRotateGesturesEnabled
    }

    /**
     * Sets whether rotate gestures should be enabled on the map.
     *
     * @param rotateGesturesEnabled A boolean indicating whether rotate gestures should be enabled.
     */
    override fun setRotateGesturesEnabled(rotateGesturesEnabled: Boolean?) {
        rotateGesturesEnabled?.let { map.uiSettings.isRotateGesturesEnabled = it }
    }

    /**
     * Checks if scroll gestures are enabled on the map.
     *
     * @return `true` if scroll gestures are enabled, `false` otherwise.
     */
    override fun isScrollGesturesEnabled(): Boolean {
        return map.uiSettings.isScrollGesturesEnabled
    }

    /**
     * Sets whether scroll gestures should be enabled on the map.
     *
     * @param scrollGesturesEnabled A boolean indicating whether scroll gestures should be enabled.
     */
    override fun setScrollGesturesEnabled(scrollGesturesEnabled: Boolean?) {
        scrollGesturesEnabled?.let {
            map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = it
        }
    }

    /**
     * Checks if scroll gestures during rotate or zoom are enabled on the map.
     *
     * @return `true` if scroll gestures during rotate or zoom are enabled, `false` otherwise.
     */
    override fun isScrollGesturesEnabledDuringRotateOrZoom(): Boolean {
        return map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom
    }

    /**
     * Sets whether scroll gestures during rotate or zoom should be enabled on the map.
     *
     * @param scrollGesturesEnabledDuringRotateOrZoom A boolean indicating whether scroll gestures during rotate or zoom should be enabled.
     */
    override fun setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom: Boolean?) {
        scrollGesturesEnabledDuringRotateOrZoom?.let {
            map.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = it
        }
    }

    /**
     * Checks if tilt gestures are enabled on the map.
     *
     * @return `true` if tilt gestures are enabled, `false` otherwise.
     */
    override fun isTiltGesturesEnabled(): Boolean {
        return map.uiSettings.isTiltGesturesEnabled
    }

    /**
     * Sets whether tilt gestures should be enabled on the map.
     *
     * @param tiltGesturesEnabled A boolean indicating whether tilt gestures should be enabled.
     */
    override fun setTiltGesturesEnabled(tiltGesturesEnabled: Boolean?) {
        tiltGesturesEnabled?.let { map.uiSettings.isTiltGesturesEnabled = it }
    }

    /**
     * Checks if zoom controls are enabled on the map.
     *
     * @return `true` if zoom controls are enabled, `false` otherwise.
     */
    override fun isZoomControlsEnabled(): Boolean {
        return map.uiSettings.isZoomControlsEnabled
    }

    /**
     * Sets whether zoom controls should be enabled on the map.
     *
     * @param zoomControlsEnabled A boolean indicating whether zoom controls should be enabled.
     */
    override fun setZoomControlsEnabled(zoomControlsEnabled: Boolean?) {
        zoomControlsEnabled?.let { map.uiSettings.isZoomControlsEnabled = it }
    }

    /**
     * Checks if zoom gestures are enabled on the map.
     *
     * @return `true` if zoom gestures are enabled, `false` otherwise.
     */
    override fun isZoomGesturesEnabled(): Boolean {
        return map.uiSettings.isZoomGesturesEnabled
    }

    /**
     * Sets whether zoom gestures should be enabled on the map.
     *
     * @param zoomGesturesEnabled A boolean indicating whether zoom gestures should be enabled.
     */
    override fun setZoomGesturesEnabled(zoomGesturesEnabled: Boolean?) {
        map.uiSettings.isZoomGesturesEnabled = true
    }

    /**
     * Sets whether all gestures should be enabled on the map.
     *
     * @param allGestureEnable A boolean indicating whether all gestures should be enabled.
     */
    override fun setAllGesturesEnabled(allGestureEnable: Boolean?) {
        allGestureEnable?.let { map.uiSettings.setAllGesturesEnabled(it) }
    }

    /**
     * Sets a listener for when the map is clicked.
     *
     * @param onClick The callback function to be invoked when the map is clicked, providing the clicked LatLng.
     */
    override fun setOnMapClickListener(onClick: (latLng: com.hms.lib.commonmobileservices.mapkit.model.LatLng) -> Unit) {
        map.setOnMapClickListener {
            onClick.invoke(
                com.hms.lib.commonmobileservices.mapkit.model.LatLng(
                    it.latitude,
                    it.longitude
                )
            )
        }
    }

    /**
     * Stops any ongoing camera animation.
     */
    override fun stopAnimation() {
        map.stopAnimation()
    }

    /**
     * Adds a circle to the map with the specified options.
     *
     * @param circleOptions The options for the circle to be added.
     * @return The newly added Circle object.
     */
    override fun addCircle(circleOptions: CircleOptions): Circle {
        return map.addCircle(circleOptions.toGmsCircleOptions()).toCircle()
    }

    /**
     * Adds a ground overlay to the map with the specified options.
     *
     * @param groundOverlayOptions The options for the ground overlay to be added.
     * @return The newly added GroundOverlay object.
     */
    override fun addGroundOverlay(groundOverlayOptions: GroundOverlayOptions): GroundOverlay {
        return map.addGroundOverlay(groundOverlayOptions.toGmsGroundOverlayOptions())!!
            .toGroundOverlay()
    }

    /**
     * Adds a tile overlay to the map with the specified options.
     *
     * @param tileOverlayOptions The options for the tile overlay to be added.
     * @return The newly added TileOverlay object.
     */
    override fun addTileOverlay(tileOverlayOptions: TileOverlayOptions): TileOverlay {
        return map.addTileOverlay(tileOverlayOptions.toGmsTileOverlayOptions())!!.toTileOverlay()
    }

    /**
     * Sets the type of map to be displayed.
     *
     * @param type The type of map to be displayed. Use one of the constants defined in the companion object of this class.
     */
    override fun setMapType(type: Int) {
        map.mapType = type
    }

    /**
     * Gets the current type of map being displayed.
     *
     * @return The type of map being displayed. It will be one of the constants defined in the companion object of this class.
     */
    override fun getMapType(): Int {
        return map.mapType
    }

    /**
     * Checks if traffic data is enabled on the map.
     *
     * @return True if traffic data is enabled, false otherwise.
     */
    override fun isTrafficEnabled(): Boolean {
        return map.isTrafficEnabled
    }

    /**
     * Sets whether traffic data should be enabled on the map.
     *
     * @param enabled True to enable traffic data, false to disable.
     */
    override fun setTrafficEnabled(enabled: Boolean) {
        map.isTrafficEnabled = enabled
    }

    /**
     * Checks if indoor maps are enabled on the map.
     *
     * @return True if indoor maps are enabled, false otherwise.
     */
    override fun isIndoorEnabled(): Boolean {
        return map.isIndoorEnabled
    }

    /**
     * Sets whether indoor maps should be enabled on the map.
     *
     * @param enabled True to enable indoor maps, false to disable.
     */
    override fun setIndoorEnabled(enabled: Boolean) {
        map.isIndoorEnabled = enabled
    }

    /**
     * Checks if 3D buildings layer is enabled on the map.
     *
     * @return True if the 3D buildings layer is enabled, false otherwise.
     */
    override fun isBuildingEnabled(): Boolean {
        return map.isBuildingsEnabled
    }

    /**
     * Sets whether the 3D buildings layer should be enabled on the map.
     *
     * @param enabled True to enable the 3D buildings layer, false to disable.
     */
    override fun setBuildingEnabled(enabled: Boolean) {
        map.isBuildingsEnabled = enabled
    }

    /**
     * Checks if the "My Location" layer is enabled on the map.
     *
     * @return True if the "My Location" layer is enabled, false otherwise.
     */
    override fun isMyLocationEnabled(): Boolean {
        return map.isMyLocationEnabled
    }

    /**
     * Sets whether the "My Location" layer should be enabled on the map.
     * Requires either ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permission.
     *
     * @param enabled True to enable the "My Location" layer, false to disable.
     */
    @RequiresPermission(
        anyOf = [
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
        ]
    )
    override fun setMyLocationEnabled(enabled: Boolean) {
        map.isMyLocationEnabled = enabled
    }

    /**
     * Sets the location source for the map.
     *
     * @param locationSource The location source to be set.
     */
    override fun setLocationSource(locationSource: LocationSource) {
        map.setLocationSource(object : com.google.android.gms.maps.LocationSource {
            override fun activate(p0: com.google.android.gms.maps.LocationSource.OnLocationChangedListener) {
                locationSource.activate(object : LocationSource.OnLocationChangedListener {
                    override fun onLocationChanged(location: Location) {
                        p0.onLocationChanged(location)
                    }
                })
            }

            override fun deactivate() {
                locationSource.deactivate()
            }
        })
    }

    /**
     * Gets the projection of the map.
     *
     * @return The projection object representing the map's projection.
     */
    override fun getProjection(): Projection {
        return map.projection.toProjection()
    }

    /**
     * Sets a listener to be notified when the camera position changes.
     *
     * @param listener The listener to be set.
     */
    override fun setOnCameraMoveListener(listener: CommonMap.OnCameraMoveListener) {
        map.setOnCameraMoveListener {
            listener.onCameraMove()
        }
    }

    /**
     * Sets a listener to be notified when a camera movement is canceled.
     *
     * @param listener The listener to be set.
     */
    override fun setOnCameraMoveCancelledListener(listener: CommonMap.OnCameraMoveCancelledListener) {
        map.setOnCameraMoveCanceledListener { listener.onCameraMoveCancelled() }
    }

    /**
     * Sets a listener to be notified when the map is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnMapClickListener(listener: CommonMap.OnMapClickListener) {
        map.setOnMapClickListener { listener.onMapClick(it.toLatLng()) }
    }

    /**
     * Sets a listener to be notified when the map is long-clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnMapLongClickListener(listener: CommonMap.OnMapLongClickListener) {
        map.setOnMapLongClickListener { listener.onMapLongClick(it.toLatLng()) }
    }

    /**
     * Sets a listener to be notified when a marker is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnMarkerClickListener(listener: CommonMap.OnMarkerClickListener) {
        map.setOnMarkerClickListener { listener.onMarkerClick(it.toMarker()) }
    }

    /**
     * Sets a listener to be notified when a marker is dragged.
     *
     * @param listener The listener to be set.
     */
    override fun setOnMarkerDragListener(listener: CommonMap.OnMarkerDragListener) {
        map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: com.google.android.gms.maps.model.Marker) {
                listener.onMarkerDrag(p0.toMarker())
            }

            override fun onMarkerDragEnd(p0: com.google.android.gms.maps.model.Marker) {
                listener.onMarkerDragEnd(p0.toMarker())
            }

            override fun onMarkerDragStart(p0: com.google.android.gms.maps.model.Marker) {
                listener.onMarkerDragStart(p0.toMarker())
            }
        })
    }

    /**
     * Sets a listener to be notified when an info window is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnInfoWindowClickListener(listener: CommonMap.OnInfoWindowClickListener) {
        map.setOnInfoWindowClickListener { listener.onInfoWindowClick(it.toMarker()) }
    }

    /**
     * Sets a listener to be notified when an info window is long-clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnInfoWindowLongClickListener(listener: CommonMap.OnInfoWindowLongClickListener) {
        map.setOnInfoWindowLongClickListener { listener.onInfoWindowLongClick(it.toMarker()) }
    }

    /**
     * Sets a listener to be notified when an info window is closed.
     *
     * @param listener The listener to be set.
     */
    override fun setOnInfoWindowCloseListener(listener: CommonMap.OnInfoWindowCloseListener) {
        map.setOnInfoWindowCloseListener { listener.onInfoWindowClose(it.toMarker()) }
    }

    /**
     * Sets an adapter for customizing the contents and layout of info windows.
     *
     * @param adapter The adapter to be set.
     */
    override fun setInfoWindowAdapter(adapter: CommonMap.InfoWindowAdapter) {
        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoContents(p0: com.google.android.gms.maps.model.Marker): View {
                return adapter.getInfoContents(p0.toMarker())
            }

            override fun getInfoWindow(p0: com.google.android.gms.maps.model.Marker): View {
                return adapter.getInfoWindow(p0.toMarker())
            }

        })
    }

    /**
     * Sets a listener to be notified when the "My Location" button is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnMyLocationClickListener(listener: CommonMap.OnMyLocationClickListener) {
        map.setOnMyLocationClickListener { listener.onMyLocationClick(it) }
    }

    /**
     * Sets a listener to be notified when the "My Location" button is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnMyLocationButtonClickListener(listener: CommonMap.OnMyLocationButtonClickListener) {
        map.setOnMyLocationButtonClickListener { listener.onMyLocationButtonClick() }
    }

    /**
     * Sets a callback listener to be invoked when the map has finished loading and is fully ready for use.
     *
     * @param callback The callback listener to be set.
     */
    override fun setOnMapLoadedCallback(callback: CommonMap.OnMapLoadedCallback) {
        map.setOnMapLoadedCallback { callback.onMapLoaded() }
    }

    /**
     * Sets a listener to be notified when a ground overlay is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnGroundOverlayClickListener(listener: CommonMap.OnGroundOverlayClickListener) {
        map.setOnGroundOverlayClickListener { listener.onGroundOverlayClick(it.toGroundOverlay()) }
    }

    /**
     * Sets a listener to be notified when a circle is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnCircleClickListener(listener: CommonMap.OnCircleClickListener) {
        map.setOnCircleClickListener { listener.onCircleClick(it.toCircle()) }
    }

    /**
     * Sets a listener to be notified when a polygon is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnPolygonClickListener(listener: CommonMap.OnPolygonClickListener) {
        map.setOnPolygonClickListener { listener.onPolygonClick(it.toPolygon()) }
    }

    /**
     * Sets a listener to be notified when a polyline is clicked.
     *
     * @param listener The listener to be set.
     */
    override fun setOnPolylineClickListener(listener: CommonMap.OnPolylineClickListener) {
        map.setOnPolylineClickListener { listener.onPolylineClick(it.toPolyline()) }
    }

    /**
     * Takes a snapshot of the map. The snapshot is captured asynchronously and the callback is invoked when the snapshot is ready.
     *
     * @param callback The callback listener to be invoked when the snapshot is ready.
     */
    override fun snapshot(callback: CommonMap.SnapshotReadyCallback) {
        map.snapshot { callback.onSnapshotReady(it) }
    }

    /**
     * Takes a snapshot of the map with a custom bitmap overlay. The snapshot is captured asynchronously and the callback is invoked when the snapshot is ready.
     *
     * @param callback The callback listener to be invoked when the snapshot is ready.
     * @param bitmap The bitmap overlay to be applied on the snapshot.
     */
    override fun snapshot(callback: CommonMap.SnapshotReadyCallback, bitmap: Bitmap) {
        map.snapshot({ p0 -> callback.onSnapshotReady(p0) }, bitmap)
    }

    /**
     * Sets padding of the map view from the edges of the map.
     *
     * @param left Padding from the left edge in pixels.
     * @param top Padding from the top edge in pixels.
     * @param right Padding from the right edge in pixels.
     * @param bottom Padding from the bottom edge in pixels.
     */
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        map.setPadding(left, top, right, bottom)
    }

    /**
     * Sets a content description for the map.
     *
     * @param description The content description to be set.
     */
    override fun setContentDescription(description: String) {
        map.setContentDescription(description)
    }

    /**
     * Sets the minimum zoom preference for the map.
     *
     * @param preference The minimum zoom preference to be set.
     */
    override fun setMinZoomPreference(preference: Float) {
        map.setMinZoomPreference(preference)
    }

    /**
     * Sets the maximum zoom preference for the map.
     *
     * @param preference The maximum zoom preference to be set.
     */
    override fun setMaxZoomPreference(preference: Float) {
        map.setMaxZoomPreference(preference)
    }

    /**
     * Resets the minimum and maximum zoom preferences for the map to their default values.
     */
    override fun resetMinMaxZoomPreference() {
        map.resetMinMaxZoomPreference()
    }

    /**
     * Sets the bounding area for the camera target.
     *
     * @param bounds The bounding area for the camera target.
     */
    override fun setLatLngBoundsForCameraTarget(bounds: LatLngBounds) {
        map.setLatLngBoundsForCameraTarget(bounds.toGmsLatLngBounds())
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
     * Sets the map style from a raw resource.
     *
     * @param resourceId The resource ID of the JSON file containing the map style.
     */
    override fun setMapStyleFromRawResource(resourceId: Int) {
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, resourceId))
    }
}