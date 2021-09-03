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
package com.hms.lib.commonmobileservices.identity.model

import android.content.Intent
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

data class UserAddressResponse(
    var title: String? = null,
    var addressLine: String? = null,
    var province: String? = null,
    var county: String? = null,
    var buildingNo: String? = null,
    var aptNo: String? = null
) : Serializable {
    fun parseIntent(data: Intent?): UserAddressResponse {
        val userAddressResponse = UserAddressResponse()
        if (data == null) {
            return userAddressResponse
        } else {
            val var2: String? = data.getStringExtra("com.huawei.hms.identity.EXTRA_ADDRESS")
            if (var2 == null) {
                Log.e("addressClient", "The address is null.")
                return userAddressResponse
            } else {
                try {
                    val json = JSONObject(var2)
                    userAddressResponse.title = json.optString("name")
                    userAddressResponse.addressLine =
                        json.optString("addressLine1") + json.optString("addressLine2")
                    userAddressResponse.province = json.optString("administrativeArea")
                    userAddressResponse.county = json.optString("locality")
                    userAddressResponse.buildingNo = json.optString("addressLine4")
                    userAddressResponse.aptNo = json.optString("addressLine5")
                } catch (var4: JSONException) {
                    Log.e("addressClient", "parse address exception", var4)
                }
                return userAddressResponse
            }
        }
    }
}