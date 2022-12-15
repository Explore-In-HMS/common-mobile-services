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
import com.hms.lib.commonmobileservices.texttospeech.manager.ITextToSpeechAPI
import com.huawei.hms.mlsdk.common.MLApplication
import com.huawei.hms.mlsdk.tts.MLTtsConfig
import com.huawei.hms.mlsdk.tts.MLTtsEngine

class HuaweiTextToSpeechKit : ITextToSpeechAPI {

    companion object {
        private val mLTtsConfig: MLTtsConfig = MLTtsConfig()
        private lateinit var mLTtsEngine: MLTtsEngine
    }

    override fun runTextToSpeech(
        text: String,
        activity: Activity,
        apiKey: String,
        languageCode: String,
        personType: String
    ) {
        MLApplication.getInstance().apiKey = apiKey
        mLTtsConfig.setLanguage(languageCode).setPerson(personType).setSpeed(1.0f).volume = 1.0f
        mLTtsEngine = MLTtsEngine(mLTtsConfig)
        mLTtsEngine.speak(text, MLTtsEngine.QUEUE_FLUSH)
    }

    override fun stopTextToSpeech() {
        mLTtsEngine.stop()
        mLTtsEngine.shutdown()
    }
}