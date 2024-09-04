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
import com.huawei.hms.mlsdk.model.download.MLLocalModelManager
import com.huawei.hms.mlsdk.model.download.MLModelDownloadListener
import com.huawei.hms.mlsdk.model.download.MLModelDownloadStrategy
import com.huawei.hms.mlsdk.translate.MLTranslatorFactory
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslateSetting
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslator
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslatorModel

/**
 * Implementation of the [ITranslator] interface for Huawei Translator.
 */
class HuaweiTranslator : ITranslator {

    private val modelManager = MLLocalModelManager.getInstance()
    private lateinit var translator: MLLocalTranslator

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
        val setting = MLLocalTranslateSetting.Factory()
            .setSourceLangCode(sourceLanguage)
            .setTargetLangCode(targetLanguage)
            .create()
        translator = MLTranslatorFactory.getInstance().getLocalTranslator(setting)

        translator.asyncTranslate(text)
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
        val model = MLLocalTranslatorModel.Factory(langCode).create()
        modelManager.isModelExist(model)
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
        val model = MLLocalTranslatorModel.Factory(langCode).create()
        val downloadStrategyFactory = MLModelDownloadStrategy.Factory()
        if (requireWifi) {
            downloadStrategyFactory.needWifi()
        }

        val downloadStrategy = downloadStrategyFactory.create()
        val modelDownloadListener = MLModelDownloadListener { _, _ -> }

        modelManager.downloadModel(model, downloadStrategy, modelDownloadListener)
            .addOnSuccessListener {
                callback(DownloadModelResult.Success)
            }.addOnFailureListener { exception ->
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
        val model = MLLocalTranslatorModel.Factory(langCode).create()
        modelManager.deleteModel(model)
            .addOnSuccessListener {
                callback(DeleteModelResult.Success)
            }.addOnFailureListener { exception ->
                callback(DeleteModelResult.Error(exception))
            }
    }

    /**
     * Stops the translator.
     */
    override fun close() {
        if (::translator.isInitialized)
            translator.stop()
    }
}