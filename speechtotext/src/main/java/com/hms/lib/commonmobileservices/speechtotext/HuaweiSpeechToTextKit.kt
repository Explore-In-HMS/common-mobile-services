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
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.huawei.hms.mlplugin.asr.MLAsrCaptureActivity
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants
import com.hms.lib.commonmobileservices.speechtotext.manager.ISpeechToTextAPI
import com.hms.lib.commonmobileservices.core.ResultData
import com.huawei.hms.mlsdk.common.MLApplication

/**
 * Implementation of ISpeechToTextAPI for Huawei Mobile Services (HMS).
 */
class HuaweiSpeechToTextKit : ISpeechToTextAPI {

    /**
     * Initiates the speech-to-text process.
     *
     * @param activity The activity context.
     * @param recordAudioResultCode The code to be used for the result of the speech-to-text operation.
     * @param languageCode The language code for speech recognition.
     * @param hmsApiKey The API key required for speech recognition in Huawei's ML Kit (unused in HMS implementation).
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
            MLApplication.getInstance().apiKey = hmsApiKey
            val intent = Intent(activity, MLAsrCaptureActivity::class.java)
                .putExtra(MLAsrCaptureConstants.LANGUAGE, languageCode) // Example: "zh-CN": Chinese; "en-US": English; "fr-FR": French; "es-ES": Spanish; "de-DE": German; "it-IT": Italian
                .putExtra(MLAsrCaptureConstants.FEATURE, MLAsrCaptureConstants.FEATURE_WORDFLUX)
            activity.startActivityForResult(intent, recordAudioResultCode)
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
        when (resultCode) {
            MLAsrCaptureConstants.ASR_SUCCESS -> {
                val bundle = data.extras
                if (bundle != null && bundle.containsKey(MLAsrCaptureConstants.ASR_RESULT)) {
                    val textData = bundle.getString(MLAsrCaptureConstants.ASR_RESULT).toString()
                    callback.invoke(ResultData.Success(textData))
                }
            }
            MLAsrCaptureConstants.ASR_FAILURE -> callback.invoke(ResultData.Failed())
        }
    }
}