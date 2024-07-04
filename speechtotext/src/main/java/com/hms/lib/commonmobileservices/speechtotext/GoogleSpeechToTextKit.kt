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

package com.hms.lib.commonmobileservices.speechtotext

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.hms.lib.commonmobileservices.speechtotext.manager.ISpeechToTextAPI
import com.hms.lib.commonmobileservices.core.ResultData

/**
 * Implementation of ISpeechToTextAPI for Google Mobile Services (GMS).
 */
class GoogleSpeechToTextKit : ISpeechToTextAPI {
    /**
     * Initiates the speech-to-text process.
     *
     * @param activity The activity context.
     * @param recordAudioResultCode The code to be used for the result of the speech-to-text operation.
     * @param languageCode The language code for speech recognition.
     * @param hmsApiKey The API key required for speech recognition (unused in GMS implementation).
     */
    override fun performSpeechToText(
        activity: Activity,
        recordAudioResultCode: Int,
        languageCode: String,
        hmsApiKey: String
    ) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            sttIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

            try {
                activity.startActivityForResult(sttIntent, recordAudioResultCode)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast.makeText(activity, "Your device does not support STT.", Toast.LENGTH_LONG).show()
            }
        } else {
            val strings = arrayOf(
                Manifest.permission.RECORD_AUDIO,
            )
            ActivityCompat.requestPermissions(activity, strings, 2)
        }
    }

    /**
     * Parses the speech-to-text result data.
     *
     * @param callback Callback to receive the parsed speech-to-text result.
     * @param activity The activity context.
     * @param data The intent data containing the speech-to-text result.
     * @param resultCode The result code of the speech-to-text operation.
     */
    override fun parseSpeechToTextData(
        callback: (speechToTextResult: ResultData<String>) -> Unit,
        activity: Activity,
        data: Intent,
        resultCode: Int
    ) {
        val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        result?.let {
            val recognizedText = it[0]
            callback.invoke(ResultData.Success(recognizedText))
        }
    }
}