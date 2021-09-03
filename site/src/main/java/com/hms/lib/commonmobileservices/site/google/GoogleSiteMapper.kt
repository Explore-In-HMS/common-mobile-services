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

package com.hms.lib.commonmobileservices.site.google

import com.hms.lib.commonmobileservices.site.SiteServiceReturn
import com.hms.lib.commonmobileservices.site.common.Mapper
import com.huawei.hms.site.api.model.Site
import org.json.JSONObject

class GoogleSiteMapper: Mapper<SiteServiceReturn, JSONObject>() {
    override fun mapToEntity(from: JSONObject): SiteServiceReturn = SiteServiceReturn(
        id = if (from.has("place_id")){from.getString("place_id")} else {"No Place ID Info"},
        name = if (from.has("name")){from.getString("name")} else {"No Name Info"},
        locationLat = if(from.has("geometry")){from.getJSONObject("geometry").getJSONObject("location").getDouble("lat")} else {null},
        locationLong = if(from.has("geometry")){from.getJSONObject("geometry").getJSONObject("location").getDouble("lng")} else {null},
        phoneNumber = if(from.has("international_phone_number")){
            from.getString("international_phone_number")
        }
        else{
               "No phone Number"
            },
        formatAddress = if(from.has("formatted_address")){
            from.getString("formatted_address")
        }else if(from.has("vicinity")){
            from.getString("vicinity")
        }else{
             "No Formatted Address"
             },
        distance = null,
        image = if (from.has("photos")){
            val array = from.getJSONArray("photos")
            val arrayList : ArrayList<String> = ArrayList()
            for (i in 0 until array.length()){
                val currentObject = array.getJSONObject(i)
                arrayList.add(currentObject.getString("photo_reference"))
            }
            arrayList
        }
        else{
            ArrayList()
        },
        averagePrice = if (from.has("price_level")){
            from.getString("price_level").toDouble()
        }else{
             0.00
             },
        point = if (from.has("rating")) {
            from.getString("rating").toDouble()
        }else{
          0.00
        },
    )
}
