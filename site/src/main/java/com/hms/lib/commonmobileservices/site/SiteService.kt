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

interface SiteService {
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

    fun getDetailSearch(
        siteID: String,
        areaLanguage: String? = null,
        childrenNode: Boolean? = null,
        callback: (SiteToReturnResult: ResultData<SiteServiceReturn>) -> Unit
    )

    fun placeSuggestion(
        keyword: String,
        siteLat: Double? = null,
        siteLng: Double? = null,
        childrenNode: Boolean? = null,
        areaRadius: Int? = null,
        areaLanguage: String? = null,
        callback: (SiteToReturnResult: ResultData<List<SiteServiceReturn>>) -> Unit
    )


    object Factory {
        fun create(context: Context, huaweiApiKey: String, googleApiKey: String): SiteService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> GoogleSiteServiceImpl(context, googleApiKey)
                MobileServiceType.HMS -> HuaweiSiteServiceImpl(context, huaweiApiKey)
                else -> throw Exception("In order to use this SDK, you should have Google Mobile Services or Huawei Mobile Services installed in your device.")
            }
        }
    }
}