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

package com.hms.lib.commonmobileservices.ads.interstitial.common

import com.hms.lib.commonmobileservices.ads.interstitial.implementation.IInterstitialAd

/**
 * Callback interface for handling interstitial ad loading events.
 */
interface InterstitialAdLoadCallback {
    /**
     * Called when the loading of an interstitial ad fails.
     *
     * @param adError The error message describing the reason for the ad load failure.
     */
    fun onAdLoadFailed(adError: String)

    /**
     * Called when an interstitial ad is successfully loaded.
     *
     * @param interstitialAd The loaded interstitial ad instance.
     */
    fun onInterstitialAdLoaded(interstitialAd: IInterstitialAd)
}