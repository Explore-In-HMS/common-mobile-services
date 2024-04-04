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
import android.speech.tts.TextToSpeech
import android.util.Log
import com.hms.lib.commonmobileservices.texttospeech.manager.ITextToSpeechAPI
import java.util.*

/**
 * Implementation of the text-to-speech API using Google's text-to-speech service.
 */
class GoogleTextToSpeechKit : ITextToSpeechAPI {

    companion object {
        private var tts: TextToSpeech? = null
    }

    /**
     * Initiates text-to-speech with the provided text.
     *
     * @param text The text to be converted to speech.
     * @param activity The activity context.
     * @param apiKey Not used in Google Text-to-Speech.
     * @param languageCode The language code for the text.
     * @param personType Not used in Google Text-to-Speech.
     */
    override fun runTextToSpeech(
        text: String,
        activity: Activity,
        apiKey: String,
        languageCode: String,
        personType: String
    ) {
        val localeLanguageCode = Locale(languageCode)
        tts = TextToSpeech(activity.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.let {
                    it.language = localeLanguageCode
                    it.setSpeechRate(1.0f)
                    it.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
                }
            } else {
                Log.e("TTS", "Initialization Failed!")
            }
        }
    }

    /**
     * Stops the text-to-speech engine.
     */
    override fun stopTextToSpeech() {
        tts?.shutdown()
    }
}