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

package com.hms.lib.commonmobileservices.texttospeech

import android.app.Activity
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.texttospeech.manager.ITextToSpeechAPI
import com.huawei.hms.mlsdk.common.MLApplication
import com.huawei.hms.mlsdk.tts.MLTtsConfig
import com.huawei.hms.mlsdk.tts.MLTtsEngine

class HuaweiTextToSpeechKit : ITextToSpeechAPI {

    private lateinit var mlTtsConfig: MLTtsConfig
    private lateinit var mlTtsEngine: MLTtsEngine

    override fun runTextToSpeech(
        text: String,
        callback: (detectedText: ResultData<String>) -> Unit,
        activity: Activity,
        apiKey: String,
        languageCode: String,
        personType: String,
    ) {
        MLApplication.getInstance().apiKey = apiKey
        mlTtsConfig = MLTtsConfig().setLanguage(languageCode).setPerson(personType).setSpeed(1.0f)
            .setVolume(1.0f)
        mlTtsEngine = MLTtsEngine(mlTtsConfig)
        mlTtsEngine.setPlayerVolume(20)
        mlTtsEngine.updateConfig(mlTtsConfig)
        mlTtsEngine.speak(text, MLTtsEngine.QUEUE_APPEND)
    }

}