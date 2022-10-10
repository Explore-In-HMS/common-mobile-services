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

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.site.SiteService
import com.hms.lib.commonmobileservices.site.SiteServiceReturn
import com.hms.lib.commonmobileservices.site.common.Mapper
import org.json.JSONException
import org.json.JSONObject


class GoogleSiteServiceImpl(
    context: Context,
    private val apiKey: String? = null
) : SiteService {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val siteMapper: Mapper<SiteServiceReturn, JSONObject> = GoogleSiteMapper()
    private val autocompleteMapper: Mapper<SiteServiceReturn, JSONObject> =
        GoogleSiteAutocompleteMapper()

    var googlePlacesURL: StringBuilder? = java.lang.StringBuilder("")

    override fun getNearbyPlaces(
        siteLat: Double,
        siteLng: Double,
        query: String?,
        hwpoiType: String?,
        radius: Int?,
        language: String?,
        pageIndex: Int?,
        pageSize: Int?,
        strictBounds: Boolean?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    ) {
        googlePlacesURL?.clear()
        googlePlacesURL?.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlacesURL?.append("location=$siteLat,$siteLng")
        googlePlacesURL?.append("&rankby=distance")
        googlePlacesURL?.append("&type=$hwpoiType")
        googlePlacesURL?.append("&keyword=$query")
        googlePlacesURL?.append("&sensor=true")
        googlePlacesURL?.append("&key=$apiKey")

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, googlePlacesURL.toString(), null,
            { response ->
                try {
                    callback.invoke(handlePlaceApiJsonArrayResult(response, siteMapper, "results"))
                } catch (e: JSONException) {
                    Log.d(TAG, "Error adding this object" + e.message)
                    callback.invoke(handlePlaceApiError(e))
                }
            },
            { error ->
                Log.d(TAG, "Error GETTING THE JSON RESULT" + error.message)
                callback.invoke(handlePlaceApiError(error))
            }) {
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["content-type"] = "application/json"
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

    override fun getTextSearchPlaces(
        query: String,
        siteLat: Double?,
        siteLng: Double?,
        hwpoiType: String?,
        radius: Int?,
        language: String?,
        pageIndex: Int?,
        pageSize: Int?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    ) {
        googlePlacesURL?.clear()
        googlePlacesURL?.append("https://maps.googleapis.com/maps/api/place/textsearch/json?")
        googlePlacesURL?.append("&query=$query")
        siteLat?.let { lat -> siteLng?.let { lng -> googlePlacesURL?.append("location=$lat,$lng") } }
        radius?.let { googlePlacesURL?.append("&radius=$it") }
        hwpoiType?.let { googlePlacesURL?.append("&type=$it") }
        googlePlacesURL?.append("&sensor=true")
        googlePlacesURL?.append("&key=$apiKey")

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, googlePlacesURL.toString(), null,
            { response ->
                try {
                    callback.invoke(handlePlaceApiJsonArrayResult(response, siteMapper, "results"))
                } catch (e: JSONException) {
                    Log.d(TAG, "Error adding this object" + e.message)
                    callback.invoke(
                        ResultData.Failed(
                            error = e.localizedMessage,
                            errorModel = ErrorModel(message = e.message, exception = e)
                        )
                    )
                }
            },
            { error ->
                Log.d(TAG, "Error GETTING THE JSON RESULT" + error.message)
                callback.invoke(handlePlaceApiError(error))
            }) {
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["content-type"] = "application/json"
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

    override fun getDetailSearch(
        siteID: String,
        areaLanguage: String?,
        childrenNode: Boolean?,
        callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit
    ) {
        googlePlacesURL?.clear()
        googlePlacesURL?.append("https://maps.googleapis.com/maps/api/place/details/json?")
        googlePlacesURL?.append("place_id=$siteID")
        areaLanguage?.let { googlePlacesURL?.append("&language=$it") }
        googlePlacesURL?.append("&key=$apiKey")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, googlePlacesURL.toString(), null,
            { response ->
                try {
                    callback.invoke(handlePlaceApiJsonObjectResult(response, siteMapper, "result"))
                } catch (e: JSONException) {
                    Log.d(TAG, "Error adding this object" + e.message)
                    callback.invoke(
                        ResultData.Failed(
                            error = e.localizedMessage,
                            errorModel = ErrorModel(message = e.message, exception = e)
                        )
                    )
                }
            },
            { error ->
                Log.d(TAG, "Error GETTING THE JSON RESULT")
                callback.invoke(
                    ResultData.Failed(
                        error = error.localizedMessage,
                        errorModel = ErrorModel(message = error.message, exception = error)
                    )
                )
            })

        requestQueue.add(jsonObjectRequest)
    }

    override fun placeSuggestion(
        keyword: String,
        siteLat: Double?,
        siteLng: Double?,
        childrenNode: Boolean?,
        areaRadius: Int?,
        areaLanguage: String?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    ) {
        googlePlacesURL?.clear()
        googlePlacesURL?.append("https://maps.googleapis.com/maps/api/place/autocomplete/json?")
        googlePlacesURL?.append("input=$keyword")
        siteLat?.let { lat ->
            siteLng?.let { lng ->
                googlePlacesURL?.append("&location=$lat,$lng")
                googlePlacesURL?.append("&origin=$lat,$lng")
            }
        }
        areaRadius?.let { googlePlacesURL?.append("&radius=$it") }
        areaLanguage?.let { googlePlacesURL?.append("&language=$it") }
        googlePlacesURL?.append("&key=$apiKey")

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, googlePlacesURL.toString(), null,
            { response ->
                try {
                    Log.d("SiteKit1", response.toString())
                    callback.invoke(
                        handlePlaceApiJsonArrayResult(
                            response,
                            autocompleteMapper,
                            "predictions"
                        )
                    )
                } catch (e: JSONException) {
                    Log.d(TAG, "Error adding this object" + e.message)
                    callback.invoke(handlePlaceApiError(e))
                }
            },
            { error ->
                Log.d(TAG, "Error GETTING THE JSON RESULT" + error.message)
                callback.invoke(handlePlaceApiError(error))
            }) {
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["content-type"] = "application/json"
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

    private fun handlePlaceApiJsonArrayResult(
        response: JSONObject,
        mapper: Mapper<SiteServiceReturn, JSONObject>,
        jsonArrayName: String
    ): ResultData<List<SiteServiceReturn>> {
        val siteServiceReturnList: MutableList<JSONObject> = mutableListOf()
        val results = response.optJSONArray(jsonArrayName)

        return getPlaceApiResult(response.getString("status")) {
            results?.let { jsonArray ->
                for (i in 0 until jsonArray.length()) {
                    siteServiceReturnList.add(jsonArray.getJSONObject(i))
                }
            }
            ResultData.Success(mapper.mapToEntityList(siteServiceReturnList))
        }
    }

    private fun handlePlaceApiJsonObjectResult(
        response: JSONObject,
        mapper: Mapper<SiteServiceReturn, JSONObject>,
        jsonObjectName: String
    ): ResultData<SiteServiceReturn> {
        val result = response.optJSONObject(jsonObjectName)

        return getPlaceApiResult(response.getString("status")) {
            result?.let { jsonObject ->
                ResultData.Success(mapper.mapToEntity(jsonObject))
            } ?: run { ResultData.Failed("UNKNOWN_ERROR") }
        }
    }

    private fun <T> getPlaceApiResult(
        status: String,
        manageSuccessState: () -> ResultData<T>
    ): ResultData<T> {
        return when (status) {
            "OK" -> manageSuccessState()
            "ZERO_RESULTS" -> ResultData.Failed("ZERO_RESULTS")
            "INVALID_REQUEST" -> ResultData.Failed("INVALID_REQUEST")
            "REQUEST_DENIED" -> ResultData.Failed("REQUEST_DENIED")
            "OVER_QUERY_LIMIT" -> ResultData.Failed("OVER_QUERY_LIMIT")
            else -> ResultData.Failed("UNKNOWN_ERROR")
        }
    }

    private fun handlePlaceApiError(e: Exception): ResultData.Failed {
        return ResultData.Failed(
            error = e.localizedMessage,
            errorModel = ErrorModel(message = e.message, exception = e)
        )
    }
}