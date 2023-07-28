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