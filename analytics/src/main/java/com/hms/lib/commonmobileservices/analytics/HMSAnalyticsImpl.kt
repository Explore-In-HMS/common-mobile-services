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
import com.hms.lib.commonmobileservices.core.Work
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsTools

class HMSAnalyticsImpl(context: Context) : CommonAnalytics {

    private val hmsAnalytics = HiAnalytics.getInstance(context)

    init {
        HiAnalyticsTools.enableLog()
    }

    override fun setAnalyticsEnabled(enabled: Boolean) {
        hmsAnalytics.setAnalyticsEnabled(enabled)
    }

    override fun setUserId(id: String) {
        hmsAnalytics.setUserId(id)
    }

    override fun setUserProfile(name: String, value: String) {
        hmsAnalytics.setUserProfile(name, value)
    }

    override fun setSessionDuration(milliseconds: Long) {
        hmsAnalytics.setSessionDuration(milliseconds)
    }

    override fun saveEvent(key: String, bundle: Bundle) {
        hmsAnalytics.onEvent(key, bundle)
    }

    override fun clearCachedData() {
        hmsAnalytics.clearCachedData()
    }

    override fun addDefaultEventParams(params: Bundle) {
        hmsAnalytics.addDefaultEventParams(params)
    }

    override fun getAAID(): Work<String> {
        val worker: Work<String> = Work()
        hmsAnalytics.aaid
            .addOnSuccessListener {
                worker.onSuccess(it)
            }
            .addOnFailureListener {
                worker.onFailure(it)
            }
            .addOnCanceledListener {
                worker.onCanceled()
            }
        return worker
    }

}