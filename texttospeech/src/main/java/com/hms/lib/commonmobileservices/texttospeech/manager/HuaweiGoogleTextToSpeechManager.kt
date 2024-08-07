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

package com.hms.lib.commonmobileservices.texttospeech.manager

import android.app.Activity
import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType

/**
 * Manager class for handling text-to-speech functionality with Huawei and Google services.
 */
class HuaweiGoogleTextToSpeechManager(context: Context) {

    private var textToSpeechService: ITextToSpeechAPI? = null

    init {
        // Initialize the text-to-speech service based on the mobile service type
        textToSpeechService = TextToSpeechFactory().getMLService(
            Device.getMobileServiceType(context, MobileServiceType.HMS)
        )!!
    }

    /**
     * Runs the text-to-speech functionality.
     *
     * @param text The text to be converted to speech.
     * @param activity The activity context.
     * @param apiKey The API key required for the service.
     * @param languageCode The language code for the speech.
     * @param personType The type of voice for the speech.
     */
    fun runTextToSpeech(
        text: String,
        activity: Activity,
        apiKey: String,
        languageCode: String,
        personType: String
    ) {
        textToSpeechService?.runTextToSpeech(text, activity, apiKey, languageCode, personType)
    }

    /**
     * Stops the text-to-speech functionality.
     */
    fun stopTextToSpeech() {
        textToSpeechService?.stopTextToSpeech()
    }
}