package com.hms.lib.commonmobileservices.languagedetection.factory

import com.hms.lib.commonmobileservices.languagedetection.implementation.GoogleLanguageIdentification
import com.hms.lib.commonmobileservices.languagedetection.implementation.HuaweiLanguageDetection
import com.hms.lib.commonmobileservices.languagedetection.implementation.ILanguageDetection
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