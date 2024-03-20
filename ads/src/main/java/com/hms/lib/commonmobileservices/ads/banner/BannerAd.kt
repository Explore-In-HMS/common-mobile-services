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

package com.hms.lib.commonmobileservices.ads.banner

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.hms.lib.commonmobileservices.ads.banner.common.BannerAdLoadCallback
import com.hms.lib.commonmobileservices.ads.banner.factory.BannerAdFactory
import com.hms.lib.commonmobileservices.ads.banner.implementation.GoogleBannerAd
import com.hms.lib.commonmobileservices.ads.banner.implementation.HuaweiBannerAd
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.banner.BannerView

class BannerAd {

    companion object {
        /**
         * Loads a banner ad based on the mobile service type.
         *
         * @param context The context of the application.
         * @param gms_AdView The Google Mobile Services AdView.
         * @param hms_BannerView The Huawei Mobile Services BannerView.
         * @param gmsAdRequestParams The Google Mobile Services AdRequest parameters. Default is null.
         * @param hmsAdRequestParams The Huawei Mobile Services AdParam parameters. Default is null.
         * @param callback The callback for banner ad loading events.
         * @throws IllegalArgumentException if the mobile service type is not supported.
         */
        fun load(
            context: Context,
            gms_AdView: AdView?,
            hms_BannerView: BannerView?,
            gmsAdRequestParams: AdRequest? = null,
            hmsAdRequestParams: AdParam? = null,
            callback: BannerAdLoadCallback
        ) {
            when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val adRequestParams = gmsAdRequestParams ?: AdRequest.Builder().build()
                    gms_AdView?.apply {
                        adListener = object : com.google.android.gms.ads.AdListener() {
                            override fun onAdLoaded() {
                                super.onAdLoaded()
                                val googleBannerAdFactory =
                                    BannerAdFactory.createFactory<GoogleBannerAd, AdView>(gms_AdView)
                                googleBannerAdFactory.create().let(callback::onBannerAdLoaded)
                            }

                            override fun onAdFailedToLoad(p0: LoadAdError) {
                                callback.onAdLoadFailed(p0.toString())
                            }
                        }
                        loadAd(adRequestParams)
                    }
                }
                MobileServiceType.HMS -> {
                    val adRequestParams = hmsAdRequestParams ?: AdParam.Builder().build()
                    hms_BannerView?.apply {
                        adListener = object : AdListener() {
                            override fun onAdLoaded() {
                                super.onAdLoaded()
                                val huaweiBannerAd =
                                    BannerAdFactory.createFactory<HuaweiBannerAd, BannerView>(
                                        hms_BannerView
                                    )
                                huaweiBannerAd.create().let(callback::onBannerAdLoaded)
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