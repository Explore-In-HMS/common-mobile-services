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

fun HmsMarker.toMarker(): Marker = Marker(this)
fun GmsMarker.toMarker(): Marker = Marker(this)

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


var holes = ArrayList<List<LatLng?>>()


fun PolygonOptions.toHMSPolygonOptions() : com.huawei.hms.maps.model.PolygonOptions {
    return com.huawei.hms.maps.model.PolygonOptions().addAll(points.map { it.toHMSLatLng() }).also { hmsOpts->
        strokeColor?.let {hmsOpts.strokeColor(it)}
        strokeWidth?.let {hmsOpts.strokeWidth(it)}
        fillColor?.let { hmsOpts.fillColor(it) }
        zIndex?.let { hmsOpts.zIndex(it) }
        isVisible?.let { hmsOpts.visible(it) }
        isGeodesic?.let { hmsOpts.geodesic(it) }
        isClickable?.let { hmsOpts.clickable(it) }
        strokeJointType?.let { hmsOpts.strokeJointType(it) }
        holes?.let { hmsOpts.addHole(it.map { hole -> hole?.toHMSLatLng() }) }
    }
}

fun PolygonOptions.toGMSPolygonOptions() : com.google.android.gms.maps.model.PolygonOptions{
    return com.google.android.gms.maps.model.PolygonOptions().
    addAll(points.map { it.toGMSLatLng() }).also {gmsOpts->
        strokeColor?.let {gmsOpts.strokeColor(it)}
        strokeWidth?.let {gmsOpts.strokeWidth(it)}
        fillColor?.let { gmsOpts.fillColor(it) }
        zIndex?.let { gmsOpts.zIndex(it) }
        isVisible?.let { gmsOpts.visible(it) }
        isGeodesic?.let { gmsOpts.geodesic(it) }
        isClickable?.let { gmsOpts.clickable(it) }
        strokeJointType?.let { gmsOpts.strokeJointType(it) }
        holes?.let { gmsOpts.addHole(it.map { hole -> hole?.toGMSLatLng() }) }
    }
}

fun PolylineOptions.toHMSPolylineOptions() : com.huawei.hms.maps.model.PolylineOptions {
    return com.huawei.hms.maps.model.PolylineOptions().addAll(points.map { it.toHMSLatLng() }).also { hmsOpts->
        color?.let { hmsOpts.color(it) }
        width?.let { hmsOpts.width(it) }
        zIndex?.let { hmsOpts.zIndex(it) }
        isVisible?.let { hmsOpts.visible(it) }
        isGeodesic?.let { hmsOpts.geodesic(it) }
        isClickable?.let { hmsOpts.clickable(it) }
        jointType?.let { hmsOpts.jointType(it) }
    }
}

fun PolylineOptions.toGMSPolylineOptions() : com.google.android.gms.maps.model.PolylineOptions{
    return com.google.android.gms.maps.model.PolylineOptions().
    addAll(points.map { it.toGMSLatLng() }).also {gmsOpts->
        color?.let { gmsOpts.color(it) }
        width?.let { gmsOpts.width(it) }
        color?.let { gmsOpts.color(it) }
        width?.let { gmsOpts.width(it) }
        zIndex?.let { gmsOpts.zIndex(it) }
        isVisible?.let { gmsOpts.visible(it) }
        isGeodesic?.let { gmsOpts.geodesic(it) }
        isClickable?.let { gmsOpts.clickable(it) }
        jointType?.let { gmsOpts.jointType(it) }
    }
}

fun CircleOptions.toHMSCircleOptions(): HmsCircleOptions{
    return HmsCircleOptions().also { hmsCircle ->
        radius?.let { hmsCircle.radius(it) }
        center?.let { hmsCircle.center(it.toHMSLatLng()) }
        strokeWidth?.let { hmsCircle.strokeWidth(it) }
        strokeColor?.let { hmsCircle.strokeColor(it) }
        fillColor?.let { hmsCircle.fillColor(it) }
        zIndex?.let { hmsCircle.zIndex(it) }
        isVisible?.let { hmsCircle.visible(it) }
        isClickable?.let { hmsCircle.clickable(it) }
    }
}

fun CircleOptions.toGmsCircleOptions(): GmsCircleOptions{
    return GmsCircleOptions().also { gmsCircle ->
        radius?.let { gmsCircle.radius(it) }
        center?.let { gmsCircle.center(it.toGMSLatLng())}
        strokeWidth?.let { gmsCircle.strokeWidth(it) }
        strokeColor?.let { gmsCircle.strokeColor(it) }
        fillColor?.let { gmsCircle.fillColor(it) }
        zIndex?.let { gmsCircle.zIndex(it) }
        isVisible?.let { gmsCircle.visible(it) }
        isClickable?.let { gmsCircle.clickable(it) }
    }
}

fun GroundOverlayOptions.toHmsGroundOverlayOptions(): HmsGroundOverlayOptions{
    return HmsGroundOverlayOptions().also { hmsOverlay ->
        bearing?.let { hmsOverlay.bearing(it) }
        anchorU?.let { au -> anchorV?.let { av -> hmsOverlay.anchor(au, av) } }
        isClickable?.let { hmsOverlay.clickable(it) }

        location?.let { location ->

            if(width != null && height != null){
                hmsOverlay.position(location.toHMSLatLng(), width!!, height!!)
            }
            if(width != null && height == null){
                hmsOverlay.position(location.toHMSLatLng(), width!!)
            }

        }

        bounds?.let { hmsOverlay.positionFromBounds(it.toHmsLatLngBounds()) }
        transparency?.let { hmsOverlay.transparency(it) }
        isVisible?.let { hmsOverlay.visible(it) }
        zIndex?.let { hmsOverlay.zIndex(it) }
    }
}

fun GroundOverlayOptions.toGmsGroundOverlayOptions(): GmsGroundOverlayOptions{
    return GmsGroundOverlayOptions().also { gmsOverlay ->
        bearing?.let { gmsOverlay.bearing(it) }
        anchorU?.let { au -> anchorV?.let { av -> gmsOverlay.anchor(au, av) } }
        isClickable?.let { gmsOverlay.clickable(it) }

        location?.let { l ->
            width?.let { w ->
                gmsOverlay.position(l.toGMSLatLng(), w)
            }
        }
        location?.let { l ->
            width?.let { w ->
                height?.let { h ->
                    gmsOverlay.position(l.toGMSLatLng(), w, h)
                }
            }
        }
        bounds?.let { gmsOverlay.positionFromBounds(it.toGmsLatLngBounds()) }
        transparency?.let { gmsOverlay.transparency(it) }
        isVisible?.let { gmsOverlay.visible(it) }
        zIndex?.let { gmsOverlay.zIndex(it) }
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
    return HmsTileOverlayOptions().also { hmsTile ->
        zIndex?.let { hmsTile.zIndex(it) }
        isVisible?.let { hmsTile.visible(it) }
        fadeIn?.let { hmsTile.fadeIn(it) }
        transparency?.let { hmsTile.transparency(it) }
    }
}

fun TileOverlayOptions.toGmsTileOverlayOptions(): GmsTileOverlayOptions{
    return GmsTileOverlayOptions().also { gmsTile ->
        zIndex?.let { gmsTile.zIndex(it) }
        isVisible?.let { gmsTile.visible(it) }
        fadeIn?.let { gmsTile.fadeIn(it) }
        transparency?.let { gmsTile.transparency(it) }
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

