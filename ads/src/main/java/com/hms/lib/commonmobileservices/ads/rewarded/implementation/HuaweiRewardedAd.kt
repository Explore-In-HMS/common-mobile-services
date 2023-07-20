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