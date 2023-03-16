package com.hms.lib.commonmobileservices.translate.factory

import com.hms.lib.commonmobileservices.translate.implementation.GoogleTranslator
import com.hms.lib.commonmobileservices.translate.implementation.HuaweiTranslator
import com.hms.lib.commonmobileservices.translate.implementation.ITranslator

abstract class TranslatorFactory {
    abstract fun create(): ITranslator
    companion object {
        inline fun <reified T: ITranslator> createFactory(): TranslatorFactory =
            when(T::class){
                HuaweiTranslator::class -> HuaweiTranslatorFactory()
                GoogleTranslator::class -> GoogleTranslatorFactory()
                else -> throw IllegalArgumentException()
            }
    }
}