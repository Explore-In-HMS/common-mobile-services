package com.hms.lib.commonmobileservices.translate.factory

import com.hms.lib.commonmobileservices.translate.implementation.GoogleTranslator
import com.hms.lib.commonmobileservices.translate.implementation.ITranslator

class GoogleTranslatorFactory : TranslatorFactory() {
    override fun create(): ITranslator {
        return GoogleTranslator()
    }
}