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
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog.TAG
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.site.SiteService
import com.hms.lib.commonmobileservices.site.SiteServiceReturn
import com.hms.lib.commonmobileservices.site.common.Mapper
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class GoogleSiteServiceImpl(private val context: Context, apiKey: String? = null): SiteService {
    val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val mapper: Mapper<SiteServiceReturn, JSONObject> = GoogleSiteMapper()
    private val autocompleteMapper: Mapper<SiteServiceReturn, JSONObject> = GoogleSiteAutocompleteMapper()

    var googlePlacesURL: StringBuilder? = java.lang.StringBuilder("")
    var API_KEY = apiKey
    override fun getNearbyPlaces(
        siteLat: Double?,
        siteLng: Double?,
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
        googlePlacesURL?.append("location=" + siteLat.toString() + "," + siteLng.toString())
        googlePlacesURL?.append("&rankby=distance")
        googlePlacesURL?.append("&type=" + hwpoiType)
        googlePlacesURL?.append("&keyword=" + query)
        googlePlacesURL?.append("&sensor=true")
        googlePlacesURL?.append("&key=" + API_KEY)

        var siteServiceReturnList: MutableList<JSONObject> = mutableListOf()
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, googlePlacesURL.toString(), null,
            { response ->
                try {
                    var jsonArray : JSONArray = JSONArray()
                    jsonArray = response.optJSONArray("results")
                    if (response.getString("status").equals("OK")){
                        for (i in 0 until jsonArray.length()){
                            val currentjsonObject = jsonArray.getJSONObject(i)
                            siteServiceReturnList.add(currentjsonObject)

                        }
                        callback.invoke(ResultData.Success(mapper.mapToEntityList(siteServiceReturnList)))
                    }else if(response.getString("status").equals("ZERO_RESULTS")){
                        Toast.makeText(context, "No results.", Toast.LENGTH_LONG).show()
                    }
                }catch (e: JSONException){
                    Log.d(TAG, "Error adding this object" + e.message)
                }
            },
            { error ->
                Log.d(TAG, "Error GETTING THE JSON RESULT" + error.message)
                callback.invoke(ResultData.Failed())
            })
        {
            override fun getHeaders(): Map<String, String>  {
                val params: MutableMap<String, String> = HashMap()
                params.put("content-type", "application/json")
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

    override fun getTextSearchPlaces(
        siteLat: Double?,
        siteLng: Double?,
        query: String?,
        hwpoiType: String?,
        radius: Int?,
        language: String?,
        pageIndex: Int?,
        pageSize: Int?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    ) {

        googlePlacesURL?.clear()
        googlePlacesURL?.append("https://maps.googleapis.com/maps/api/place/textsearch/json?")
        googlePlacesURL?.append("location=" + siteLat + "," + siteLng)
        googlePlacesURL?.append("&radius=" + radius)
        googlePlacesURL?.append("&type=" + hwpoiType)
        googlePlacesURL?.append("&keyword=" + query)
        googlePlacesURL?.append("&sensor=true")
        googlePlacesURL?.append("&key=" + API_KEY)

        var siteServiceReturnList: MutableList<JSONObject> = mutableListOf()
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, googlePlacesURL.toString(), null,
            { response ->
                try {
                    var jsonArray : JSONArray? = JSONArray()
                    jsonArray = response.optJSONArray("results")
                    if (response.getString("status").equals("OK")){
                        for (i in 0 until jsonArray.length()){
                            val currentjsonObject = jsonArray.getJSONObject(i)
                            siteServiceReturnList.add(currentjsonObject)

                        }
                        callback.invoke(ResultData.Success(mapper.mapToEntityList(siteServiceReturnList)))
                    }else if(response.getString("status").equals("ZERO_RESULTS")){
                        Toast.makeText(context, "No results.", Toast.LENGTH_LONG).show()
                    }

                }catch (e: JSONException){
                    Log.d(TAG, "Error adding this object" + e.message)
                }

            },
            { error ->
                Log.d(TAG, "Error GETTING THE JSON RESULT" + error.message)
                callback.invoke(ResultData.Failed())
            })
        {
            override fun getHeaders(): Map<String, String>  {
                val params: MutableMap<String, String> = HashMap()
                params.put("content-type", "application/json")
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

    override fun getDetailSearch(
        siteID: String,
        areaLanguage: String,
        childrenNode: Boolean,
        callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit
    ) {

        googlePlacesURL?.clear()
        googlePlacesURL?.append("https://maps.googleapis.com/maps/api/place/details/json?")
        googlePlacesURL?.append("place_id=" + siteID)
        googlePlacesURL?.append("&language=" + areaLanguage)
        googlePlacesURL?.append("&key=" + API_KEY)


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, googlePlacesURL.toString(), null,
            { response ->
                try {
                    val jsonObject: JSONObject = response.getJSONObject("result")
                    if (response.getString("status").equals("OK")) {
                        callback.invoke(ResultData.Success(mapper.mapToEntity(jsonObject)))

                    } else if (response.getString("status").equals("ZERO_RESULTS")) {
                        Toast.makeText(context, "No results.", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Log.d(TAG, "Error making this request")
                    }
                }
                catch (e: JSONException){
                    Log.d(TAG, "Error adding this object" + e.message)
                }

            },
            { error ->
                Log.d(TAG, "Error GETTING THE JSON RESULT")
                callback.invoke(ResultData.Failed())
            })

        requestQueue.add(jsonObjectRequest)
    }

    override fun placeSuggestion(
        siteLat: Double?,
        siteLng: Double?,
        keyword: String?,
        childrenNode: Boolean?,
        areaRadius: Int?,
        areaLanguage: String?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    ){

        googlePlacesURL?.clear()
        googlePlacesURL?.append("https://maps.googleapis.com/maps/api/place/autocomplete/json?")
        googlePlacesURL?.append("input=" + keyword)
        googlePlacesURL?.append("&location=" + siteLat + "," + siteLng)
        googlePlacesURL?.append("&origin=" + siteLat + "," + siteLng)
        googlePlacesURL?.append("&radius=" + areaRadius)
        googlePlacesURL?.append("&language=" + areaLanguage)
        googlePlacesURL?.append("&key=" + API_KEY)

        var siteServiceReturnList: MutableList<JSONObject> = mutableListOf()
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, googlePlacesURL.toString(), null,
            { response ->
                try {
                    var jsonArray : JSONArray? = JSONArray()
                    jsonArray = response.optJSONArray("predictions")
                    for (i in 0 until jsonArray.length()){
                        val currentjsonObject = jsonArray.getJSONObject(i)
                        siteServiceReturnList.add(currentjsonObject)

                    }

                }catch (e: JSONException){
                    Log.d(TAG, "Error adding this object" + e.message)
                }

                callback.invoke(ResultData.Success(autocompleteMapper.mapToEntityList(siteServiceReturnList)))
            },
            { error ->
                Log.d(TAG, "Error GETTING THE JSON RESULT" + error.message)
                callback.invoke(ResultData.Failed())
            })
        {
            override fun getHeaders(): Map<String, String>  {
                val params: MutableMap<String, String> = HashMap()
                params.put("content-type", "application/json")
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

}
