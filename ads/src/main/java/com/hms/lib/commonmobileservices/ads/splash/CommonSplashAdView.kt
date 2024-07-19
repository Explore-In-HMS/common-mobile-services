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

const val AD_TIMEOUT = 10000
const val MSG_AD_TIMEOUT = 1001

@SuppressLint("CustomViewStyleable")
class CommonSplashAdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var hmsSplashView: SplashView

    private var gmsAdUnitId: String? = null
    private var hmsAdUnitId: String? = null
    private var hasPaused: Boolean = false

    private var screenOrientation: Int
        get() {
            val config = resources.configuration
            return if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

    private val timeoutHandler = Handler(Looper.getMainLooper()) {
        if (it.what == MSG_AD_TIMEOUT) {
            if (hasWindowFocus()) {
                jump()
            }
        }
        false
    }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CommonSplashAdViewParams, 0, 0
        )
        gmsAdUnitId =
            typedArray.getString(R.styleable.CommonSplashAdViewParams_gms_splash_ad_unit_id)
        hmsAdUnitId =
            typedArray.getString(R.styleable.CommonSplashAdViewParams_hms_splash_ad_unit_id)
        screenOrientation =
            typedArray.getInt(R.styleable.CommonSplashAdViewParams_screen_orientation, 0)
        typedArray.recycle()
    }

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

    private fun jump() {
        if (!hasPaused) {
            (hmsSplashView.parent as? ViewGroup)?.removeView(hmsSplashView)
            hmsSplashView.destroyView()
        }
    }
}
