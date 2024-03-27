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

package com.hms.lib.commonmobileservices.ads.interstitial.factory

import com.hms.lib.commonmobileservices.ads.interstitial.implementation.GoogleInterstitialAd
import com.hms.lib.commonmobileservices.ads.interstitial.implementation.HuaweiInterstitialAd
import com.hms.lib.commonmobileservices.ads.interstitial.implementation.IInterstitialAd
import com.huawei.hms.ads.InterstitialAd

/**
 * Abstract factory class for creating interstitial ads.
 */
abstract class InterstitialAdFactory {
    /**
     * Abstract method to create an interstitial ad instance.
     *
     * @return The created interstitial ad.
     */
    abstract fun create(): IInterstitialAd

    companion object {
        /**
         * Creates an interstitial ad factory based on the type of interstitial ad and its associated view.
         *
         * @param interstitialAd The interstitial ad instance.
         * @return An interstitial ad factory.
         * @throws IllegalArgumentException If an invalid combination of interstitial ad type and view type is provided.
         */
        inline fun <reified T : IInterstitialAd, reified K> createFactory(interstitialAd: K): InterstitialAdFactory {
            return when (T::class) {
                HuaweiInterstitialAd::class -> {
                    when (K::class) {
                        InterstitialAd::class -> {
                            HuaweiInterstitialAdFactory(interstitialAd as InterstitialAd)
                        }
                        com.google.android.gms.ads.interstitial.InterstitialAd::class -> {
                            throw IllegalArgumentException("${HuaweiInterstitialAd::class.java.name} expected but ${com.google.android.gms.ads.interstitial.InterstitialAd::class.java.name} found")
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
                GoogleInterstitialAd::class -> {
                    when (K::class) {
                        com.google.android.gms.ads.interstitial.InterstitialAd::class -> {
                            GoogleInterstitialAdFactory(interstitialAd as com.google.android.gms.ads.interstitial.InterstitialAd)
                        }
                        InterstitialAd::class -> {
                            throw IllegalArgumentException("${GoogleInterstitialAd::class.java.name} expected but ${InterstitialAd::class.java.name} found")
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}