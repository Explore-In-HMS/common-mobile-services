package com.hms.lib.commonmobileservices.ads.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.hms.lib.commonmobileservices.ads.R
import com.hms.lib.commonmobileservices.ads.splash.common.SplashAdLoadCallback
import com.hms.lib.commonmobileservices.ads.splash.factory.SplashAdFactory
import com.hms.lib.commonmobileservices.ads.splash.implementation.GoogleAppOpenAd
import com.hms.lib.commonmobileservices.ads.splash.implementation.HuaweiSplashAd
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.splash.SplashAd
import com.huawei.hms.ads.splash.SplashView
import com.huawei.hms.ads.splash.SplashView.SplashAdLoadListener

/**
 * The timeout duration for the splash ad in milliseconds.
 */
const val AD_TIMEOUT = 10000

/**
 * The message identifier for the ad timeout.
 */
const val MSG_AD_TIMEOUT = 1001


/**
 * A custom view to display splash ads from both GMS and HMS.
 * @param context The context of the application.
 * @param attrs The attribute set for custom view styling.
 * @param defStyleAttr Default style attributes.
 */
@SuppressLint("CustomViewStyleable")
class CommonSplashAdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * The HMS splash view used to display the Huawei splash ad.
     */
    private lateinit var hmsSplashView: SplashView

    /**
     * The ad unit ID for GMS (Google Mobile Services) splash ad.
     */
    private var gmsAdUnitId: String? = null

    /**
     * The ad unit ID for HMS (Huawei Mobile Services) splash ad.
     */
    private var hmsAdUnitId: String? = null

    /**
     * Indicates whether the splash ad has been paused.
     */
    private var hasPaused: Boolean = false


    /**
     * Gets the current screen orientation.
     * @return [ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE] if the orientation is landscape,
     * [ActivityInfo.SCREEN_ORIENTATION_PORTRAIT] otherwise.
     */
    private val screenOrientation: Int
        get() {
            val config = resources.configuration
            return if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

    /**
     * Handler to manage the timeout for the splash ad.
     * If the ad is not loaded within the specified timeout, this handler will trigger the `jump` method to proceed.
     */
    private val timeoutHandler = Handler(Looper.getMainLooper()) {
        if (it.what == MSG_AD_TIMEOUT) {
            if (hasWindowFocus()) {
                jump()
            }
        }
        false
    }

    /**
     * Initializes the CommonSplashAdView with attributes from XML.
     * This block reads the custom attributes defined for the view and sets the relevant properties.
     */
    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CommonSplashAdViewParams, 0, 0
        )
        gmsAdUnitId =
            typedArray.getString(R.styleable.CommonSplashAdViewParams_gms_splash_ad_unit_id)
        hmsAdUnitId =
            typedArray.getString(R.styleable.CommonSplashAdViewParams_hms_splash_ad_unit_id)
        typedArray.recycle()
    }

    /**
     * Loads the appropriate splash ad based on the mobile service type.
     * @param callback The callback to be invoked when the ad is loaded or fails to load.
     */
    fun load(
        callback: SplashAdLoadCallback,
    ) {
        when (Device.getMobileServiceType(context)) {
            MobileServiceType.GMS -> {
                AppOpenAd.load(
                    context,
                    gmsAdUnitId ?: "",
                    AdRequest.Builder().build(),
                    object : AppOpenAd.AppOpenAdLoadCallback() {
                        override fun onAdLoaded(appOpenAd: AppOpenAd) {
                            val googleAppOpenAdFactory =
                                SplashAdFactory.createFactory<GoogleAppOpenAd, AppOpenAd>(
                                    appOpenAd
                                )
                            googleAppOpenAdFactory.create().let(callback::onSplashAdLoaded)
                        }

                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            callback.onAdLoadFailed(loadAdError.toString())
                        }
                    }
                )
            }

            MobileServiceType.HMS -> {
                hmsSplashView = SplashView(context)
                val adRequestParams = AdParam.Builder().build()
                hmsSplashView.load(
                    hmsAdUnitId ?: "",
                    screenOrientation,
                    adRequestParams,
                    object : SplashAdLoadListener() {
                        override fun onAdLoaded() {
                            val huaweiSplashAdFactory =
                                SplashAdFactory.createFactory<HuaweiSplashAd, SplashAd>(SplashAd())
                            huaweiSplashAdFactory.create().let(callback::onSplashAdLoaded)
                            timeoutHandler.sendEmptyMessageDelayed(
                                MSG_AD_TIMEOUT,
                                AD_TIMEOUT.toLong()
                            )
                        }

                        override fun onAdFailedToLoad(errorCode: Int) {
                            callback.onAdLoadFailed("HMS Splash Ad Failed To Load. Error code: $errorCode")
                            jump()
                        }

                        override fun onAdDismissed() {
                            jump()
                        }
                    })
                addView(hmsSplashView)
            }

            MobileServiceType.NON -> throw IllegalArgumentException()
        }
    }

    /**
     * Handles the transition after the splash ad is displayed or fails to load.
     */
    private fun jump() {
        if (!hasPaused) {
            (hmsSplashView.parent as? ViewGroup)?.removeView(hmsSplashView)
            hmsSplashView.destroyView()
        }
    }
}
