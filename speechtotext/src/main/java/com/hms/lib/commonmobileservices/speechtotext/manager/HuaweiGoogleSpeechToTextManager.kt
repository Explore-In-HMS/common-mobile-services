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

package com.hms.lib.commonmobileservices.speechtotext.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultData

/**
 * Manager class for performing speech-to-text operations using Huawei or Google services.
 *
 * @property context The context used for initializing the manager.
 */
class HuaweiGoogleSpeechToTextManager(context: Context) {
    private var speechToTextService: ISpeechToTextAPI? = null

    /**
     * Initializes the manager with the appropriate speech-to-text service based on the mobile service type.
     *
     * @param context The context used for initialization.
     */
    init {
        speechToTextService = SpeechToTextFactory().getMLService(Device.getMobileServiceType(context, MobileServiceType.HMS))!!
    }

    /**
     * Performs speech-to-text conversion.
     *
     * @param activity The activity context.
     * @param recordAudioResultCode The result code for recording audio.
     * @param languageCode The language code for the speech.
     * @param hmsApiKey The API key for Huawei services.
     */
    fun performSpeechToText(activity: Activity, recordAudioResultCode: Int, languageCode: String, hmsApiKey: String) {
        speechToTextService?.performSpeechToText(activity, recordAudioResultCode, languageCode, hmsApiKey)
    }

    /**
     * Parses the speech-to-text result data.
     *
     * @param callback Callback function to handle the parsed result.
     * @param activity The activity context.
     * @param data The intent data containing the speech-to-text result.
     * @param resultCode The result code for the operation.
     */
    fun parseSpeechToTextData(callback: (speechToTextResult: ResultData<String>) -> Unit, activity: Activity, data: Intent, resultCode: Int) {
        speechToTextService?.parseSpeechToTextData(callback, activity, data, resultCode)
    }
}