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

package com.hms.lib.commonmobileservices.textrecognition.implementation

import android.app.Activity
import android.graphics.Bitmap
import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.textrecognition.common.RecognitionResult
import com.hms.lib.commonmobileservices.textrecognition.manager.ITextRecognitionAPI
import com.huawei.hms.mlsdk.MLAnalyzerFactory
import com.huawei.hms.mlsdk.common.MLApplication
import com.huawei.hms.mlsdk.common.MLFrame
import com.huawei.hms.mlsdk.text.MLLocalTextSetting

class HuaweiTextRecognitionKit : ITextRecognitionAPI {
    override fun textRecognition(
        bitmap: Bitmap,
        callback: (recognizedValue: RecognitionResult<Any>) -> Unit
    ) {

        val setting = MLLocalTextSetting.Factory()
            .setOCRMode(MLLocalTextSetting.OCR_DETECT_MODE)
            .create()

        val analyzer = MLAnalyzerFactory.getInstance().getLocalTextAnalyzer(setting)
        val frame = MLFrame.fromBitmap(bitmap)
        val task = analyzer?.asyncAnalyseFrame(frame)

        task?.let { result ->
            result.addOnSuccessListener { mlText ->
                callback.invoke(RecognitionResult.Success(mlText.stringValue))
            }.addOnFailureListener { e ->
                callback.invoke(
                    RecognitionResult.Error(
                        errorMessage = e.localizedMessage,
                        errorModel = ErrorModel(
                            message = e.message,
                            exception = e
                        )
                    )
                )
            }
        } ?: run {
            callback.invoke(RecognitionResult.Error())
        }
    }
}