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

package com.hms.lib.commonmobileservices.ads.splash.factory

import com.google.android.gms.ads.appopen.AppOpenAd
import com.hms.lib.commonmobileservices.ads.splash.implementation.GoogleAppOpenAd
import com.hms.lib.commonmobileservices.ads.splash.implementation.HuaweiSplashAd
import com.hms.lib.commonmobileservices.ads.splash.implementation.ISplashAd
import com.huawei.hms.ads.splash.SplashAd

abstract class SplashAdFactory {
    abstract fun create(): ISplashAd

    companion object {
        inline fun <reified T : ISplashAd, reified K> createFactory(splashAd: K): SplashAdFactory {
            when (T::class) {
                HuaweiSplashAd::class -> {
                    return when (K::class) {
                        com.huawei.hms.ads.splash.SplashAd::class -> {
                            HuaweiSplashAdFactory(splashAd as SplashAd)
                        }
                        com.google.android.gms.ads.appopen.AppOpenAd::class -> {
                            throw IllegalArgumentException("${SplashAd::class.java.name} expected but ${AppOpenAd::class.java.name} found")
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
                GoogleAppOpenAd::class -> {
                    return when (K::class) {
                        com.google.android.gms.ads.appopen.AppOpenAd::class -> {
                            GoogleAppOpenAdFactory(splashAd as AppOpenAd)
                        }
                        com.huawei.hms.ads.splash.SplashAd::class -> {
                            throw IllegalArgumentException("${AppOpenAd::class.java.name} expected but ${SplashAd::class.java.name} found")
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}