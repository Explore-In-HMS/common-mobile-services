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
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.textrecognition.common.RecognitionResult
import com.hms.lib.commonmobileservices.textrecognition.manager.ITextRecognitionAPI
import com.huawei.hms.mlsdk.common.MLApplication

class GoogleTextRecognitionKit : ITextRecognitionAPI {
    override fun textRecognition(
        activity: Activity,
        bitmap: Bitmap,
        callback: (recognizedValue: RecognitionResult<Any>) -> Unit
    ) {

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)

        val task = recognizer.process(image)
        recognizer.optionalFeatures

        task.let { result ->
            result.addOnSuccessListener { text->
                callback.invoke(RecognitionResult.Success(text.text))
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
        }


    }

}