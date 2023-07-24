package com.hms.lib.commonmobileservices.ads.rewarded

import android.content.Context
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.common.RewardedAdLoadCallback
import com.hms.lib.commonmobileservices.ads.rewarded.factory.RewardedAdFactory
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.GoogleRewardedAd
import com.hms.lib.commonmobileservices.ads.rewarded.implementation.HuaweiRewardedAd
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.reward.RewardAd
import com.huawei.hms.ads.reward.RewardAdLoadListener

class RewardedAd {

    companion object {
        /**
         * TODO:Explain AdRequestParams in Readme document
         * TODO:Initialize Ad_ID param with test ad_ID
         */
        fun load(
            context: Context,
            hmsAd_ID: String,
            gmsAd_ID: String,
            callback: RewardedAdLoadCallback,
            gmsAdRequestParams: AdManagerAdRequest? = null,
            hmsAdRequestParams: AdParam? = null
        ) {
            when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val adRequestParams = gmsAdRequestParams ?: AdManagerAdRequest.Builder().build()
                    RewardedAd.load(
                        context,
                        gmsAd_ID,
                        adRequestParams,
                        object : com.google.android.gms.ads.rewarded.RewardedAdLoadCallback() {
                            override fun onAdLoaded(p0: RewardedAd) {
                                val huaweiRewardedAdFactory = RewardedAdFactory.createFactory<GoogleRewardedAd, RewardedAd>(p0)
                                huaweiRewardedAdFactory.create().let(callback::onRewardedAdLoaded)
                            }

                            override fun onAdFailedToLoad(p0: LoadAdError) {
                                callback.onAdLoadFailed(p0.toString())
                            }
                        })
                }
                MobileServiceType.HMS -> {
                    val rewardAd = RewardAd(context, hmsAd_ID)
                    val adRequestParams = hmsAdRequestParams ?: AdParam.Builder().build()
                    rewardAd.loadAd(adRequestParams, object : RewardAdLoadListener() {
                        override fun onRewardedLoaded() {
                            val googleRewardedAdFactory = RewardedAdFactory.createFactory<HuaweiRewardedAd,RewardAd>(rewardAd)
                            googleRewardedAdFactory.create().let(callback::onRewardedAdLoaded)
                        }

                        override fun onRewardAdFailedToLoad(errorCode: Int) {
                            callback.onAdLoadFailed("HMS Reward Ad Failed To Load. Error code: $errorCode")
                        }
                    })
                }
                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}