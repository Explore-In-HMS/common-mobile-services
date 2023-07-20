package com.hms.lib.commonmobileservices.ads.rewarded.factory

import com.hms.lib.commonmobileservices.ads.rewarded.implementation.HuaweiRewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.IRewardedAd
import com.huawei.hms.ads.reward.RewardAd

class HuaweiRewardedAdFactory(private val rewardedAd: RewardAd): RewardedAdFactory() {
    override fun create(): IRewardedAd = HuaweiRewardedAd(rewardedAd)
}