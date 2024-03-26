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

/**
 * An inline function designed to simplify the logging of analytics events with a single data point.
 * It supports common data types such as String, Int, Long, Double, and Float. The function
 * automatically packages the data into a [Bundle] with an appropriate key-value pair and logs
 * the event using the [CommonAnalytics.saveEvent] method.
 *
 * @param T The type parameter, reified to allow checking the type at runtime.
 * @param eventKey The key representing the event to be logged. This is used as the event identifier.
 * @param paramKey An optional parameter key to be used instead of the event key for the data in the bundle.
 * If null or not provided, the event key will be used as the parameter key.
 * @param data The data to be logged with the event. Supported types are String, Int, Long, Double, and Float.
 * If a type other than these is provided, an informational log message will be output and the data will not be logged.
 *
 * Usage of this function allows for a more concise and type-safe way of logging analytics events that
 * consist of a single data point. It handles the creation of the [Bundle], insertion of the data based on its
 * type, and calls [saveEvent] method of [CommonAnalytics] interface, making the analytics logging process
 * simpler and error-free.
 *
 * Example:
 * ```
 * analytics.saveSingleData("purchaseAmount", "amount", 9.99)
 * ```
 * In this example, an event named "purchaseAmount" is logged with a single parameter "amount" having a value of 9.99.
 */
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
/**
 * Saves analytics data for a given event key.
 *
 * @param eventKey The key identifying the event.
 * @param keyDataPairs The key-value pairs of data to be saved.
 * @throws IllegalArgumentException if an invalid data type is provided.
 */
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