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
package com.hms.lib.commonmobileservices.textrecognition.manager

import android.app.Activity
import android.graphics.Bitmap
import com.google.mlkit.vision.text.Text
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.textrecognition.common.RecognitionResult

interface ITextRecognitionAPI {

    fun textRecognition(
        activity: Activity,
        bitmap: Bitmap,
        callback: (recognizedValue: RecognitionResult<Any>) -> Unit
        ) {

    }
}