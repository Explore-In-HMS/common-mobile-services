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

import android.app.Activity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.translate.manager.ITranslateAPI

class GoogleTranslate : ITranslateAPI {

    override fun performTranslate(
        callback: (translateValue: ResultData<String>) -> Unit,
        translatingText: String,
        targetLanguageCode: String,
        activity: Activity,
        apiKey: String
    ) {

        val sourceLanguageCode = "tr"

        val translateSetting = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguageCode)
            .setTargetLanguage(targetLanguageCode)
            .build()
        val mlTranslateGoogle = Translation.getClient(translateSetting)

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        mlTranslateGoogle.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                mlTranslateGoogle.translate(translatingText)
                    .addOnSuccessListener {
                        val task = mlTranslateGoogle.translate(translatingText)
                        task.addOnSuccessListener { translatedText ->
                            callback.invoke(ResultData.Success(translatedText))
                        }.addOnFailureListener {
                            callback.invoke(ResultData.Failed())
                        }
                    }
                    .addOnFailureListener {
                        callback.invoke(ResultData.Failed())
                    }
            }
            .addOnFailureListener {
                callback.invoke(ResultData.Failed())
            }

    }
}