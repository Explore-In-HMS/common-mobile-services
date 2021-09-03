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
import com.huawei.hmf.tasks.Task
import com.huawei.hms.mlsdk.common.MLApplication
import com.huawei.hms.mlsdk.langdetect.MLLangDetectorFactory
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector
import com.huawei.hms.mlsdk.translate.MLTranslatorFactory
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslateSetting
import com.hms.lib.commonmobileservices.translate.manager.ITranslateAPI
import com.hms.lib.commonmobileservices.core.ResultData

class HuaweiTranslate : ITranslateAPI {

    override fun performTranslate(
        callback: (translateValue: ResultData<String>) -> Unit,
        translatingText: String,
        targetLanguageCode: String,
        activity: Activity,
        apiKey:String
    ) {
        var sourceLanguageCode = "tr"
        MLApplication.getInstance().apiKey = apiKey

        val mlRemoteLangDetect: MLRemoteLangDetector =
            MLLangDetectorFactory.getInstance().remoteLangDetector
        val firstBestDetectTask: Task<String> =
            mlRemoteLangDetect.firstBestDetect(translatingText)

        firstBestDetectTask.addOnSuccessListener {
            sourceLanguageCode = it
        }
        val translateSetting =
            MLRemoteTranslateSetting.Factory()
                .setSourceLangCode(sourceLanguageCode)
                .setTargetLangCode(targetLanguageCode)
                .create()
        val mlRemoteTranslator =
            MLTranslatorFactory.getInstance().getRemoteTranslator(translateSetting)

        val task = mlRemoteTranslator?.asyncTranslate(translatingText)
        task?.addOnSuccessListener { translatedText ->
            callback.invoke(ResultData.Success(translatedText))
        }?.addOnFailureListener {
            callback.invoke(ResultData.Failed())
        }
    }

}