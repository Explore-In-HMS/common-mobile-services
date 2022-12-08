package com.huawei.hms.languagedetection

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.languagedetection.factory.LanguageDetectionFactory
import com.huawei.hms.languagedetection.implementation.GoogleLanguageIdentification
import com.huawei.hms.languagedetection.implementation.HuaweiLanguageDetection
import com.huawei.hms.languagedetection.implementation.ILanguageDetection
import com.huawei.hms.mlsdk.common.MLApplication

class HuaweiGoogleLanguageDetector private constructor() {
    companion object {
        fun getClient(
            context: Context,
            huaweiApiKey: String,
            confidenceThreshold: Float? = null
        ): ILanguageDetection {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val languageDetectionFactory =
                        LanguageDetectionFactory.createFactory<GoogleLanguageIdentification>()
                    lateinit var googleLanguageIdentification: ILanguageDetection

                    confidenceThreshold?.let {
                        googleLanguageIdentification = languageDetectionFactory.create(it)
                    } ?: run { googleLanguageIdentification = languageDetectionFactory.create() }

                    googleLanguageIdentification
                }
                MobileServiceType.HMS -> {
                    MLApplication.getInstance().apiKey = huaweiApiKey
                    val languageDetectionFactory =
                        LanguageDetectionFactory.createFactory<HuaweiLanguageDetection>()
                    lateinit var huaweiLanguageDetection: ILanguageDetection

                    confidenceThreshold?.let {
                        huaweiLanguageDetection = languageDetectionFactory.create(it)
                    } ?: run { huaweiLanguageDetection = languageDetectionFactory.create() }

                    huaweiLanguageDetection
                }
                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}