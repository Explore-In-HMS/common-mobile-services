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

package com.hms.lib.commonmobileservices.site

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.site.google.GoogleSiteServiceImpl
import com.hms.lib.commonmobileservices.site.huawei.HuaweiSiteServiceImpl

/**
 * Interface defining the methods to interact with site services for both Google and Huawei platforms.
 */
interface SiteService {
    /**
     * Retrieves nearby places based on the provided parameters.
     *
     * @param siteLat The latitude of the reference point for the search.
     * @param siteLng The longitude of the reference point for the search.
     * @param query (Optional) A keyword to filter search results.
     * @param hwpoiType (Optional) The type of point of interest to search for.
     * @param radius (Optional) The search radius in meters.
     * @param language (Optional) The language code for the search results.
     * @param pageIndex (Optional) The index of the page of results to retrieve.
     * @param pageSize (Optional) The number of results to retrieve per page.
     * @param strictBounds (Optional) Indicates whether to strictly bound the search results within the defined area.
     * @param callback The callback function to handle the result data or error.
     */
    fun getNearbyPlaces(
        siteLat: Double,
        siteLng: Double,
        query: String? = null,
        hwpoiType: String? = null,
        radius: Int? = null,
        language: String? = null,
        pageIndex: Int? = null,
        pageSize: Int? = null,
        strictBounds: Boolean? = null,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    )

    /**
     * Retrieves places based on a text search query.
     *
     * @param query The search query.
     * @param siteLat (Optional) The latitude of the reference point for the search.
     * @param siteLng (Optional) The longitude of the reference point for the search.
     * @param hwpoiType (Optional) The type of point of interest to search for.
     * @param radius (Optional) The search radius in meters.
     * @param language (Optional) The language code for the search results.
     * @param pageIndex (Optional) The index of the page of results to retrieve.
     * @param pageSize (Optional) The number of results to retrieve per page.
     * @param callback The callback function to handle the result data or error.
     */
    fun getTextSearchPlaces(
        query: String,
        siteLat: Double? = null,
        siteLng: Double? = null,
        hwpoiType: String? = null,
        radius: Int? = null,
        language: String? = null,
        pageIndex: Int? = null,
        pageSize: Int? = null,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    )

    /**
     * Retrieves detailed information about a specific place.
     *
     * @param siteID The unique identifier of the place.
     * @param areaLanguage (Optional) The language code for the search results.
     * @param childrenNode (Optional) Indicates whether to include children nodes in the result.
     * @param callback The callback function to handle the result data or error.
     */
    fun getDetailSearch(
        siteID: String,
        areaLanguage: String? = null,
        childrenNode: Boolean? = null,
        callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit
    )

    /**
     * Provides suggestions for places based on a keyword.
     *
     * @param keyword The keyword for which suggestions are requested.
     * @param siteLat (Optional) The latitude of the reference point for the search.
     * @param siteLng (Optional) The longitude of the reference point for the search.
     * @param childrenNode (Optional) Indicates whether to include children nodes in the result.
     * @param areaRadius (Optional) The search radius in meters.
     * @param areaLanguage (Optional) The language code for the search results.
     * @param callback The callback function to handle the result data or error.
     */
    fun placeSuggestion(
        keyword: String,
        siteLat: Double? = null,
        siteLng: Double? = null,
        childrenNode: Boolean? = null,
        areaRadius: Int? = null,
        areaLanguage: String? = null,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    )

    /**
     * Factory object for creating instances of the SiteService interface.
     */
    object Factory {
        /**
         * Creates an instance of the SiteService interface based on the device's mobile service type.
         *
         * @param context The application context.
         * @param huaweiApiKey The API key for Huawei services.
         * @param googleApiKey The API key for Google services.
         * @return An implementation of the SiteService interface.
         * @throws Exception If neither Google Mobile Services nor Huawei Mobile Services are installed on the device.
         */
        fun create(context: Context, huaweiApiKey: String, googleApiKey: String): SiteService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> GoogleSiteServiceImpl(context, googleApiKey)
                MobileServiceType.HMS -> HuaweiSiteServiceImpl(context, huaweiApiKey)
                else -> throw Exception("In order to use this SDK, you should have Google Mobile Services or Huawei Mobile Services installed in your device.")
            }
        }
    }
}