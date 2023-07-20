package com.hms.lib.commonmobileservices.ads.rewarded.implementation

import android.app.Activity
import android.os.Bundle
import com.hms.lib.commonmobileservices.ads.rewarded.common.UserRewardEarnedListener
import com.hms.lib.commonmobileservices.ads.rewarded.common.MetaDataChangedListener

interface IRewardedAd {
    fun getMetaData(): Bundle
    fun setOnMetadataChangedListener(callback: MetaDataChangedListener)
    fun setImmersive(value: Boolean)
    fun show(activity: Activity, callback: UserRewardEarnedListener)
}