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

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.site.SiteService
import com.hms.lib.commonmobileservices.site.SiteServiceReturn
import com.hms.lib.commonmobileservices.site.common.Mapper
import com.huawei.hms.site.api.SearchResultListener
import com.huawei.hms.site.api.SearchService
import com.huawei.hms.site.api.SearchServiceFactory
import com.huawei.hms.site.api.model.*
import java.lang.Exception
import java.net.URLEncoder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HuaweiSiteServiceImpl(private val context: Context, apiKey: String? = null): SiteService {

    private val siteService: SearchService = SearchServiceFactory.create(context, URLEncoder.encode(apiKey, "utf-8"))
    private val mapper: Mapper<SiteServiceReturn, Site> = HuaweiSiteMapper()

    override fun getNearbyPlaces(
        siteLat: Double?,
        siteLng: Double?,
        keyword: String?,
        hwpoiType: String?,
        areaRadius: Int?,
        areaLanguage: String?,
        Index: Int?,
        Size: Int?,
        Bounds: Boolean?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    ) {
        val nearbySearchRequest = NearbySearchRequest()
        nearbySearchRequest.location = siteLat?.let { siteLng?.let { it1 -> Coordinate(it, it1) } }
        nearbySearchRequest.query = keyword
        nearbySearchRequest.hwPoiType = hwpoiType?.let { HwLocationType.valueOf(it) }
        nearbySearchRequest.radius = areaRadius
        nearbySearchRequest.language = areaLanguage
        nearbySearchRequest.pageIndex = Index
        nearbySearchRequest.pageSize = Size
        if (Bounds != null) {
            nearbySearchRequest.strictBounds = Bounds
        }

        siteService.nearbySearch(nearbySearchRequest, object : SearchResultListener<NearbySearchResponse> {
            override fun onSearchResult(nearbySearchResponse: NearbySearchResponse?) {
                val siteList: List<Site>? = nearbySearchResponse?.getSites()
                if (nearbySearchResponse == null || nearbySearchResponse.getTotalCount() <= 0 || siteList.isNullOrEmpty()) {
                    return
                }
                callback.invoke(ResultData.Success(siteList?.let { mapper.mapToEntityList(it) }))
            }
            override fun onSearchError(searchStatus: SearchStatus) {
                Log.e(TAG, "onSearchError is: " + searchStatus.errorCode)
                callback.invoke(ResultData.Failed())
            }
        })

    }


    override fun getTextSearchPlaces(
        siteLat: Double?,
        siteLng: Double?,
        keyword: String?,
        hwpoiType: String?,
        areaRadius: Int?,
        areaLanguage: String?,
        Index: Int?,
        Size: Int?,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit){

        val textSearchRequest = TextSearchRequest()
        textSearchRequest.location = siteLat?.let { siteLng?.let { it1 -> Coordinate(it, it1) } }
        textSearchRequest.query = keyword
        textSearchRequest.hwPoiType = hwpoiType?.let { HwLocationType.valueOf(it) }
        textSearchRequest.radius = areaRadius
        textSearchRequest.language = areaLanguage
        textSearchRequest.pageIndex = Index
        textSearchRequest.pageSize = Size

        siteService.textSearch(textSearchRequest, object : SearchResultListener<TextSearchResponse> {
            override fun onSearchResult(textSearchResponse: TextSearchResponse?) {
                val siteList: List<Site>? = textSearchResponse?.getSites()
                if (textSearchResponse == null || textSearchResponse.getTotalCount() <= 0 || siteList.isNullOrEmpty()) {
                    return
                }
                callback.invoke(ResultData.Success(siteList?.let { mapper.mapToEntityList(it) }))
            }

            override fun onSearchError(searchStatus: SearchStatus) {
                Log.e(TAG, "onSearchError is: " + searchStatus.errorCode)
                callback.invoke(ResultData.Failed())
            }
        })

    }


    override fun getDetailSearch(
        siteID: String,
        areaLanguage: String,
        childrenNode: Boolean,
        callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit){

        val detailSearchRequest = DetailSearchRequest()
        detailSearchRequest.language = areaLanguage
        detailSearchRequest.siteId = siteID
        detailSearchRequest.children = childrenNode

        siteService.detailSearch(detailSearchRequest, object : SearchResultListener<DetailSearchResponse> {
            override fun onSearchResult(detailSearchResponse: DetailSearchResponse?) {
                val site: Site? = detailSearchResponse?.getSite()
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

    override fun placeSuggestion(siteLat: Double?,
                                         siteLng: Double?,
                                         keyword: String?,
                                         childrenNode: Boolean?,
                                         areaRadius: Int?,
                                         areaLanguage: String?,
                                 callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit){

        val queryAutocompleteRequest = QueryAutocompleteRequest()
        queryAutocompleteRequest.location = siteLat?.let { siteLng?.let { it1 -> Coordinate(it, it1) } }
        queryAutocompleteRequest.query = keyword
        queryAutocompleteRequest.radius = areaRadius
        queryAutocompleteRequest.language = areaLanguage
        if (childrenNode != null) {
            queryAutocompleteRequest.children = childrenNode
        }

        siteService.queryAutocomplete(queryAutocompleteRequest, object : SearchResultListener<QueryAutocompleteResponse> {
            override fun onSearchResult(queryAutocompleteResponse: QueryAutocompleteResponse?) {
                val siteList: List<Site>? = queryAutocompleteResponse?.getSites()?.toList()
                if (queryAutocompleteResponse == null || queryAutocompleteResponse.getSites().size <= 0 || siteList.isNullOrEmpty()) {
                    return
                }
                callback.invoke(ResultData.Success(siteList?.let { mapper.mapToEntityList(it) }))
            }

            override fun onSearchError(searchStatus: SearchStatus) {
                Log.e(TAG, "onSearchError is: " + searchStatus.errorCode)
                callback.invoke(ResultData.Failed())
            }
        })
    }
}
