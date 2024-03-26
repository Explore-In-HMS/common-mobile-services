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

package com.hms.lib.commonmobileservices.ads.splash

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.hms.lib.commonmobileservices.ads.splash.common.SplashAdLoadCallback
import com.hms.lib.commonmobileservices.ads.splash.factory.SplashAdFactory
import com.hms.lib.commonmobileservices.ads.splash.implementation.GoogleAppOpenAd
import com.hms.lib.commonmobileservices.ads.splash.implementation.HuaweiSplashAd
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.splash.SplashView
import com.huawei.hms.ads.splash.SplashView.SplashAdLoadListener

/**
 * A utility class for loading Splash Ads.
 */
class SplashAd {
    companion object {
        /**
         * Loads a Splash Ad based on the mobile service type.
         *
         * @param context The context of the application.
         * @param hmsAd_ID The Huawei Mobile Services Ad ID.
         * @param gmsAd_ID The Google Mobile Services Ad ID.
         * @param splashView The SplashView used for displaying the ad.
         * @param screenOrientation The screen orientation for the ad.
         * @param callback The callback for handling ad loading events.
         * @param hmsAdRequestParams The Huawei Mobile Services AdParam parameters. Default is null.
         * @throws IllegalArgumentException if the mobile service type is not supported.
         */
        fun load(
            context: Context,
            hmsAd_ID: String,
            gmsAd_ID: String,
            splashView: SplashView,
            screenOrientation: Int,
            callback: SplashAdLoadCallback,
            hmsAdRequestParams: AdParam? = null
        ) {
            when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    AppOpenAd.load(
                        context,
                        gmsAd_ID,
                        AdRequest.Builder().build(),
                        object : AppOpenAd.AppOpenAdLoadCallback() {
                            override fun onAdLoaded(appOpenAd: AppOpenAd) {
                                val googleAppOpenAdFactory =
                                    SplashAdFactory.createFactory<GoogleAppOpenAd, AppOpenAd>(
                                        appOpenAd
                                    )
                                googleAppOpenAdFactory.create().let(callback::onSplashAdLoaded)
                            }

                            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                callback.onAdLoadFailed(loadAdError.toString())
                            }
                        }
                    )
                }
                MobileServiceType.HMS -> {
                    val splashAd = SplashAd()
                    val adRequestParams = hmsAdRequestParams ?: AdParam.Builder().build()
                    splashView.load(
                        hmsAd_ID,
                        screenOrientation,
                        adRequestParams,
                        object : SplashAdLoadListener() {
                            override fun onAdLoaded() {
                                val huaweiSplashAdFactory =
                                    SplashAdFactory.createFactory<HuaweiSplashAd, SplashAd>(splashAd)
                                huaweiSplashAdFactory.create().let(callback::onSplashAdLoaded)
                            }

                            override fun onAdFailedToLoad(errorCode: Int) {
                                callback.onAdLoadFailed("HMS Splash Ad Failed To Load. Error code: $errorCode")
                            }
                        })
                }
                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}