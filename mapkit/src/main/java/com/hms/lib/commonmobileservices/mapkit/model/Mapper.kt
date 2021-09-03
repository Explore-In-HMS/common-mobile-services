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

import com.huawei.hms.maps.model.*

fun Polygon.toCommonPolygon() : CommonPolygon = CommonPolygon(this)
fun Polyline.toCommonPolyline() : CommonPolyline = CommonPolyline(this)

fun com.google.android.gms.maps.model.Polygon.toCommonPolygon() : CommonPolygon = CommonPolygon(this)
fun com.google.android.gms.maps.model.Polyline.toCommonPolyline() : CommonPolyline = CommonPolyline(this)

fun CommonPolygonOptions.toHMSPolygonOptions() : PolygonOptions {
    return PolygonOptions().addAll(baseLatLngs.map { it.toHMSLatLng() }).also { hmsOpts->
        strokeColor?.let {hmsOpts.strokeColor(it)}
        strokeWidth?.let {hmsOpts.strokeWidth(it)}
    }
}

fun CommonLatLng.toHMSLatLng() : LatLng {
    return LatLng(lat,lng)
}

fun LatLng.toCommonLatLng() : CommonLatLng {
    return CommonLatLng(latitude,longitude)
}

fun CommonLatLng.toGMSLatLng() : com.google.android.gms.maps.model.LatLng{
    return com.google.android.gms.maps.model.LatLng(lat,lng)
}

fun com.google.android.gms.maps.model.LatLng.toCommonLatLng() : CommonLatLng {
    return CommonLatLng(latitude,longitude)
}


fun CommonPolygonOptions.toGMSPolygonOptions() : com.google.android.gms.maps.model.PolygonOptions{
    return com.google.android.gms.maps.model.PolygonOptions().
    addAll(baseLatLngs.map { it.toGMSLatLng() }).also {gmsOpts->
        strokeColor?.let {gmsOpts.strokeColor(it)}
        strokeWidth?.let {gmsOpts.strokeWidth(it)}
    }
}

fun CommonPolylineOptions.toHMSPolylineOptions() : PolylineOptions {
    return PolylineOptions().addAll(baseLatLngs.map { it.toHMSLatLng() }).also { hmsOpts->
        Color?.let {hmsOpts.color(it)}
        Width?.let {hmsOpts.width(it)}
    }
}

fun CommonPolylineOptions.toGMSPolylineOptions() : com.google.android.gms.maps.model.PolylineOptions{
    return com.google.android.gms.maps.model.PolylineOptions().
    addAll(baseLatLngs.map { it.toGMSLatLng() }).also {gmsOpts->
        Color?.let {gmsOpts.color(it)}
        Width?.let {gmsOpts.width(it)}
    }
}



