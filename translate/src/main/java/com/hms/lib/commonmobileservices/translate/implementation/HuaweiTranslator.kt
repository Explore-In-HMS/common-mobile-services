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

class HuaweiTranslator : ITranslator {

    private val modelManager = MLLocalModelManager.getInstance()
    private lateinit var translator: MLLocalTranslator

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

    override fun downloadModel(
        langCode: String,
        requireWifi: Boolean,
        callback: (downloadResult: DownloadModelResult) -> Unit
    ) {
        val model = MLLocalTranslatorModel.Factory(langCode).create()
        val downloadStrategyFactory = MLModelDownloadStrategy.Factory()
        if (requireWifi){
            downloadStrategyFactory.needWifi()
        }
        val downloadStrategy = downloadStrategyFactory.create()
        val modelDownloadListener = MLModelDownloadListener { alreadyDownLength, totalLength -> }

        modelManager.downloadModel(model, downloadStrategy, modelDownloadListener)
            .addOnSuccessListener {
                callback(DownloadModelResult.Success)
            }.addOnFailureListener { exception ->
                callback(DownloadModelResult.Error(exception))
            }
    }

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

    override fun close() {
        if (::translator.isInitialized)
            translator.stop()
    }
}