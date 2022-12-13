package com.hms.lib.commonmobileservices.languagedetection.factory

import com.hms.lib.commonmobileservices.languagedetection.implementation.HuaweiLanguageDetection
import com.hms.lib.commonmobileservices.languagedetection.implementation.ILanguageDetection
import com.huawei.hms.mlsdk.langdetect.MLLangDetectorFactory
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetectorSetting

class HuaweiLanguageDetectorFactory: LanguageDetectionFactory() {

    override fun create(): ILanguageDetection {
        val mlRemoteLangDetector = MLLangDetectorFactory.getInstance()
            .remoteLangDetector

        return HuaweiLanguageDetection(mlRemoteLangDetector)
    }

    override fun create(confidenceThreshold: Float): ILanguageDetection {
        val threshold = confidenceThreshold.coerceIn(0.0f,1.0f)
        val setting = MLRemoteLangDetectorSetting.Factory()
            .setTrustedThreshold(threshold)
            .create()
        val mlRemoteLangDetector = MLLangDetectorFactory.getInstance()
            .getRemoteLangDetector(setting)

        return HuaweiLanguageDetection(mlRemoteLangDetector)
    }
}