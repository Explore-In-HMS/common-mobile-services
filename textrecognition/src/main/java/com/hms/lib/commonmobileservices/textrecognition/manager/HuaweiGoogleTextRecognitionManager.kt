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
import android.content.Context
import android.graphics.Bitmap
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.textrecognition.common.RecognitionResult

/**
 * Manages text recognition using Huawei or Google text recognition services based on the mobile service type.
 */
class HuaweiGoogleTextRecognitionManager(context: Context) {
    private var textRecognitionService: ITextRecognitionAPI? = null

    init {
        // Get the appropriate text recognition service based on the mobile service type
        textRecognitionService = TextRecognitionFactory().getMLService(
            Device.getMobileServiceType(context, MobileServiceType.HMS)
        )
    }

    /**
     * Performs text recognition on the provided bitmap image.
     *
     * @param bitmap The bitmap image containing the text to be recognized.
     * @param callback Callback to receive the recognition result.
     */
    fun textRecognition(
        bitmap: Bitmap,
        callback: (recognizedValue: RecognitionResult<Any>) -> Unit
    ) {
        // Delegate text recognition to the appropriate service
        textRecognitionService?.textRecognition(bitmap, callback)
    }
}