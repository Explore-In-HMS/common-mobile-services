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

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.hms.lib.commonmobileservices.ads.interstitial.common.InterstitialAdLoadCallback
import com.hms.lib.commonmobileservices.ads.interstitial.factory.InterstitialAdFactory
import com.hms.lib.commonmobileservices.ads.interstitial.implementation.GoogleInterstitialAd
import com.hms.lib.commonmobileservices.ads.interstitial.implementation.HuaweiInterstitialAd
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam

/**
 * Helper class for loading interstitial ads based on the mobile service type.
 */
class InterstitialAd {
    companion object {
        /**
         * Loads an interstitial ad based on the mobile service type.
         *
         * @param context The context of the application.
         * @param hmsAd_ID The ad ID for Huawei Mobile Services.
         * @param gmsAd_ID The ad ID for Google Mobile Services.
         * @param gmsAdRequestParams The Google Mobile Services AdRequest parameters. Default is null.
         * @param hmsAdRequestParams The Huawei Mobile Services AdParam parameters. Default is null.
         * @param callback The callback for interstitial ad loading events.
         * @throws IllegalArgumentException if the mobile service type is not supported.
         */
        fun load(
            context: Context,
            hmsAd_ID: String,
            gmsAd_ID: String,
            gmsAdRequestParams: AdRequest? = null,
            hmsAdRequestParams: AdParam? = null,
            callback: InterstitialAdLoadCallback
        ) {
            when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val adRequestParams = gmsAdRequestParams ?: AdManagerAdRequest.Builder().build()
                    InterstitialAd.load(
                        context,
                        gmsAd_ID,
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
                }
                MobileServiceType.HMS -> {
                    val interstitialAd = com.huawei.hms.ads.InterstitialAd(context)
                    val adRequestParams = hmsAdRequestParams ?: AdParam.Builder().build()
                    interstitialAd.apply {
                        adId = hmsAd_ID
                        adListener = object : AdListener() {
                            override fun onAdLoaded() {
                                super.onAdLoaded()
                                val huaweiInterstitialAd =
                                    InterstitialAdFactory.createFactory<HuaweiInterstitialAd, com.huawei.hms.ads.InterstitialAd>(
                                        interstitialAd
                                    )
                                huaweiInterstitialAd.create().let(callback::onInterstitialAdLoaded)
                            }

                            override fun onAdFailed(errorCode: Int) {
                                callback.onAdLoadFailed(errorCode.toString())
                            }
                        }
                        loadAd(adRequestParams)
                    }
                }
                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}