package com.hms.lib.commonmobileservices.ads.banner

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.hms.lib.commonmobileservices.ads.R
import com.hms.lib.commonmobileservices.ads.banner.common.BannerAdLoadCallback
import com.hms.lib.commonmobileservices.ads.banner.factory.BannerAdFactory
import com.hms.lib.commonmobileservices.ads.banner.implementation.GoogleBannerAd
import com.hms.lib.commonmobileservices.ads.banner.implementation.HuaweiBannerAd
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import com.huawei.hms.ads.banner.BannerView

@SuppressLint("CustomViewStyleable")
/**
 * A custom view that encapsulates banner ad views for both GMS (Google Mobile Services) and HMS (Huawei Mobile Services).
 * It dynamically initializes the appropriate ad view based on the mobile service type available on the device.
 *
 * @param context The context to use.
 * @param attrs The attributes of the XML tag that is inflating the view.
 * @param defStyleAttr An attribute in the current theme that contains a reference to a style resource that supplies defaults values for the view. Can be 0 to not look for defaults.
 */
class CommonBannerAdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var gmsAdView: AdView
    private lateinit var hmsAdView: BannerView

    private var gmsAdUnitId: String? = null
    private var hmsAdUnitId: String? = null

    private var gmsAdSize: AdSize? = null
    private var hmsAdSize: BannerAdSize? = null

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CommonAdViewParams, 0, 0
        )
        gmsAdUnitId = typedArray.getString(R.styleable.CommonAdViewParams_gms_ad_unit_id)
        hmsAdUnitId = typedArray.getString(R.styleable.CommonAdViewParams_hms_ad_unit_id)

        gmsAdSize = getGMSAdSize(typedArray.getInt(R.styleable.CommonAdViewParams_ad_size, -1))
        hmsAdSize = getHMSAdSize(typedArray.getInt(R.styleable.CommonAdViewParams_ad_size, -1))

        typedArray.recycle()
    }

    /**
     * Initializes the appropriate banner ad view based on the mobile service type.
     *
     * @param callback The callback to be invoked when an ad is loaded or fails to load.
     */
    fun initialize(callback: BannerAdLoadCallback) {

        when (Device.getMobileServiceType(context)) {
            MobileServiceType.GMS -> {
                gmsAdView = AdView(context).apply {
                    gmsAdUnitId?.let { adUnitId = it }
                    gmsAdSize?.let { setAdSize(it) }
                    adListener = object : com.google.android.gms.ads.AdListener() {
                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            val googleBannerAdFactory =
                                BannerAdFactory.createFactory<GoogleBannerAd, AdView>(gmsAdView)
                            googleBannerAdFactory.create().let(callback::onBannerAdLoaded)
                        }

                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            callback.onAdLoadFailed(p0.toString())
                        }
                    }
                }
                addView(gmsAdView)
                loadGMSAd()
            }

            MobileServiceType.HMS -> {
                hmsAdView = BannerView(context).apply {
                    adId = hmsAdUnitId
                    bannerAdSize = hmsAdSize
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            val huaweiBannerAd =
                                BannerAdFactory.createFactory<HuaweiBannerAd, BannerView>(
                                    hmsAdView
                                )
                            huaweiBannerAd.create().let(callback::onBannerAdLoaded)
                        }

                        override fun onAdFailed(errorCode: Int) {
                            callback.onAdLoadFailed(errorCode.toString())
                        }
                    }
                }
                addView(hmsAdView)
                loadHMSAd()
            }

            MobileServiceType.NON -> throw IllegalArgumentException(context.getString(R.string.unsupported_mobile_service_type))
        }
    }

    /**
     * Loads a GMS banner ad.
     */
    private fun loadGMSAd() {
        val adRequest = AdRequest.Builder().build()
        gmsAdView.loadAd(adRequest)
    }

    /**
     * Loads an HMS banner ad.
     */
    private fun loadHMSAd() {
        val adParam = AdParam.Builder().build()
        hmsAdView.loadAd(adParam)
    }

    /**
     * Returns the appropriate GMS ad size based on the provided integer.
     *
     * @param size The integer representing the ad size.
     * @return The corresponding GMS AdSize.
     */
    private fun getGMSAdSize(size: Int): AdSize {
        return when (size) {
            1 -> AdSize.LARGE_BANNER
            2 -> AdSize.FULL_BANNER
            3 -> AdSize.LEADERBOARD
            4 -> AdSize.MEDIUM_RECTANGLE
            5 -> AdSize.SMART_BANNER
            6 -> AdSize.WIDE_SKYSCRAPER
            else -> AdSize.BANNER
        }
    }

    /**
     * Returns the appropriate HMS ad size based on the provided integer.
     *
     * @param size The integer representing the ad size.
     * @return The corresponding HMS BannerAdSize.
     */
    private fun getHMSAdSize(size: Int): BannerAdSize {
        return when (size) {
            1 -> BannerAdSize.BANNER_SIZE_360_57
            2 -> BannerAdSize.BANNER_SIZE_360_144
            3 -> BannerAdSize.BANNER_SIZE_468_60
            4 -> BannerAdSize.BANNER_SIZE_320_100
            5 -> BannerAdSize.BANNER_SIZE_DYNAMIC
            6 -> BannerAdSize.BANNER_SIZE_320_50
            else -> BannerAdSize.BANNER_SIZE_320_50
        }
    }

    /**
     * Cleans up resources when the view is detached from the window.
     */
    override fun onDetachedFromWindow() {
        gmsAdView.destroy()
        hmsAdView.destroy()
        super.onDetachedFromWindow()
    }
}