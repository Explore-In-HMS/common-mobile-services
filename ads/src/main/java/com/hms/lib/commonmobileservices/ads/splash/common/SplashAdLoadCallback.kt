package com.hms.lib.commonmobileservices.ads.splash.common

import com.hms.lib.commonmobileservices.ads.splash.implementation.ISplashAd

/**
 * Callback interface for handling events related to loading splash ads.
 */
interface SplashAdLoadCallback {
    /**
     * Called when the splash ad fails to load.
     *
     * @param adError A string representation of the error encountered while loading the ad.
     */
    fun onAdLoadFailed(adError: String)

    /**
     * Called when the splash ad is successfully loaded.
     *
     * @param splashAd The loaded splash ad.
     */
    fun onSplashAdLoaded(splashAd: ISplashAd)
}