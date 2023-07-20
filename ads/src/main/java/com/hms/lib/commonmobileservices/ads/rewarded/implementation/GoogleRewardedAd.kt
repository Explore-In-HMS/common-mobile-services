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