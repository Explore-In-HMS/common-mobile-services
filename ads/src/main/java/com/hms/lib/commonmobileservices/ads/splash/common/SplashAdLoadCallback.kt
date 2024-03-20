package com.hms.lib.commonmobileservices.ads.splash.common

import com.hms.lib.commonmobileservices.ads.splash.implementation.ISplashAd

interface SplashAdLoadCallback {
    fun onAdLoadFailed(adError: String)
    fun onSplashAdLoaded(splashAd: ISplashAd)
}