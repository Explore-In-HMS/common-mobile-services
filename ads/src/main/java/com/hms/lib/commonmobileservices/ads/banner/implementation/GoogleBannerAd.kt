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

package com.hms.lib.commonmobileservices.ads.banner.implementation

import com.google.android.gms.ads.AdView

/**
 * Implementation of the IBannerAd interface for Google banner ads.
 *
 * @property _banner The AdView instance representing the Google banner ad.
 */
class GoogleBannerAd(private var _banner: AdView) : IBannerAd {
    /**
     * Destroys the Google banner ad.
     */
    override fun destroy() {
        _banner.destroy()
    }

    /**
     * Checks if the Google banner ad is currently loading.
     *
     * @return true if the banner ad is loading, false otherwise.
     */
    override fun isLoading(): Boolean {
        return _banner.isLoading
    }
}