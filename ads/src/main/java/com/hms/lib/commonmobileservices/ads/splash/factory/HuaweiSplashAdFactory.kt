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

import com.hms.lib.commonmobileservices.ads.splash.implementation.HuaweiSplashAd
import com.hms.lib.commonmobileservices.ads.splash.implementation.ISplashAd
import com.huawei.hms.ads.splash.SplashAd

/**
 * Factory class for creating Huawei Splash ads.
 *
 * @param splashAd The Huawei Splash ad to be used for creating the splash ad.
 */
class HuaweiSplashAdFactory(private val splashAd: SplashAd) : SplashAdFactory() {
    /**
     * Creates an instance of the Huawei Splash ad.
     *
     * @return An instance of the ISplashAd interface representing the Huawei Splash ad.
     */
    override fun create(): ISplashAd = HuaweiSplashAd(splashAd)
}