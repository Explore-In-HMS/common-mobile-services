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
import com.hms.lib.commonmobileservices.ads.banner.implementation.IBannerAd

class GoogleBannerAdFactory(private val bannerAd: AdView) : BannerAdFactory() {
    override fun create(): IBannerAd = GoogleBannerAd(bannerAd)
}