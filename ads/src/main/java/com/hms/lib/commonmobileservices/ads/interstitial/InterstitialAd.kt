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

package com.hms.lib.commonmobileservices.ads.interstitial

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.hms.lib.commonmobileservices.ads.R
import com.hms.lib.commonmobileservices.ads.interstitial.common.InterstitialAdLoadCallback
import com.hms.lib.commonmobileservices.ads.interstitial.factory.InterstitialAdFactory
import com.hms.lib.commonmobileservices.ads.interstitial.implementation.GoogleInterstitialAd
import com.hms.lib.commonmobileservices.ads.interstitial.implementation.HuaweiInterstitialAd
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam

/**
 * A class to manage loading interstitial ads for both Google Mobile Services (GMS) and Huawei Mobile Services (HMS).
 */
class InterstitialAd {
    companion object {
        /**
         * Loads an interstitial ad based on the mobile service type available on the device.
         *
         * @param context The context in which the ad is to be loaded.
         * @param gmsAdUnitId The ad unit ID for Google Mobile Services.
         * @param hmsAdUnitId The ad unit ID for Huawei Mobile Services.
         * @param callback The callback to notify when the ad is loaded or if loading fails.
         */
        fun load(
            context: Context,
            gmsAdUnitId: String? = null,
            hmsAdUnitId: String? = null,
            callback: InterstitialAdLoadCallback,
        ) {
            when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val adRequestParams = AdManagerAdRequest.Builder().build()
                    if (gmsAdUnitId != null) {
                        InterstitialAd.load(
                            context,
                            gmsAdUnitId,
                            adRequestParams,
                            object :
                                com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback() {
                                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                    val googleInterstitialAdFactory =
                                        InterstitialAdFactory.createFactory<GoogleInterstitialAd, InterstitialAd>(
                                            interstitialAd
                                        )
                                    googleInterstitialAdFactory.create()
                                        .let(callback::onInterstitialAdLoaded)
                                }

                                override fun onAdFailedToLoad(adError: LoadAdError) {
                                    callback.onAdLoadFailed(adError.toString())
                                }
                            })
                    } else Log.i(TAG, context.getString(R.string.you_must_enter_the_gms_ad_unit_id))
                }

                MobileServiceType.HMS -> {
                    val interstitialAd = com.huawei.hms.ads.InterstitialAd(context)
                    val adRequestParams = AdParam.Builder().build()
                    if (hmsAdUnitId != null) {
                        interstitialAd.apply {
                            adId = hmsAdUnitId
                            adListener = object : AdListener() {
                                override fun onAdLoaded() {
                                    super.onAdLoaded()
                                    val huaweiInterstitialAd =
                                        InterstitialAdFactory.createFactory<HuaweiInterstitialAd, com.huawei.hms.ads.InterstitialAd>(
                                            interstitialAd
                                        )
                                    huaweiInterstitialAd.create()
                                        .let(callback::onInterstitialAdLoaded)
                                }

                                override fun onAdFailed(errorCode: Int) {
                                    callback.onAdLoadFailed(errorCode.toString())
                                }
                            }
                            loadAd(adRequestParams)
                        }
                    } else Log.i(TAG, context.getString(R.string.you_must_enter_the_hms_ad_unit_id))
                }

                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}