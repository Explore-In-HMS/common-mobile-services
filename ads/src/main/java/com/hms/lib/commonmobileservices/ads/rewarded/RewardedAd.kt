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

package com.hms.lib.commonmobileservices.ads.rewarded

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.hms.lib.commonmobileservices.ads.R
import com.hms.lib.commonmobileservices.ads.rewarded.common.RewardedAdLoadCallback
import com.hms.lib.commonmobileservices.ads.rewarded.factory.RewardedAdFactory
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.GoogleRewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.HuaweiRewardedAd
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.reward.RewardAd
import com.huawei.hms.ads.reward.RewardAdLoadListener

/**
 * Utility class for loading rewarded ads based on the mobile service type.
 */
class RewardedAd {
    companion object {
        private const val TAG = "RewardedAd"
        /**
         * Loads a rewarded ad based on the mobile service type.
         *
         * @param context The context of the application.
         * @param hmsAdUnitId The ad ID for Huawei Mobile Services.
         * @param gmsAdUnitId The ad ID for Google Mobile Services.
         * @param callback The callback for rewarded ad loading events.
         * @throws IllegalArgumentException if the mobile service type is not supported.
         */
        fun load(
            context: Context,
            hmsAdUnitId: String? = null,
            gmsAdUnitId: String? = null,
            callback: RewardedAdLoadCallback
        ) {
            when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val adRequestParams = AdManagerAdRequest.Builder().build()
                    if (gmsAdUnitId != null) {
                        RewardedAd.load(
                            context,
                            gmsAdUnitId,
                            adRequestParams,
                            object : com.google.android.gms.ads.rewarded.RewardedAdLoadCallback() {
                                override fun onAdLoaded(p0: RewardedAd) {
                                    val huaweiRewardedAdFactory =
                                        RewardedAdFactory.createFactory<GoogleRewardedAd, RewardedAd>(p0)
                                    huaweiRewardedAdFactory.create().let(callback::onRewardedAdLoaded)
                                }

                                override fun onAdFailedToLoad(p0: LoadAdError) {
                                    callback.onAdLoadFailed(p0.toString())
                                }
                            })
                    } else Log.i(TAG, context.getString(R.string.you_must_enter_the_gms_ad_unit_id))
                }
                MobileServiceType.HMS -> {
                    val adRequestParams = AdParam.Builder().build()
                    if (hmsAdUnitId != null) {
                        val rewardAd = RewardAd(context, hmsAdUnitId)
                        rewardAd.loadAd(adRequestParams, object : RewardAdLoadListener() {
                            override fun onRewardedLoaded() {
                                val googleRewardedAdFactory =
                                    RewardedAdFactory.createFactory<HuaweiRewardedAd, RewardAd>(rewardAd)
                                googleRewardedAdFactory.create().let(callback::onRewardedAdLoaded)
                            }

                            override fun onRewardAdFailedToLoad(errorCode: Int) {
                                callback.onAdLoadFailed(context.getString(R.string.hms_reward_ad_failed_to_load_error_code) + "$errorCode")
                            }
                        })
                    } else Log.i(TAG, context.getString(R.string.you_must_enter_the_hms_ad_unit_id))
                }
                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}