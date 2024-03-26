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

/**
 * Converts a [com.huawei.hms.maps.model.Polygon] object to a [Polygon] object.
 *
 * @receiver The [com.huawei.hms.maps.model.Polygon] object to convert.
 * @return The converted [Polygon] object.
 */
fun com.huawei.hms.maps.model.Polygon.toPolygon(): Polygon = Polygon(this)

/**
 * Converts a [com.google.android.gms.maps.model.Polygon] object to a [Polygon] object.
 *
 * @receiver The [com.google.android.gms.maps.model.Polygon] object to convert.
 * @return The converted [Polygon] object.
 */
fun com.google.android.gms.maps.model.Polygon.toPolygon(): Polygon = Polygon(this)

/**
 * Converts a [com.huawei.hms.maps.model.Polyline] object to a [Polyline] object.
 *
 * @receiver The [com.huawei.hms.maps.model.Polyline] object to convert.
 * @return The converted [Polyline] object.
 */
fun com.huawei.hms.maps.model.Polyline.toPolyline(): Polyline = Polyline(this)

/**
 * Converts a [com.google.android.gms.maps.model.Polyline] object to a [Polyline] object.
 *
 * @receiver The [com.google.android.gms.maps.model.Polyline] object to convert.
 * @return The converted [Polyline] object.
 */
fun com.google.android.gms.maps.model.Polyline.toPolyline(): Polyline = Polyline(this)

/**
 * Converts an [HmsCircle] object to a [Circle] object.
 *
 * @receiver The [HmsCircle] object to convert.
 * @return The converted [Circle] object.
 */
fun HmsCircle.toCircle(): Circle = Circle(this)

/**
 * Converts a [GmsCircle] object to a [Circle] object.
 *
 * @receiver The [GmsCircle] object to convert.
 * @return The converted [Circle] object.
 */
fun GmsCircle.toCircle(): Circle = Circle(this)

/**
 * Converts an [HmsGroundOverlay] object to a [GroundOverlay] object.
 *
 * @receiver The [HmsGroundOverlay] object to convert.
 * @return The converted [GroundOverlay] object.
 */
fun HmsGroundOverlay.toGroundOverlay(): GroundOverlay = GroundOverlay(this)

/**
 * Converts a [GmsGroundOverlay] object to a [GroundOverlay] object.
 *
 * @receiver The [GmsGroundOverlay] object to convert.
 * @return The converted [GroundOverlay] object.
 */
fun GmsGroundOverlay.toGroundOverlay(): GroundOverlay = GroundOverlay(this)

/**
 * Converts an [HmsTileOverlay] object to a [TileOverlay] object.
 *
 * @receiver The [HmsTileOverlay] object to convert.
 * @return The converted [TileOverlay] object.
 */
fun HmsTileOverlay.toTileOverlay(): TileOverlay = TileOverlay(this)

/**
 * Converts a [GmsTileOverlay] object to a [TileOverlay] object.
 *
 * @receiver The [GmsTileOverlay] object to convert.
 * @return The converted [TileOverlay] object.
 */
fun GmsTileOverlay.toTileOverlay(): TileOverlay = TileOverlay(this)

/**
 * Converts an [HmsProjection] object to a [Projection] object.
 *
 * @receiver The [HmsProjection] object to convert.
 * @return The converted [Projection] object.
 */
fun HmsProjection.toProjection(): Projection = Projection(this)

/**
 * Converts a [GmsProjection] object to a [Projection] object.
 *
 * @receiver The [GmsProjection] object to convert.
 * @return The converted [Projection] object.
 */
fun GmsProjection.toProjection(): Projection = Projection(this)

/**
 * Converts an [HmsMarker] object to a [Marker] object.
 *
 * @receiver The [HmsMarker] object to convert.
 * @return The converted [Marker] object.
 */
fun HmsMarker.toMarker(): Marker = Marker(this)

/**
 * Converts a [GmsMarker] object to a [Marker] object.
 *
 * @receiver The [GmsMarker] object to convert.
 * @return The converted [Marker] object.
 */
fun GmsMarker.toMarker(): Marker = Marker(this)

/**
 * Converts a [LatLng] object to a Huawei Maps [com.huawei.hms.maps.model.LatLng] object.
 *
 * @receiver The [LatLng] object to convert.
 * @return The converted Huawei Maps [com.huawei.hms.maps.model.LatLng] object.
 */
fun LatLng.toHMSLatLng(): com.huawei.hms.maps.model.LatLng {
    return com.huawei.hms.maps.model.LatLng(lat, lng)
}

/**
 * Converts a Huawei Maps [com.huawei.hms.maps.model.LatLng] object to a [LatLng] object.
 *
 * @receiver The Huawei Maps [com.huawei.hms.maps.model.LatLng] object to convert.
 * @return The converted [LatLng] object.
 */
fun com.huawei.hms.maps.model.LatLng.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}

/**
 * Converts a [LatLng] object to a Google Maps [com.google.android.gms.maps.model.LatLng] object.
 *
 * @receiver The [LatLng] object to convert.
 * @return The converted Google Maps [com.google.android.gms.maps.model.LatLng] object.
 */
fun LatLng.toGMSLatLng(): com.google.android.gms.maps.model.LatLng {
    return com.google.android.gms.maps.model.LatLng(lat, lng)
}

/**
 * Converts a Google Maps [com.google.android.gms.maps.model.LatLng] object to a [LatLng] object.
 *
 * @receiver The Google Maps [com.google.android.gms.maps.model.LatLng] object to convert.
 * @return The converted [LatLng] object.
 */
fun com.google.android.gms.maps.model.LatLng.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}

/**
 * List to hold the holes of a polygon.
 */
var holes = ArrayList<List<LatLng?>>()

/**
 * Converts a [PolygonOptions] object to Huawei Maps [com.huawei.hms.maps.model.PolygonOptions] object.
 *
 * @receiver The [PolygonOptions] object to convert.
 * @return The converted Huawei Maps [com.huawei.hms.maps.model.PolygonOptions] object.
 */
fun PolygonOptions.toHMSPolygonOptions(): com.huawei.hms.maps.model.PolygonOptions {
    return com.huawei.hms.maps.model.PolygonOptions().addAll(points.map { it.toHMSLatLng() })
        .also { hmsOpts ->
            strokeColor?.let { hmsOpts.strokeColor(it) }
            strokeWidth?.let { hmsOpts.strokeWidth(it) }
            fillColor?.let { hmsOpts.fillColor(it) }
            zIndex?.let { hmsOpts.zIndex(it) }
            isVisible?.let { hmsOpts.visible(it) }
            isGeodesic?.let { hmsOpts.geodesic(it) }
            isClickable?.let { hmsOpts.clickable(it) }
            strokeJointType?.let { hmsOpts.strokeJointType(it) }
            holes?.let { hmsOpts.addHole(it.map { hole -> hole?.toHMSLatLng() }) }
        }
}

/**
 * Converts a [PolygonOptions] object to Google Maps [com.google.android.gms.maps.model.PolygonOptions] object.
 *
 * @receiver The [PolygonOptions] object to convert.
 * @return The converted Google Maps [com.google.android.gms.maps.model.PolygonOptions] object.
 */
fun PolygonOptions.toGMSPolygonOptions(): com.google.android.gms.maps.model.PolygonOptions {
    return com.google.android.gms.maps.model.PolygonOptions()
        .addAll(points.map { it.toGMSLatLng() }).also { gmsOpts ->
        strokeColor?.let { gmsOpts.strokeColor(it) }
        strokeWidth?.let { gmsOpts.strokeWidth(it) }
        fillColor?.let { gmsOpts.fillColor(it) }
        zIndex?.let { gmsOpts.zIndex(it) }
        isVisible?.let { gmsOpts.visible(it) }
        isGeodesic?.let { gmsOpts.geodesic(it) }
        isClickable?.let { gmsOpts.clickable(it) }
        strokeJointType?.let { gmsOpts.strokeJointType(it) }
        holes?.let { gmsOpts.addHole(it.map { hole -> hole?.toGMSLatLng() }) }
    }
}

/**
 * Converts a [PolylineOptions] object to Huawei Maps [com.huawei.hms.maps.model.PolylineOptions] object.
 *
 * @receiver The [PolylineOptions] object to convert.
 * @return The converted Huawei Maps [com.huawei.hms.maps.model.PolylineOptions] object.
 */
fun PolylineOptions.toHMSPolylineOptions(): com.huawei.hms.maps.model.PolylineOptions {
    return com.huawei.hms.maps.model.PolylineOptions().addAll(points.map { it.toHMSLatLng() })
        .also { hmsOpts ->
            color?.let { hmsOpts.color(it) }
            width?.let { hmsOpts.width(it) }
            zIndex?.let { hmsOpts.zIndex(it) }
            isVisible?.let { hmsOpts.visible(it) }
            isGeodesic?.let { hmsOpts.geodesic(it) }
            isClickable?.let { hmsOpts.clickable(it) }
            jointType?.let { hmsOpts.jointType(it) }
        }
}

/**
 * Converts a [PolylineOptions] object to Google Maps [com.google.android.gms.maps.model.PolylineOptions] object.
 *
 * @receiver The [PolylineOptions] object to convert.
 * @return The converted Google Maps [com.google.android.gms.maps.model.PolylineOptions] object.
 */
fun PolylineOptions.toGMSPolylineOptions(): com.google.android.gms.maps.model.PolylineOptions {
    return com.google.android.gms.maps.model.PolylineOptions()
        .addAll(points.map { it.toGMSLatLng() }).also { gmsOpts ->
        color?.let { gmsOpts.color(it) }
        width?.let { gmsOpts.width(it) }
        zIndex?.let { gmsOpts.zIndex(it) }
        isVisible?.let { gmsOpts.visible(it) }
        isGeodesic?.let { gmsOpts.geodesic(it) }
        isClickable?.let { gmsOpts.clickable(it) }
        jointType?.let { gmsOpts.jointType(it) }
    }
}

/**
 * Converts a [CircleOptions] object to Huawei Maps [HmsCircleOptions] object.
 *
 * @receiver The [CircleOptions] object to convert.
 * @return The converted Huawei Maps [HmsCircleOptions] object.
 */
fun CircleOptions.toHMSCircleOptions(): HmsCircleOptions {
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

/**
 * Converts a [CircleOptions] object to Google Maps [GmsCircleOptions] object.
 *
 * @receiver The [CircleOptions] object to convert.
 * @return The converted Google Maps [GmsCircleOptions] object.
 */
fun CircleOptions.toGmsCircleOptions(): GmsCircleOptions {
    return GmsCircleOptions().also { gmsCircle ->
        radius?.let { gmsCircle.radius(it) }
        center?.let { gmsCircle.center(it.toGMSLatLng()) }
        strokeWidth?.let { gmsCircle.strokeWidth(it) }
        strokeColor?.let { gmsCircle.strokeColor(it) }
        fillColor?.let { gmsCircle.fillColor(it) }
        zIndex?.let { gmsCircle.zIndex(it) }
        isVisible?.let { gmsCircle.visible(it) }
        isClickable?.let { gmsCircle.clickable(it) }
    }
}

/**
 * Converts a [GroundOverlayOptions] object to Huawei Maps [HmsGroundOverlayOptions] object.
 *
 * @receiver The [GroundOverlayOptions] object to convert.
 * @return The converted Huawei Maps [HmsGroundOverlayOptions] object.
 */
fun GroundOverlayOptions.toHmsGroundOverlayOptions(): HmsGroundOverlayOptions {
    return HmsGroundOverlayOptions().also { hmsOverlay ->
        bearing?.let { hmsOverlay.bearing(it) }
        anchorU?.let { au -> anchorV?.let { av -> hmsOverlay.anchor(au, av) } }
        isClickable?.let { hmsOverlay.clickable(it) }

        location?.let { location ->

            if (width != null && height != null) {
                hmsOverlay.position(location.toHMSLatLng(), width!!, height!!)
            }
            if (width != null && height == null) {
                hmsOverlay.position(location.toHMSLatLng(), width!!)
            }

        }

        bounds?.let { hmsOverlay.positionFromBounds(it.toHmsLatLngBounds()) }
        transparency?.let { hmsOverlay.transparency(it) }
        isVisible?.let { hmsOverlay.visible(it) }
        zIndex?.let { hmsOverlay.zIndex(it) }
    }
}

/**
 * Converts a [GroundOverlayOptions] object to Google Maps [GmsGroundOverlayOptions] object.
 *
 * @receiver The [GroundOverlayOptions] object to convert.
 * @return The converted Google Maps [GmsGroundOverlayOptions] object.
 */
fun GroundOverlayOptions.toGmsGroundOverlayOptions(): GmsGroundOverlayOptions {
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

/**
 * Converts a [LatLngBounds] object to Google Maps [GmsLatLngBounds] object.
 *
 * @receiver The [LatLngBounds] object to convert.
 * @return The converted Google Maps [GmsLatLngBounds] object.
 */
fun LatLngBounds.toGmsLatLngBounds(): GmsLatLngBounds {
    return GmsLatLngBounds(
        southwest.toGMSLatLng(),
        northeast.toGMSLatLng()
    )
}

/**
 * Converts a [LatLngBounds] object to Huawei Maps [HmsLatLngBounds] object.
 *
 * @receiver The [LatLngBounds] object to convert.
 * @return The converted Huawei Maps [HmsLatLngBounds] object.
 */
fun LatLngBounds.toHmsLatLngBounds(): HmsLatLngBounds {
    return HmsLatLngBounds(
        southwest.toHMSLatLng(),
        northeast.toHMSLatLng()
    )
}

/**
 * Converts a [TileOverlayOptions] object to Huawei Maps [HmsTileOverlayOptions] object.
 *
 * @receiver The [TileOverlayOptions] object to convert.
 * @return The converted Huawei Maps [HmsTileOverlayOptions] object.
 */
fun TileOverlayOptions.toHmsTileOverlayOptions(): HmsTileOverlayOptions {
    return HmsTileOverlayOptions().also { hmsTile ->
        zIndex?.let { hmsTile.zIndex(it) }
        isVisible?.let { hmsTile.visible(it) }
        fadeIn?.let { hmsTile.fadeIn(it) }
        transparency?.let { hmsTile.transparency(it) }
    }
}

/**
 * Converts a [TileOverlayOptions] object to Google Maps [GmsTileOverlayOptions] object.
 *
 * @receiver The [TileOverlayOptions] object to convert.
 * @return The converted Google Maps [GmsTileOverlayOptions] object.
 */
fun TileOverlayOptions.toGmsTileOverlayOptions(): GmsTileOverlayOptions {
    return GmsTileOverlayOptions().also { gmsTile ->
        zIndex?.let { gmsTile.zIndex(it) }
        isVisible?.let { gmsTile.visible(it) }
        fadeIn?.let { gmsTile.fadeIn(it) }
        transparency?.let { gmsTile.transparency(it) }
    }
}

/**
 * Converts a [HmsVisibleRegion] object to [VisibleRegion].
 *
 * @receiver The [HmsVisibleRegion] object to convert.
 * @return The converted [VisibleRegion] object.
 */
fun HmsVisibleRegion.toVisibleRegion(): VisibleRegion {
    return VisibleRegion(
        nearLeft = nearLeft.toLatLng(),
        nearRight = nearRight.toLatLng(),
        farLeft = farLeft.toLatLng(),
        farRight = farRight.toLatLng(),
        bounds = latLngBounds.toLatLngBounds()
    )
}

/**
 * Converts a [GmsVisibleRegion] object to [VisibleRegion].
 *
 * @receiver The [GmsVisibleRegion] object to convert.
 * @return The converted [VisibleRegion] object.
 */
fun GmsVisibleRegion.toVisibleRegion(): VisibleRegion {
    return VisibleRegion(
        nearLeft = nearLeft.toLatLng(),
        nearRight = nearRight.toLatLng(),
        farLeft = farLeft.toLatLng(),
        farRight = farRight.toLatLng(),
        bounds = latLngBounds.toLatLngBounds()
    )
}

/**
 * Converts a [GmsLatLngBounds] object to [LatLngBounds].
 *
 * @receiver The [GmsLatLngBounds] object to convert.
 * @return The converted [LatLngBounds] object.
 */
fun GmsLatLngBounds.toLatLngBounds(): LatLngBounds {
    return LatLngBounds(
        southwest.toLatLng(),
        northeast.toLatLng()
    )
}

/**
 * Converts a [HmsLatLngBounds] object to [LatLngBounds].
 *
 * @receiver The [HmsLatLngBounds] object to convert.
 * @return The converted [LatLngBounds] object.
 */
fun HmsLatLngBounds.toLatLngBounds(): LatLngBounds {
    return LatLngBounds(
        southwest.toLatLng(),
        northeast.toLatLng()
    )
}
