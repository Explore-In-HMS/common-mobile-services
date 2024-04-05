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
package com.hms.lib.commonmobileservices.translate.factory

import com.hms.lib.commonmobileservices.translate.implementation.GoogleTranslator
import com.hms.lib.commonmobileservices.translate.implementation.HuaweiTranslator
import com.hms.lib.commonmobileservices.translate.implementation.ITranslator

/**
 * Abstract factory class for creating instances of translators.
 */
abstract class TranslatorFactory {
    /**
     * Abstract method to create a translator instance.
     *
     * @return An instance of ITranslator.
     */
    abstract fun create(): ITranslator

    companion object {
        /**
         * Creates a factory for the specified type of translator.
         *
         * @return An instance of TranslatorFactory corresponding to the provided translator type.
         * @throws IllegalArgumentException if the provided translator type is not supported.
         */
        inline fun <reified T : ITranslator> createFactory(): TranslatorFactory =
            when (T::class) {
                HuaweiTranslator::class -> HuaweiTranslatorFactory()
                GoogleTranslator::class -> GoogleTranslatorFactory()
                else -> throw IllegalArgumentException("Unsupported translator type")
            }
    }
}