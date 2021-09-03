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
import android.speech.RecognizerIntent
import android.widget.Toast
import com.hms.lib.commonmobileservices.speechtotext.manager.ISpeechToTextAPI
import com.hms.lib.commonmobileservices.core.ResultData
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions

class GoogleSpeechToTextKit :
    ISpeechToTextAPI {

    override fun performSpeechToText(
        activity: Activity,
        recordAudioResultCode: Int,
        languageCode: String,
        hmsApiKey:String
    ) {
        activity.runWithPermissions(Manifest.permission.RECORD_AUDIO) {
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
        }
    }

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