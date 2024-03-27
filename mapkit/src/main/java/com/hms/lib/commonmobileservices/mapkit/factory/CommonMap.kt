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

import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.view.View
import com.hms.lib.commonmobileservices.mapkit.LocationSource
import com.hms.lib.commonmobileservices.mapkit.Projection
import com.hms.lib.commonmobileservices.mapkit.model.*

/**
 * The CommonMap interface represents a generic map functionality commonly found in mapping libraries.
 * It extends the UISettings interface to provide methods for controlling the user interface settings of the map.
 */
interface CommonMap : UISettings {
    /**
     * Companion object containing constants for different map types.
     */
    /**
     * Companion object containing constants representing different types of map.
     */
    companion object {
        /**
         * Constant representing a map with no specific type.
         */
        const val MAP_TYPE_NONE = 0

        /**
         * Constant representing a normal map type.
         */
        const val MAP_TYPE_NORMAL = 1

        /**
         * Constant representing a satellite map type.
         */
        const val MAP_TYPE_SATELLITE = 2

        /**
         * Constant representing a terrain map type.
         */
        const val MAP_TYPE_TERRAIN = 3

        /**
         * Constant representing a hybrid map type combining satellite and normal views.
         */
        const val MAP_TYPE_HYBRID = 4
    }

    /**
     * Retrieves the View object representing the map view.
     *
     * @return The View object representing the map view.
     */
    fun getMapView(): View

    /**
     * Called when the map is created. This method allows initializing the map with optional initial state.
     *
     * @param bundle The bundle containing initial map state, or null if no initial state is provided.
     */

    fun onCreate(bundle: Bundle?)

    /**
     * Asynchronously initializes the map and invokes the provided callback when the map is ready.
     *
     * @param onMapReadyListener Callback to be invoked when the map is ready. It receives the ready map instance.
     */
    fun getMapAsync(onMapReadyListener: (map: CommonMap) -> Unit)

    /**
     * Adds a marker to the map with specified options.
     *
     * @param title The title of the marker, or null if no title is provided.
     * @param snippet The snippet of the marker, or null if no snippet is provided.
     * @param latitude The latitude of the marker position.
     * @param longitude The longitude of the marker position.
     * @param iconBitmap The bitmap representing the marker icon, or null if default icon is used.
     * @param anchor The anchor point of the marker icon, or null if default anchor is used.
     * @return The Marker object representing the added marker on the map.
     */
    fun addMarker(
        title: String? = null,
        snippet: String? = null,
        latitude: Double,
        longitude: Double,
        iconBitmap: Bitmap? = null,
        anchor: Pair<Float, Float>? = null
    ): Marker

    /**
     * Adds a polygon to the map with the specified options.
     *
     * @param polygonOptions The options for configuring the polygon appearance and behavior.
     * @return The Polygon object representing the added polygon on the map.
     */
    fun addPolygon(polygonOptions: PolygonOptions): Polygon

    /**
     * Adds a polyline to the map with the specified options.
     *
     * @param polylineOptions The options for configuring the polyline appearance and behavior.
     * @return The Polyline object representing the added polyline on the map.
     */
    fun addPolyline(polylineOptions: PolylineOptions): Polyline

    /**
     * Sets a callback to be invoked when an info window attached to a marker is clicked.
     *
     * @param markerClickCallback The callback function to handle info window clicks. It receives the title,
     * snippet, and LatLng of the marker whose info window was clicked.
     */
    fun setOnInfoWindowClickListener(
        markerClickCallback: (
            markerTitle: String?,
            markerSnippet: String?,
            latLng: LatLng
        ) -> Unit
    )

    /**
     * Sets a callback to be invoked when the map is clicked.
     *
     * @param onClick The callback function to handle map clicks. It receives the LatLng coordinates of the clicked point.
     */
    fun setOnMapClickListener(onClick: (latLng: LatLng) -> Unit)

    /**
     * Moves the camera to the specified position and zoom level immediately.
     *
     * @param latitude The latitude of the target position.
     * @param longitude The longitude of the target position.
     * @param zoomRatio The zoom level to move the camera to.
     */
    fun moveCamera(latitude: Double, longitude: Double, zoomRatio: Float)

    /**
     * Animates the camera to the specified position and zoom level.
     *
     * @param latitude The latitude of the target position.
     * @param longitude The longitude of the target position.
     * @param zoomRatio The zoom level to animate the camera to.
     */
    fun animateCamera(latitude: Double, longitude: Double, zoomRatio: Float)

    /**
     * Enables or disables the display of the user's location on the map.
     *
     * @param myLocationEnabled True to enable showing the user's location, false otherwise.
     * @return True if the operation was successful, false otherwise.
     */
    fun setMyLocationEnabled(myLocationEnabled: Boolean?): Boolean

    /**
     * Clears all map elements from the map.
     */
    fun clear()

    /**
     * Saves the current state of the map to the provided bundle.
     *
     * @param bundle The bundle to save the map state into.
     */
    fun onSaveInstanceState(bundle: Bundle)

    /**
     * Called when the activity is starting.
     */
    fun onStart()

    /**
     * Called when the activity will start interacting with the user.
     */
    fun onResume()

    /**
     * Called when the activity is going into the background, but has not yet been killed.
     */
    fun onPause()

    /**
     * Called when the activity is no longer visible to the user.
     */
    fun onStop()

    /**
     * Called before the activity is destroyed.
     */
    fun onDestroy()

    /**
     * Called when the operating system has determined that it is a good time for a process to trim unneeded memory.
     */
    fun onLowMemory()

    /**
     * Calculates the distance between two points on the map.
     *
     * @param p1 The first LatLng representing the starting point.
     * @param p2 The second LatLng representing the destination point.
     * @return The distance between the two points in meters.
     */
    fun calculateDistanceBetweenPoints(p1: LatLng, p2: LatLng): Double

    /**
     * Retrieves the current position of the camera.
     *
     * @return The CameraPosition object representing the current camera position.
     */
    fun getCameraPosition(): CameraPosition

    /**
     * Retrieves the maximum zoom level supported by the map.
     *
     * @return The maximum zoom level.
     */
    fun getMaxZoomLevel(): Float

    /**
     * Retrieves the minimum zoom level supported by the map.
     *
     * @return The minimum zoom level.
     */
    fun getMinZoomLevel(): Float

    /**
     * Stops any ongoing camera animation.
     */
    fun stopAnimation()

    /**
     * Adds a circle overlay to the map with the specified options.
     *
     * @param circleOptions The options for configuring the circle overlay appearance and behavior.
     * @return The Circle object representing the added circle overlay on the map.
     */
    fun addCircle(circleOptions: CircleOptions): Circle

    /**
     * Adds a ground overlay to the map with the specified options.
     *
     * @param groundOverlayOptions The options for configuring the ground overlay appearance and behavior.
     * @return The GroundOverlay object representing the added ground overlay on the map.
     */
    fun addGroundOverlay(groundOverlayOptions: GroundOverlayOptions): GroundOverlay

    /**
     * Adds a tile overlay to the map with the specified options.
     *
     * @param tileOverlayOptions The options for configuring the tile overlay appearance and behavior.
     * @return The TileOverlay object representing the added tile overlay on the map.
     */
    fun addTileOverlay(tileOverlayOptions: TileOverlayOptions): TileOverlay

    /**
     * Sets the type of map to be displayed.
     *
     * @param type The type of map. Use one of the constants defined in the CommonMap.Companion for map types.
     */
    fun setMapType(type: Int)

    /**
     * Retrieves the current type of map being displayed.
     *
     * @return The type of map. It will be one of the constants defined in the CommonMap.Companion for map types.
     */
    fun getMapType(): Int

    /**
     * Checks whether traffic information is enabled on the map.
     *
     * @return True if traffic information is enabled, false otherwise.
     */
    fun isTrafficEnabled(): Boolean

    /**
     * Enables or disables the display of traffic information on the map.
     *
     * @param enabled True to enable traffic information, false to disable.
     */
    fun setTrafficEnabled(enabled: Boolean)

    /**
     * Checks whether indoor maps are enabled on the map.
     *
     * @return True if indoor maps are enabled, false otherwise.
     */
    fun isIndoorEnabled(): Boolean

    /**
     * Enables or disables the display of indoor maps on the map.
     *
     * @param enabled True to enable indoor maps, false to disable.
     */
    fun setIndoorEnabled(enabled: Boolean)

    /**
     * Checks whether 3D building models are enabled on the map.
     *
     * @return True if 3D building models are enabled, false otherwise.
     */
    fun isBuildingEnabled(): Boolean

    /**
     * Enables or disables the display of 3D building models on the map.
     *
     * @param enabled True to enable 3D building models, false to disable.
     */
    fun setBuildingEnabled(enabled: Boolean)

    /**
     * Checks whether the display of the user's location on the map is enabled.
     *
     * @return True if showing the user's location is enabled, false otherwise.
     */
    fun isMyLocationEnabled(): Boolean

    /**
     * Enables or disables the display of the user's location on the map.
     *
     * @param enabled True to enable showing the user's location, false otherwise.
     */
    fun setMyLocationEnabled(enabled: Boolean)

    /**
     * Sets the location source for the map.
     *
     * @param locationSource The location source to be set.
     */
    fun setLocationSource(locationSource: LocationSource)

    /**
     * Retrieves the Projection object for converting between LatLng coordinates and screen pixel coordinates.
     *
     * @return The Projection object for the map.
     */
    fun getProjection(): Projection

    /**
     * Sets a listener to be notified when the camera starts moving.
     *
     * @param listener The listener to be notified.
     */
    fun setOnCameraMoveListener(listener: OnCameraMoveListener)

    /**
     * Sets a listener to be notified when the camera starts moving.
     *
     * @param listener The listener to be notified.
     */
    fun setOnCameraMoveStartedListener(listener: OnCameraMoveStartedListener)

    /**
     * Sets a listener to be notified when the camera movement is cancelled.
     *
     * @param listener The listener to be notified.
     */
    fun setOnCameraMoveCancelledListener(listener: OnCameraMoveCancelledListener)

    /**
     * Sets a listener to be notified when the camera movement has ended.
     *
     * @param listener The listener to be notified.
     */
    fun setOnCameraIdleListener(listener: OnCameraIdleListener)

    /**
     * Sets a listener to be notified when the map is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnMapClickListener(listener: OnMapClickListener)

    /**
     * Sets a listener to be notified when the map is long clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnMapLongClickListener(listener: OnMapLongClickListener)

    /**
     * Sets a listener to be notified when a marker is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnMarkerClickListener(listener: OnMarkerClickListener)

    /**
     * Sets a listener to be notified when a marker is dragged.
     *
     * @param listener The listener to be notified.
     */
    fun setOnMarkerDragListener(listener: OnMarkerDragListener)

    /**
     * Sets a listener to be notified when an info window attached to a marker is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnInfoWindowClickListener(listener: OnInfoWindowClickListener)

    /**
     * Sets a listener to be notified when an info window attached to a marker is long clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnInfoWindowLongClickListener(listener: OnInfoWindowLongClickListener)

    /**
     * Sets a listener to be notified when an info window attached to a marker is closed.
     *
     * @param listener The listener to be notified.
     */
    fun setOnInfoWindowCloseListener(listener: OnInfoWindowCloseListener)

    /**
     * Sets a custom adapter for providing info window content for markers.
     *
     * @param adapter The adapter to be used for info window content customization.
     */
    fun setInfoWindowAdapter(adapter: InfoWindowAdapter)

    /**
     * Sets a listener to be notified when the "My Location" button is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnMyLocationClickListener(listener: OnMyLocationClickListener)

    /**
     * Sets a listener to be notified when the "My Location" button is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnMyLocationButtonClickListener(listener: OnMyLocationButtonClickListener)

    /**
     * Sets a callback to be invoked when the map has finished loading.
     *
     * @param callback The callback to be invoked.
     */
    fun setOnMapLoadedCallback(callback: OnMapLoadedCallback)

    /**
     * Sets a listener to be notified when a ground overlay is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnGroundOverlayClickListener(listener: OnGroundOverlayClickListener)

    /**
     * Sets a listener to be notified when a circle overlay is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnCircleClickListener(listener: OnCircleClickListener)

    /**
     * Sets a listener to be notified when a polygon overlay is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnPolygonClickListener(listener: OnPolygonClickListener)

    /**
     * Sets a listener to be notified when a polyline overlay is clicked.
     *
     * @param listener The listener to be notified.
     */
    fun setOnPolylineClickListener(listener: OnPolylineClickListener)

    /**
     * Takes a snapshot of the map asynchronously. The snapshot will be provided through the specified callback.
     *
     * @param callback The callback to receive the snapshot.
     */
    fun snapshot(callback: SnapshotReadyCallback)

    /**
     * Takes a snapshot of the map synchronously using the provided bitmap. The snapshot will be provided through the specified callback.
     *
     * @param callback The callback to receive the snapshot.
     * @param bitmap The bitmap to use for the snapshot.
     */
    fun snapshot(callback: SnapshotReadyCallback, bitmap: Bitmap)

    /**
     * Sets padding for the map. Padding is space around the edges of the map view.
     *
     * @param var1 The left padding, in pixels.
     * @param var2 The top padding, in pixels.
     * @param var3 The right padding, in pixels.
     * @param var4 The bottom padding, in pixels.
     */
    fun setPadding(var1: Int, var2: Int, var3: Int, var4: Int)

    /**
     * Sets a content description for the map view.
     *
     * @param description The content description to be set.
     */
    fun setContentDescription(description: String)

    /**
     * Sets the minimum zoom level allowed for the map.
     *
     * @param preference The minimum zoom level.
     */
    fun setMinZoomPreference(preference: Float)

    /**
     * Sets the maximum zoom level allowed for the map.
     *
     * @param preference The maximum zoom level.
     */
    fun setMaxZoomPreference(preference: Float)

    /**
     * Resets the minimum and maximum zoom preferences for the map to their default values.
     */
    fun resetMinMaxZoomPreference()

    /**
     * Sets the bounding box for the camera target. This determines the region that will be centered on the map.
     *
     * @param bounds The bounding box to set for the camera target.
     */
    fun setLatLngBoundsForCameraTarget(bounds: LatLngBounds)

    /**
     * Sets a custom map style from a JSON string.
     *
     * @param json The JSON string representing the custom map style.
     */
    fun setMapStyle(json: String)

    /**
     * Sets a custom map style from a raw resource ID.
     *
     * @param resourceId The resource ID of the raw JSON file containing the custom map style.
     */
    fun setMapStyleFromRawResource(resourceId: Int)

    /**
     * Interface definition for a callback to be invoked when the camera position changes.
     */
    interface OnCameraMoveListener {
        /**
         * Called when the camera position changes.
         */
        fun onCameraMove()
    }

    /**
     * Abstract class defining a callback to be invoked when the camera movement starts.
     */
    abstract class OnCameraMoveStartedListener {
        companion object {
            /** Indicates that the camera movement was caused by a user gesture. */
            const val REASON_GESTURE: Int = 1

            /** Indicates that the camera movement was caused by an API animation. */
            const val REASON_API_ANIMATION: Int = 2

            /** Indicates that the camera movement was caused by a developer animation. */
            const val REASON_DEVELOPER_ANIMATION: Int = 3
        }

        /**
         * Called when the camera movement starts.
         *
         * @param reason The reason for the camera movement. Use one of the REASON_ constants defined in this companion object.
         */
        abstract fun onCameraMoveStarted(reason: Int)
    }

    /**
     * Interface definition for a callback to be invoked when the camera movement is cancelled.
     */
    interface OnCameraMoveCancelledListener {
        /**
         * Called when the camera movement is cancelled.
         */
        fun onCameraMoveCancelled()
    }

    /**
     * Interface definition for a callback to be invoked when the camera movement has ended and the camera is now idle.
     */
    interface OnCameraIdleListener {
        /**
         * Called when the camera movement has ended and the camera is now idle.
         */
        fun onCameraIdle()
    }

    /**
     * Interface definition for a callback to be invoked when the map is clicked.
     */
    interface OnMapClickListener {
        /**
         * Called when the map is clicked.
         *
         * @param latLng The geographical coordinates of the clicked point.
         */
        fun onMapClick(latLng: LatLng)
    }

    /**
     * Interface definition for a callback to be invoked when the map is long clicked.
     */
    interface OnMapLongClickListener {
        /**
         * Called when the map is long clicked.
         *
         * @param latLng The geographical coordinates of the long clicked point.
         */
        fun onMapLongClick(latLng: LatLng)
    }

    /**
     * Interface definition for a callback to be invoked when a marker is clicked.
     */
    interface OnMarkerClickListener {
        /**
         * Called when a marker is clicked.
         *
         * @param marker The marker that was clicked.
         * @return True if the listener has consumed the event, false otherwise.
         */
        fun onMarkerClick(marker: Marker?): Boolean
    }

    /**
     * Interface definition for callbacks to be invoked when a marker is dragged.
     */
    interface OnMarkerDragListener {
        /**
         * Called when a marker drag is started.
         *
         * @param marker The marker being dragged.
         */
        fun onMarkerDragStart(marker: Marker?)

        /**
         * Called repeatedly while a marker is being dragged.
         *
         * @param marker The marker being dragged.
         */
        fun onMarkerDrag(marker: Marker?)

        /**
         * Called when a marker drag ends.
         *
         * @param marker The marker being dragged.
         */
        fun onMarkerDragEnd(marker: Marker?)
    }

    /**
     * Interface definition for a callback to be invoked when an info window attached to a marker is clicked.
     */
    interface OnInfoWindowClickListener {
        /**
         * Called when an info window attached to a marker is clicked.
         *
         * @param marker The marker whose info window was clicked.
         */
        fun onInfoWindowClick(marker: Marker?)
    }

    /**
     * Interface definition for a callback to be invoked when an info window attached to a marker is long clicked.
     */
    interface OnInfoWindowLongClickListener {
        /**
         * Called when an info window attached to a marker is long clicked.
         *
         * @param marker The marker whose info window was long clicked.
         */
        fun onInfoWindowLongClick(marker: Marker?)
    }

    /**
     * Interface definition for a callback to be invoked when an info window attached to a marker is closed.
     */
    interface OnInfoWindowCloseListener {
        /**
         * Called when an info window attached to a marker is closed.
         *
         * @param marker The marker whose info window was closed.
         */
        fun onInfoWindowClose(marker: Marker?)
    }

    /**
     * Interface definition for providing custom views for marker info windows.
     */
    interface InfoWindowAdapter {
        /**
         * Provides a custom contents view for the info window of the given marker.
         *
         * @param marker The marker for which the info window contents are being requested.
         * @return The custom view for the info window contents.
         */
        fun getInfoContents(marker: Marker?): View

        /**
         * Provides a custom view for the entire info window of the given marker.
         *
         * @param marker The marker for which the info window is being requested.
         * @return The custom view for the entire info window.
         */
        fun getInfoWindow(marker: Marker?): View
    }

    /**
     * Interface definition for a callback to be invoked when the "My Location" button is clicked.
     */
    interface OnMyLocationClickListener {
        /**
         * Called when the "My Location" button is clicked.
         *
         * @param location The current location of the user.
         */
        fun onMyLocationClick(location: Location)
    }

    /**
     * Interface definition for a callback to be invoked when the "My Location" button is clicked.
     */
    interface OnMyLocationButtonClickListener {
        /**
         * Called when the "My Location" button is clicked.
         *
         * @return True if the listener has consumed the event, false otherwise.
         */
        fun onMyLocationButtonClick(): Boolean
    }

    /**
     * Interface definition for a callback to be invoked when the map has finished loading.
     */
    interface OnMapLoadedCallback {
        /**
         * Called when the map has finished loading.
         */
        fun onMapLoaded()
    }

    /**
     * Interface definition for a callback to be invoked when a ground overlay is clicked.
     */
    interface OnGroundOverlayClickListener {
        /**
         * Called when a ground overlay is clicked.
         *
         * @param groundOverlay The ground overlay that was clicked.
         */
        fun onGroundOverlayClick(groundOverlay: GroundOverlay)
    }

    /**
     * Interface definition for a callback to be invoked when a circle overlay is clicked.
     */
    interface OnCircleClickListener {
        /**
         * Called when a circle overlay is clicked.
         *
         * @param circle The circle overlay that was clicked.
         */
        fun onCircleClick(circle: Circle)
    }

    /**
     * Interface definition for a callback to be invoked when a polygon overlay is clicked.
     */
    interface OnPolygonClickListener {
        /**
         * Called when a polygon overlay is clicked.
         *
         * @param polygon The polygon overlay that was clicked.
         */
        fun onPolygonClick(polygon: Polygon)
    }

    /**
     * Interface definition for a callback to be invoked when a polyline overlay is clicked.
     */
    interface OnPolylineClickListener {
        /**
         * Called when a polyline overlay is clicked.
         *
         * @param polyline The polyline overlay that was clicked.
         */
        fun onPolylineClick(polyline: Polyline)
    }

    /**
     * Interface definition for a callback to be invoked when a snapshot of the map is ready.
     */
    interface SnapshotReadyCallback {
        /**
         * Called when a snapshot of the map is ready.
         *
         * @param bitmap The bitmap containing the snapshot of the map. It may be null if snapshot fails.
         */
        fun onSnapshotReady(bitmap: Bitmap?)
    }
}