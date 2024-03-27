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

import com.hms.lib.commonmobileservices.ads.banner.implementation.HuaweiBannerAd
import com.hms.lib.commonmobileservices.ads.banner.implementation.IBannerAd
import com.huawei.hms.ads.banner.BannerView

/**
 * Factory class for creating Huawei banner ads.
 *
 * @param bannerAd The BannerView instance used for creating the banner ad.
 */
class HuaweiBannerAdFactory(private val bannerAd: BannerView) : BannerAdFactory() {
    /**
     * Creates a Huawei banner ad instance.
     *
     * @return The created Huawei banner ad.
     */
    override fun create(): IBannerAd = HuaweiBannerAd(bannerAd)
}