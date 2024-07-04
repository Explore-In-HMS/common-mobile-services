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

package com.hms.lib.commonmobileservices.ads.rewarded.common

import com.hms.lib.commonmobileservices.ads.rewarded.implementation.IRewardedAd


/**
 * Callback interface for handling events related to loading rewarded ads.
 */
interface RewardedAdLoadCallback {
    /**
     * Called when loading of a rewarded ad fails.
     *
     * @param adError The error message describing the failure.
     */
    fun onAdLoadFailed(adError: String)

    /**
     * Called when a rewarded ad is successfully loaded.
     *
     * @param rewardedAd The loaded rewarded ad.
     */
    fun onRewardedAdLoaded(rewardedAd: IRewardedAd)
}