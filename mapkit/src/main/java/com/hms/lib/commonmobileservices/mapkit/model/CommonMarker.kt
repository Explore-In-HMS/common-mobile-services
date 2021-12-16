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

import com.huawei.hms.maps.model.Marker
import java.lang.Exception

class CommonMarker(val markerImpl : Any?) {
    fun hide(){
        when (markerImpl){
            is Marker -> markerImpl.isVisible=false
            is com.google.android.gms.maps.model.Marker -> markerImpl.isVisible=false
        }
    }

    fun show(){
        when (markerImpl){
            is Marker -> markerImpl.isVisible=true
            is com.google.android.gms.maps.model.Marker -> markerImpl.isVisible=true
        }
    }

    fun remove() : Boolean{
        return try {
            when (markerImpl){
                is Marker -> markerImpl.remove()
                is com.google.android.gms.maps.model.Marker -> markerImpl.remove()
            }
            true
        }
        catch (e:Exception){
            e.printStackTrace()
            false
        }
    }
}
