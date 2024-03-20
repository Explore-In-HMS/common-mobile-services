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

package com.hms.lib.commonmobileservices.ads.rewarded.implementation

import android.app.Activity
import android.os.Bundle
import com.hms.lib.commonmobileservices.ads.rewarded.common.IRewardItem
import com.hms.lib.commonmobileservices.ads.rewarded.common.UserRewardEarnedListener
import com.hms.lib.commonmobileservices.ads.rewarded.common.MetaDataChangedListener
import com.huawei.hms.ads.reward.OnMetadataChangedListener
import com.huawei.hms.ads.reward.Reward
import com.huawei.hms.ads.reward.RewardAd
import com.huawei.hms.ads.reward.RewardAdStatusListener

class HuaweiRewardedAd(private var _rewarded: RewardAd) : IRewardedAd {
    override fun getMetaData(): Bundle = _rewarded.metadata
    override fun setOnMetadataChangedListener(callback: MetaDataChangedListener) {
        val listener = object : OnMetadataChangedListener() {
            override fun onMetadataChanged() {
                callback.onMetaDataChanged()
            }
        }
        _rewarded.setOnMetadataChangedListener(listener)
    }

    override fun setImmersive(value: Boolean) {
        _rewarded.setImmersive(value)
    }

    override fun show(activity: Activity, callback: UserRewardEarnedListener) {
        val listener = object : RewardAdStatusListener() {
            override fun onRewarded(p0: Reward?) {
                val rewardItem = object : IRewardItem {
                    override fun getAmount(): Int? {
                        return p0?.amount
                    }

                    override fun getTypeOrName(): String? {
                        return p0?.name
                    }
                }
                callback.onUserEarnedReward(rewardItem)
            }
        }
        _rewarded.show(activity, listener)
    }
}