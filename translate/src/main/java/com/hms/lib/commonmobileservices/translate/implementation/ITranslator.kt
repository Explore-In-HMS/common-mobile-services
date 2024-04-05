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
package com.hms.lib.commonmobileservices.translate.implementation

import com.hms.lib.commonmobileservices.translate.common.DeleteModelResult
import com.hms.lib.commonmobileservices.translate.common.DownloadModelResult
import com.hms.lib.commonmobileservices.translate.common.RequiresModelDownloadResult
import com.hms.lib.commonmobileservices.translate.common.TranslateResult

/**
 * Interface for a translator service that provides translation functionalities.
 */
interface ITranslator {
    /**
     * Translates the given text from the source language to the target language.
     *
     * @param text The text to be translated.
     * @param sourceLanguage The language code of the source language.
     * @param targetLanguage The language code of the target language.
     * @param callback Callback function to handle the translation result.
     */
    fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        callback: (
            translateResult: TranslateResult
        ) -> Unit
    )

    /**
     * Checks if the translation model for the given language code needs to be downloaded.
     *
     * @param langCode The language code.
     * @param callback Callback function to handle the result.
     */
    fun requiresModelDownload(
        langCode: String,
        callback: (
            requiresModelDownloadResult: RequiresModelDownloadResult
        ) -> Unit
    )

    /**
     * Downloads the translation model for the given language code.
     *
     * @param langCode The language code.
     * @param requireWifi Indicates whether to require Wi-Fi for the download. Default is true.
     * @param callback Callback function to handle the result.
     */
    fun downloadModel(
        langCode: String,
        requireWifi: Boolean = true,
        callback: (
            downloadResult: DownloadModelResult
        ) -> Unit
    )

    /**
     * Deletes the downloaded translation model for the given language code.
     *
     * @param langCode The language code.
     * @param callback Callback function to handle the result.
     */
    fun deleteModel(
        langCode: String,
        callback: (
            downloadResult: DeleteModelResult
        ) -> Unit
    )

    /**
     * Closes the translator service.
     */
    fun close()
}