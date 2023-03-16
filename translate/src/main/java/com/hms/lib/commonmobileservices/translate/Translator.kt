package com.hms.lib.commonmobileservices.translate

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.translate.factory.TranslatorFactory
import com.hms.lib.commonmobileservices.translate.implementation.GoogleTranslator
import com.hms.lib.commonmobileservices.translate.implementation.HuaweiTranslator
import com.hms.lib.commonmobileservices.translate.implementation.ITranslator

class Translator {
    companion object {
        fun getClient(
            context: Context,
        ): ITranslator {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    val translatorFactory =
                        TranslatorFactory.createFactory<GoogleTranslator>()
                    translatorFactory.create()
                }
                MobileServiceType.HMS -> {
                    val translatorFactory =
                        TranslatorFactory.createFactory<HuaweiTranslator>()
                    translatorFactory.create()
                }
                MobileServiceType.NON -> throw IllegalArgumentException()
            }
        }
    }
}