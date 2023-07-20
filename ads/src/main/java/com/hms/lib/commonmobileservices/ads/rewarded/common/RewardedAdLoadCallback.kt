package com.hms.lib.commonmobileservices.ads.rewarded.common

import com.hms.lib.commonmobileservices.ads.rewarded.implementation.IRewardedAd


interface RewardedAdLoadCallback {
    fun onAdLoadFailed(adError: String)
    fun onRewardedAdLoaded(rewardedAd: IRewardedAd)
}