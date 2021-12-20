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
package com.hms.lib.commonmobileservices.mapkit

import android.graphics.Point
import com.hms.lib.commonmobileservices.mapkit.model.*
import java.lang.Exception

class Projection(private val projectionImpl: Any) {
    fun fromScreenLocation(point: Point): LatLng?{
        return try{
            when(projectionImpl){
                is HmsProjection -> projectionImpl.fromScreenLocation(point).toLatLng()
                is GmsProjection -> projectionImpl.fromScreenLocation(point).toLatLng()
            }
            null
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    fun toScreenLocation(latLng: LatLng): Point?{
        return try{
            when(projectionImpl){
                is HmsProjection -> projectionImpl.toScreenLocation(latLng.toHMSLatLng())
                is GmsProjection -> projectionImpl.toScreenLocation(latLng.toGMSLatLng())
            }
            null
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    fun getVisibleRegion(): VisibleRegion?{
        return try{
            when(projectionImpl){
                is HmsProjection -> projectionImpl.visibleRegion.toVisibleRegion()
                is GmsProjection -> projectionImpl.visibleRegion.toVisibleRegion()
            }
            null
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}