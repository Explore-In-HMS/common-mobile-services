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

import android.content.Context
import android.os.Bundle
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.Work


interface CommonAnalytics {

    fun setAnalyticsEnabled(enabled: Boolean)

    fun setUserId(id: String)

    fun setUserProfile(name: String, value: String)

    fun setSessionDuration(milliseconds: Long)

    fun saveEvent(key: String, bundle: Bundle)

    fun clearCachedData()

    fun addDefaultEventParams(params: Bundle)

    fun getAAID(): Work<String>


    companion object {
        fun instance(
            context: Context,
            firstPriority: MobileServiceType? = null
        ): CommonAnalytics? {
            return when (Device.getMobileServiceType(context, firstPriority)) {
                MobileServiceType.HMS -> {
                    HMSAnalyticsImpl(context)
                }
                MobileServiceType.GMS -> {
                    GMSAnalyticsImpl(context)
                }
                MobileServiceType.NON -> null
            }
        }
    }
}