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

package com.hms.lib.commonmobileservices.site.huawei

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.site.SiteService
import com.hms.lib.commonmobileservices.site.SiteServiceReturn
import com.hms.lib.commonmobileservices.site.common.Mapper
import com.huawei.hms.site.api.SearchResultListener
import com.huawei.hms.site.api.SearchService
import com.huawei.hms.site.api.SearchServiceFactory
import com.huawei.hms.site.api.model.*
import java.net.URLEncoder

class HuaweiSiteServiceImpl(context: Context, apiKey: String? = null) : SiteService {

    private val siteService: SearchService =
        SearchServiceFactory.create(context, URLEncoder.encode(apiKey, "utf-8"))
    private val mapper: Mapper<SiteServiceReturn, Site> = HuaweiSiteMapper()

    override fun getNearbyPlaces(
        siteLat: Double,
        siteLng: Double,
        keyword: String?,
        hwpoiType: String?,
        areaRadius: Int?,
        areaLanguage: String?,
        pageIndex: Int?,
        pageSize: Int?,
        strictBounds: Boolean?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    ) {
        val nearbySearchRequest = NearbySearchRequest()
        nearbySearchRequest.location = Coordinate(siteLat, siteLng)
        keyword?.let { nearbySearchRequest.query = it }
        hwpoiType?.let { nearbySearchRequest.hwPoiType = HwLocationType.valueOf(it) }
        areaRadius?.let { nearbySearchRequest.radius = it }
        areaLanguage?.let { nearbySearchRequest.language = it }
        pageIndex?.let { nearbySearchRequest.pageIndex = it }
        pageSize?.let { nearbySearchRequest.pageSize = it }
        strictBounds?.let { nearbySearchRequest.strictBounds = it }

        siteService.nearbySearch(
            nearbySearchRequest,
            object : SearchResultListener<NearbySearchResponse> {
                override fun onSearchResult(nearbySearchResponse: NearbySearchResponse?) {
                    val siteList: List<Site>? = nearbySearchResponse?.sites
                    if (nearbySearchResponse == null || nearbySearchResponse.totalCount <= 0 || siteList.isNullOrEmpty()) {
                        return
                    }
                    callback.invoke(ResultData.Success(siteList.let { mapper.mapToEntityList(it) }))
                }

                override fun onSearchError(searchStatus: SearchStatus) {
                    Log.e(TAG, "onSearchError is: " + searchStatus.errorCode)
                    callback.invoke(ResultData.Failed())
                }
            })

    }


    override fun getTextSearchPlaces(
        keyword: String,
        siteLat: Double?,
        siteLng: Double?,
        hwpoiType: String?,
        areaRadius: Int?,
        areaLanguage: String?,
        pageIndex: Int?,
        pageSize: Int?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    ) {

        val textSearchRequest = TextSearchRequest()
        textSearchRequest.query = keyword
        siteLat?.let { lat -> siteLng?.let { lng -> textSearchRequest.location = Coordinate(lat, lng) } }
        hwpoiType?.let { textSearchRequest.hwPoiType = HwLocationType.valueOf(it) }
        areaRadius?.let { textSearchRequest.radius = it }
        areaLanguage?.let { textSearchRequest.language = it }
        pageIndex?.let { textSearchRequest.pageIndex = it }
        pageSize?.let { textSearchRequest.pageSize = it }


        siteService.textSearch(
            textSearchRequest,
            object : SearchResultListener<TextSearchResponse> {
                override fun onSearchResult(textSearchResponse: TextSearchResponse?) {
                    val siteList: List<Site>? = textSearchResponse?.sites
                    if (textSearchResponse == null || textSearchResponse.totalCount <= 0 || siteList.isNullOrEmpty()) {
                        return
                    }
                    callback.invoke(ResultData.Success(siteList.let { mapper.mapToEntityList(it) }))
                }

                override fun onSearchError(searchStatus: SearchStatus) {
                    Log.e(TAG, "onSearchError is: " + searchStatus.errorCode)
                    callback.invoke(ResultData.Failed())
                }
            })

    }


    override fun getDetailSearch(
        siteID: String,
        areaLanguage: String?,
        childrenNode: Boolean?,
        callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit
    ) {

        val detailSearchRequest = DetailSearchRequest()
        detailSearchRequest.siteId = siteID
        areaLanguage?.let { detailSearchRequest.language = it }
        childrenNode?.let { detailSearchRequest.isChildren = it }


        siteService.detailSearch(
            detailSearchRequest,
            object : SearchResultListener<DetailSearchResponse> {
                override fun onSearchResult(detailSearchResponse: DetailSearchResponse?) {
                    val site: Site? = detailSearchResponse?.site
                    if (detailSearchResponse == null) {
                        return
                    }
                    callback.invoke(ResultData.Success(site?.let { mapper.mapToEntity(it) }))
                }

                override fun onSearchError(searchStatus: SearchStatus) {
                    Log.e(TAG, "onSearchError is: " + searchStatus.errorCode)
                    callback.invoke(ResultData.Failed())
                }
            })
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
        val queryAutocompleteRequest = QueryAutocompleteRequest()
        queryAutocompleteRequest.query = keyword
        siteLat?.let { lat -> siteLng?.let { lng -> queryAutocompleteRequest.location = Coordinate(lat, lng) } }
        areaRadius?.let { queryAutocompleteRequest.radius = it }
        areaLanguage?.let { queryAutocompleteRequest.language = it }
        childrenNode?.let { queryAutocompleteRequest.isChildren = it }

        siteService.queryAutocomplete(
            queryAutocompleteRequest,
            object : SearchResultListener<QueryAutocompleteResponse> {
                override fun onSearchResult(queryAutocompleteResponse: QueryAutocompleteResponse?) {
                    val siteList: List<Site>? = queryAutocompleteResponse?.sites?.toList()
                    if (queryAutocompleteResponse == null ||
                        queryAutocompleteResponse.sites.isEmpty() ||
                        siteList.isNullOrEmpty()) {
                        return
                    }
                    callback.invoke(ResultData.Success(siteList.let { mapper.mapToEntityList(it) }))
                }

                override fun onSearchError(searchStatus: SearchStatus) {
                    Log.e(TAG, "onSearchError is: " + searchStatus.errorCode)
                    callback.invoke(ResultData.Failed())
                }
            })
    }
}