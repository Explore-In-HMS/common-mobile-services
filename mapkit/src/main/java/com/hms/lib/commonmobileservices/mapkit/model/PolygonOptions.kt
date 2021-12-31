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

import kotlin.collections.ArrayList

class PolygonOptions()  {
    var points = ArrayList<LatLng>()
    var holes = ArrayList<List<LatLng?>>()
    var strokeColor : Int? = -16777216
    var strokeWidth : Float? = 10.0F
    var fillColor: Int? = 0
    var zIndex: Float? = 0.0F
    var isVisible: Boolean? = true
    var isGeodesic: Boolean? = false
    var isClickable: Boolean? = false
    var strokeJointType: Int = 0
}


