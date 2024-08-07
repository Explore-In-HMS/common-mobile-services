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

package com.hms.lib.commonmobileservices.objectdetection.manager

import android.app.Activity
import android.graphics.Bitmap
import com.hms.lib.commonmobileservices.core.ResultData

/**
 * Interface for object detection API.
 */
interface IObjectDetectionAPI {

    /**
     * Performs static image detection.
     * @param callback Callback function to handle the detection result.
     * @param activity The activity context.
     * @param bitmap The input image bitmap.
     * @param apiKey The API key for the ML service.
     */
    fun staticImageDetection(
        callback: (detectedValue: ResultData<List<Any>>) -> Unit,
        activity: Activity,
        bitmap: Bitmap,
        apiKey: String
    )

}