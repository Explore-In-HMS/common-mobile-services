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

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.*
import com.hms.lib.commonmobileservices.translate.common.DeleteModelResult
import com.hms.lib.commonmobileservices.translate.common.DownloadModelResult
import com.hms.lib.commonmobileservices.translate.common.RequiresModelDownloadResult
import com.hms.lib.commonmobileservices.translate.common.TranslateResult

/**
 * Implementation of the [ITranslator] interface for Google Translator.
 */
class GoogleTranslator : ITranslator {

    private val modelManager = RemoteModelManager.getInstance()
    private lateinit var translator: Translator

    /**
     * Translates the given text from the source language to the target language.
     *
     * @param text The text to be translated.
     * @param sourceLanguage The language code of the source language.
     * @param targetLanguage The language code of the target language.
     * @param callback Callback function to handle the translation result.
     */
    override fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        callback: (translateResult: TranslateResult) -> Unit
    ) {
        val sourceLang = TranslateLanguage.fromLanguageTag(sourceLanguage) ?: ""
        val targetLang = TranslateLanguage.fromLanguageTag(targetLanguage) ?: ""

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLang)
            .setTargetLanguage(targetLang)
            .build()
        translator = Translation.getClient(options)

        translator.translate(text)
            .addOnSuccessListener { translatedText ->
                callback(TranslateResult.Success(translatedText))
            }
            .addOnFailureListener { exception ->
                callback(TranslateResult.Error(exception))
            }
    }

    /**
     * Checks if the translation model for the given language code needs to be downloaded.
     *
     * @param langCode The language code.
     * @param callback Callback function to handle the result.
     */
    override fun requiresModelDownload(
        langCode: String,
        callback: (requiresModelDownloadResult: RequiresModelDownloadResult) -> Unit
    ) {
        val model = TranslateRemoteModel.Builder(langCode).build()
        modelManager.isModelDownloaded(model)
            .addOnSuccessListener { modelExist ->
                if (!modelExist) callback(RequiresModelDownloadResult.Required)
                else callback(RequiresModelDownloadResult.NotRequired)
            }
            .addOnFailureListener { exception ->
                callback(RequiresModelDownloadResult.Error(exception))
            }
    }

    /**
     * Downloads the translation model for the given language code.
     *
     * @param langCode The language code.
     * @param requireWifi Indicates whether to require Wi-Fi for the download.
     * @param callback Callback function to handle the result.
     */
    override fun downloadModel(
        langCode: String,
        requireWifi: Boolean,
        callback: (downloadResult: DownloadModelResult) -> Unit
    ) {
        val languageTag = TranslateLanguage.fromLanguageTag(langCode) ?: ""
        val model = TranslateRemoteModel.Builder(languageTag).build()
        val conditionBuilder = DownloadConditions.Builder()
        if (requireWifi) {
            conditionBuilder.requireWifi()
        }
        val conditions = conditionBuilder.build()

        modelManager.download(model, conditions)
            .addOnSuccessListener {
                callback(DownloadModelResult.Success)
            }
            .addOnFailureListener { exception ->
                callback(DownloadModelResult.Error(exception))
            }
    }

    /**
     * Deletes the downloaded translation model for the given language code.
     *
     * @param langCode The language code.
     * @param callback Callback function to handle the result.
     */
    override fun deleteModel(
        langCode: String,
        callback: (downloadResult: DeleteModelResult) -> Unit
    ) {
        val languageTag = TranslateLanguage.fromLanguageTag(langCode) ?: ""
        val model = TranslateRemoteModel.Builder(languageTag).build()

        modelManager.deleteDownloadedModel(model)
            .addOnSuccessListener {
                callback(DeleteModelResult.Success)
            }
            .addOnFailureListener { exception ->
                callback(DeleteModelResult.Error(exception))
            }
    }

    /**
     * Closes the translator instance.
     */
    override fun close() {
        if (::translator.isInitialized)
            translator.close()
    }
}