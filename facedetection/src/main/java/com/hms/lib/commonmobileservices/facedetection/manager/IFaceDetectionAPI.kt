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

package com.hms.lib.commonmobileservices.facedetection.manager

import android.app.Activity
import android.graphics.Bitmap
import com.hms.lib.commonmobileservices.core.ResultData

/**
 * Interface representing a face detection API.
 */
interface IFaceDetectionAPI {

    /**
     * Performs face detection on the provided bitmap image.
     * @param callback A callback function to handle the result of the face detection operation.
     * @param activity The activity context for displaying UI elements during the face detection process.
     * @param bitmap The bitmap image on which face detection is to be performed.
     * @param apiKey The API key required for accessing the face detection service.
     */
    fun faceDetection(
        callback: (detectedValue: ResultData<List<Any>>) -> Unit,
        activity: Activity,
        bitmap: Bitmap,
        apiKey: String
    )
}