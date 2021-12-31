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
package com.hms.lib.commonmobileservices.analytics

import android.os.Bundle
import android.util.Log

inline fun <reified T> CommonAnalytics.saveSingleData(
    eventKey: String,
    paramKey: String? = null,
    data: T
) {
    val bundle = Bundle()
    val paramKeyGen = paramKey ?: eventKey
    when (data) {
        is String -> bundle.putString(paramKeyGen, data)
        is Int -> bundle.putInt(paramKeyGen, data)
        is Long -> bundle.putLong(paramKeyGen, data)
        is Double -> bundle.putDouble(paramKeyGen, data)
        is Float -> bundle.putFloat(paramKeyGen, data)
        else -> {
            Log.i("ShoppingAnalytics", "Invalid type")
        }
    }
    saveEvent(eventKey, bundle)
}

//To log data with key and variable number of features
//example: CommonAnalytics.saveData("ProductClick","price",20.99,"title","Gloves","seller","Wintermax")
fun CommonAnalytics.saveData(eventKey: String, vararg keyDataPairs: Any) {
    val bundle = Bundle()
    keyDataPairs.forEachIndexed { index, value ->
        if (index % 2 == 0) {
            when (val data = keyDataPairs[index + 1]) {
                is String -> bundle.putString(value as String, data)
                is Int -> bundle.putInt(value as String, data)
                is Long -> bundle.putLong(value as String, data)
                is Double -> bundle.putDouble(value as String, data)
                is Float -> bundle.putFloat(value as String, data)
                else -> {
                    Log.i("ShoppingAnalytics", "Invalid type")
                }
            }
        }
    }
    saveEvent(eventKey, bundle)
}