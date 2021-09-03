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
import com.hms.lib.commonmobileservices.core.*
import com.hms.lib.commonmobileservices.site.google.GoogleSiteServiceImpl
import com.hms.lib.commonmobileservices.site.huawei.HuaweiSiteServiceImpl
import com.huawei.hms.site.api.model.*

interface SiteService {
    fun getNearbyPlaces(siteLat: Double?,
                                siteLng: Double?,
                                query: String?,
                                hwpoiType: String?,
                                radius: Int?,
                                language: String?,
                                pageIndex: Int?,
                                pageSize: Int?,
                                strictBounds: Boolean?,
                        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit)

    fun getTextSearchPlaces(siteLat: Double?,
                                    siteLng: Double?,
                                    query: String?,
                                    hwpoiType: String?,
                                    radius: Int?,
                                    language: String?,
                                    pageIndex: Int?,
                                    pageSize: Int?,
                            callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit)

    fun getDetailSearch(siteID: String,
                                areaLanguage: String,
                                childrenNode: Boolean,
                        callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit)

    fun placeSuggestion(siteLat: Double?,
                                siteLng: Double?,
                                keyword: String?,
                                childrenNode: Boolean?,
                                areaRadius: Int?,
                                areaLanguage: String?,
                        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit)


    object Factory {
        fun create(context: Context, apiKey: String?=null): SiteService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleSiteServiceImpl(context, apiKey)
                }
                MobileServiceType.HMS -> {
                    HuaweiSiteServiceImpl(context, apiKey)
                }
                else -> {
                    throw Exception("In order to use this SDK, you should have Google Mobile Services or Huawei Mobile Services installed in your device.")
                }
            }
        }
    }
}
