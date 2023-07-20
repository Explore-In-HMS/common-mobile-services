package com.hms.lib.commonmobileservices.ads.rewarded.factory

import com.google.android.gms.ads.rewarded.RewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.GoogleRewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.IRewardedAd

class GoogleRewardedAdFactory(private val rewardedAd: RewardedAd) : RewardedAdFactory() {
    override fun create(): IRewardedAd = GoogleRewardedAd(rewardedAd)
}