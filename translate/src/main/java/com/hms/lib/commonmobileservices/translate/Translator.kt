// Copyright 2020. Explore in HMS. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

// http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.hms.lib.commonmobileservices.translate

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.translate.factory.TranslatorFactory
import com.hms.lib.commonmobileservices.translate.implementation.GoogleTranslator
import com.hms.lib.commonmobileservices.translate.implementation.HuaweiTranslator
import com.hms.lib.commonmobileservices.translate.implementation.ITranslator

/**
 * Singleton class responsible for providing an instance of the translator based on the mobile service type.
 */
class Translator {
    companion object {
        /**
         * Retrieves the translator instance based on the mobile service type.
         *
         * @param context The context of the application.
         * @return An instance of the translator.
         * @throws IllegalArgumentException if the mobile service type is not supported.
         */
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