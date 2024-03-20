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
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.OnAdMetadataChangedListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.common.IRewardItem
import com.hms.lib.commonmobileservices.ads.rewarded.common.UserRewardEarnedListener
import com.hms.lib.commonmobileservices.ads.rewarded.common.MetaDataChangedListener

class GoogleRewardedAd(private var _rewarded: RewardedAd) : IRewardedAd {
    override fun getMetaData(): Bundle = _rewarded.adMetadata

    override fun setOnMetadataChangedListener(callback: MetaDataChangedListener) {
        val listener = OnAdMetadataChangedListener { callback.onMetaDataChanged() }
        _rewarded.onAdMetadataChangedListener = listener
    }

    override fun setImmersive(value: Boolean) {
        _rewarded.setImmersiveMode(value)
    }

    override fun show(activity: Activity, callback: UserRewardEarnedListener) {
        val listener = object : OnUserEarnedRewardListener {
            override fun onUserEarnedReward(p0: RewardItem) {
                val rewardItem = object : IRewardItem {
                    override fun getAmount(): Int {
                        return p0.amount
                    }

                    override fun getTypeOrName(): String {
                        return p0.type
                    }

                }
                callback.onUserEarnedReward(rewardItem)
            }
        }
        _rewarded.show(activity, listener)
    }

}