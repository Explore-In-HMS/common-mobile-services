package com.hms.lib.commonmobileservices.ads.rewarded.factory

import com.google.android.gms.ads.rewarded.RewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.GoogleRewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.HuaweiRewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.IRewardedAd
import com.huawei.hms.ads.reward.RewardAd

abstract class RewardedAdFactory {
    abstract fun create(): IRewardedAd

    companion object {
        inline fun <reified T : IRewardedAd, reified K> createFactory(rewardedAd: K): RewardedAdFactory {
            when (T::class) {
                HuaweiRewardedAd::class -> {
                    return when (K::class) {
                        com.huawei.hms.ads.reward.RewardAd::class -> {
                            HuaweiRewardedAdFactory(rewardedAd as RewardAd)
                        }
                        com.google.android.gms.ads.rewarded.RewardedAd::class -> {
                            throw IllegalArgumentException("${HuaweiRewardedAd::class.java.name} expected but ${RewardedAd::class.java.name} found")
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
                GoogleRewardedAd::class -> {
                    return when (K::class) {
                        com.google.android.gms.ads.rewarded.RewardedAd::class -> {
                            GoogleRewardedAdFactory(rewardedAd as RewardedAd)
                        }
                        com.huawei.hms.ads.reward.RewardAd::class -> {
                            throw IllegalArgumentException("${GoogleRewardedAd::class.java.name} expected but ${RewardAd::class.java.name} found")
                        }
                        else -> throw IllegalArgumentException()
                    }
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}