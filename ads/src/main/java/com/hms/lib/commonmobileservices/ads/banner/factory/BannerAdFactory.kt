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

package com.hms.lib.commonmobileservices.ads.banner.factory

import com.google.android.gms.ads.AdView
import com.hms.lib.commonmobileservices.ads.banner.implementation.GoogleBannerAd
import com.hms.lib.commonmobileservices.ads.banner.implementation.HuaweiBannerAd
import com.hms.lib.commonmobileservices.ads.banner.implementation.IBannerAd
import com.huawei.hms.ads.banner.BannerView

/**
 * Abstract factory class for creating banner ads.
 */
abstract class BannerAdFactory {
    /**
     * Abstract method to create a banner ad instance.
     *
     * @return The created banner ad.
     */
    abstract fun create(): IBannerAd

    companion object {
        /**
         * Creates a banner ad factory based on the type of banner ad and its associated view.
         *
         * @param bannerAd The banner ad instance.
         * @return A banner ad factory.
         * @throws IllegalArgumentException If an invalid combination of banner ad type and view type is provided.
         */
        inline fun <reified T : IBannerAd, reified K> createFactory(bannerAd: K?): BannerAdFactory {
            return when (T::class) {
                HuaweiBannerAd::class -> {
                    when (K::class) {
                        BannerView::class -> {
                            HuaweiBannerAdFactory(bannerAd as BannerView)
                        }
                        AdView::class -> {
                            throw IllegalArgumentException("${HuaweiBannerAd::class.java.name} expected but ${AdView::class.java.name} found")
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
                GoogleBannerAd::class -> {
                    when (K::class) {
                        AdView::class -> {
                            GoogleBannerAdFactory(bannerAd as AdView)
                        }
                        BannerView::class -> {
                            throw IllegalArgumentException("${GoogleBannerAd::class.java.name} expected but ${BannerView::class.java.name} found")
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}