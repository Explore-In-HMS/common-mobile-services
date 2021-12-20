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

import com.google.android.gms.maps.model.Tile
import com.hms.lib.commonmobileservices.mapkit.Projection

fun com.huawei.hms.maps.model.Polygon.toPolygon() : Polygon = Polygon(this)
fun com.google.android.gms.maps.model.Polygon.toPolygon() : Polygon = Polygon(this)

fun com.huawei.hms.maps.model.Polyline.toPolyline() : Polyline = Polyline(this)
fun com.google.android.gms.maps.model.Polyline.toPolyline() : Polyline = Polyline(this)

fun HmsCircle.toCircle(): Circle = Circle(this)
fun GmsCircle.toCircle(): Circle = Circle(this)

fun HmsGroundOverlay.toGroundOverlay(): GroundOverlay = GroundOverlay(this)
fun GmsGroundOverlay.toGroundOverlay(): GroundOverlay = GroundOverlay(this)

fun HmsTileOverlay.toTileOverlay(): TileOverlay = TileOverlay(this)
fun GmsTileOverlay.toTileOverlay(): TileOverlay = TileOverlay(this)

fun HmsProjection.toProjection(): Projection = Projection(this)
fun GmsProjection.toProjection(): Projection = Projection(this)

fun PolygonOptions.toHMSPolygonOptions() : com.huawei.hms.maps.model.PolygonOptions {
    return com.huawei.hms.maps.model.PolygonOptions().addAll(baseLatLngs.map { it.toHMSLatLng() }).also { hmsOpts->
        strokeColor?.let {hmsOpts.strokeColor(it)}
        strokeWidth?.let {hmsOpts.strokeWidth(it)}
    }
}

fun LatLng.toHMSLatLng() : com.huawei.hms.maps.model.LatLng {
    return com.huawei.hms.maps.model.LatLng(lat,lng)
}

fun com.huawei.hms.maps.model.LatLng.toLatLng() : LatLng {
    return LatLng(latitude,longitude)
}

fun LatLng.toGMSLatLng() : com.google.android.gms.maps.model.LatLng{
    return com.google.android.gms.maps.model.LatLng(lat,lng)
}

fun com.google.android.gms.maps.model.LatLng.toLatLng() : LatLng {
    return LatLng(latitude,longitude)
}


fun PolygonOptions.toGMSPolygonOptions() : com.google.android.gms.maps.model.PolygonOptions{
    return com.google.android.gms.maps.model.PolygonOptions().
    addAll(baseLatLngs.map { it.toGMSLatLng() }).also {gmsOpts->
        strokeColor?.let {gmsOpts.strokeColor(it)}
        strokeWidth?.let {gmsOpts.strokeWidth(it)}
    }
}

fun PolylineOptions.toHMSPolylineOptions() : com.huawei.hms.maps.model.PolylineOptions {
    return com.huawei.hms.maps.model.PolylineOptions().addAll(baseLatLngs.map { it.toHMSLatLng() }).also { hmsOpts->
        Color?.let {hmsOpts.color(it)}
        Width?.let {hmsOpts.width(it)}
    }
}

fun PolylineOptions.toGMSPolylineOptions() : com.google.android.gms.maps.model.PolylineOptions{
    return com.google.android.gms.maps.model.PolylineOptions().
    addAll(baseLatLngs.map { it.toGMSLatLng() }).also {gmsOpts->
        Color?.let {gmsOpts.color(it)}
        Width?.let {gmsOpts.width(it)}
    }
}

fun CircleOptions.toHMSCircleOptions(): HmsCircleOptions{
    return HmsCircleOptions().also { hmsCircle ->
        radius?.let { hmsCircle.radius(it) }
        center?.let { hmsCircle.center(it.toHMSLatLng()) }
    }
}

fun CircleOptions.toGmsCircleOptions(): GmsCircleOptions{
    return GmsCircleOptions().also { gmsCircle ->
        radius?.let { gmsCircle.radius(it) }
        center?.let { gmsCircle.center(it.toGMSLatLng()) }
    }
}

fun GroundOverlayOptions.toHmsGroundOverlayOptions(): HmsGroundOverlayOptions{
    return HmsGroundOverlayOptions().also { options ->
        options.bearing(getBearing())
        options.anchor(getAnchorU(), getAnchorV())
        options.clickable(isClickable())
        getLocation()?.let { options.position(it.toHMSLatLng(), getWidth()) }
        getLocation()?.let { options.position(it.toHMSLatLng(), getWidth(), getHeight()) }
        getBounds()?.let { options.positionFromBounds(it.toHmsLatLngBounds()) }
        options.transparency(getTransparency())
        options.visible(isVisible())
        options.zIndex(getZIndex())
    }
}

fun GroundOverlayOptions.toGmsGroundOverlayOptions(): GmsGroundOverlayOptions{
    return GmsGroundOverlayOptions().also { options ->
        options.bearing(getBearing())
        options.anchor(getAnchorU(), getAnchorV())
        options.clickable(isClickable())
        getLocation()?.let { options.position(it.toGMSLatLng(), getWidth()) }
        getLocation()?.let { options.position(it.toGMSLatLng(), getWidth(), getHeight()) }
        getBounds()?.let { options.positionFromBounds(it.toGmsLatLngBounds()) }
        options.transparency(getTransparency())
        options.visible(isVisible())
        options.zIndex(getZIndex())
    }
}

fun LatLngBounds.toGmsLatLngBounds(): GmsLatLngBounds{
    return GmsLatLngBounds(
        southwest.toGMSLatLng(),
        northeast.toGMSLatLng()
    )
}

fun LatLngBounds.toHmsLatLngBounds(): HmsLatLngBounds{
    return HmsLatLngBounds(
        southwest.toHMSLatLng(),
        northeast.toHMSLatLng()
    )
}

fun TileOverlayOptions.toHmsTileOverlayOptions(): HmsTileOverlayOptions{
    return HmsTileOverlayOptions().also { options ->
        options.zIndex(zIndex)
        options.visible(isVisible)
        options.fadeIn(fadeIn)
        options.transparency(transparency)
    }
}

fun TileOverlayOptions.toGmsTileOverlayOptions(): GmsTileOverlayOptions{
    return GmsTileOverlayOptions().also { options ->
        options.zIndex(zIndex)
        options.visible(isVisible)
        options.fadeIn(fadeIn)
        options.transparency(transparency)
    }
}

fun HmsVisibleRegion.toVisibleRegion(): VisibleRegion{
    return VisibleRegion(
        nearLeft = nearLeft.toLatLng(),
        nearRight = nearRight.toLatLng(),
        farLeft = farLeft.toLatLng(),
        farRight = farRight.toLatLng(),
        bounds = latLngBounds.toLatLngBounds()
    )
}

fun GmsVisibleRegion.toVisibleRegion(): VisibleRegion{
    return VisibleRegion(
        nearLeft = nearLeft.toLatLng(),
        nearRight = nearRight.toLatLng(),
        farLeft = farLeft.toLatLng(),
        farRight = farRight.toLatLng(),
        bounds = latLngBounds.toLatLngBounds()
    )
}

fun GmsLatLngBounds.toLatLngBounds(): LatLngBounds{
    return LatLngBounds(
        southwest.toLatLng(),
        northeast.toLatLng()
    )
}

fun HmsLatLngBounds.toLatLngBounds(): LatLngBounds{
    return LatLngBounds(
        southwest.toLatLng(),
        northeast.toLatLng()
    )
}

