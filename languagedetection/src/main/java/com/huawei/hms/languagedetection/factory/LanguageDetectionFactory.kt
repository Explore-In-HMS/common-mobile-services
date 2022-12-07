package com.huawei.hms.languagedetection.factory

import com.huawei.hms.languagedetection.implementation.GoogleLanguageIdentification
import com.huawei.hms.languagedetection.implementation.HuaweiLanguageDetection
import com.huawei.hms.languagedetection.implementation.ILanguageDetection
import java.lang.IllegalArgumentException

abstract class LanguageDetectionFactory {
    abstract fun create(): ILanguageDetection
    abstract fun create(confidenceThreshold: Float): ILanguageDetection

    companion object {
        inline fun <reified T: ILanguageDetection> createFactory(): LanguageDetectionFactory =
            when(T::class){
                HuaweiLanguageDetection::class -> HuaweiLanguageDetectorFactory()
                GoogleLanguageIdentification::class -> GoogleLanguageDetectorFactory()
                else -> throw IllegalArgumentException()
            }
    }
}