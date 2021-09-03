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

import com.huawei.hms.maps.model.Polygon
import java.lang.Exception

class CommonPolygon(val polygonImpl : Any) {
    fun hide(){
        when (polygonImpl){
            is Polygon -> polygonImpl.isVisible=false
            is com.google.android.gms.maps.model.Polygon -> polygonImpl.isVisible=false
        }
    }

    fun show(){
        when (polygonImpl){
            is Polygon -> polygonImpl.isVisible=true
            is com.google.android.gms.maps.model.Polygon -> polygonImpl.isVisible=true
        }
    }

    fun remove() :Boolean{
        return try {
            when (polygonImpl){
                is Polygon -> polygonImpl.remove()
                is com.google.android.gms.maps.model.Polygon -> polygonImpl.remove()
            }
            true
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

}
