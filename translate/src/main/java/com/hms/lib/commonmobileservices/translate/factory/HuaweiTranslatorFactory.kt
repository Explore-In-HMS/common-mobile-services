package com.hms.lib.commonmobileservices.translate.factory

import com.hms.lib.commonmobileservices.translate.implementation.HuaweiTranslator
import com.hms.lib.commonmobileservices.translate.implementation.ITranslator

class HuaweiTranslatorFactory : TranslatorFactory() {
    override fun create(): ITranslator {
        return HuaweiTranslator()
    }
}