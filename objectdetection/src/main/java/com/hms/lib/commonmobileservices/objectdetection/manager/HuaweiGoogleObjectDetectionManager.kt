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
import android.content.Context
import android.graphics.Bitmap
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultData

/**
 * Class responsible for managing object detection using Huawei or Google ML services.
 */
class HuaweiGoogleObjectDetectionManager(context: Context) {
    private var objectDetectionService: IObjectDetectionAPI? = null

    /**
     * Initializes the object detection service based on the mobile service type.
     */
    init {
        objectDetectionService = ObjectDetectionFactory().getMLService(
            Device.getMobileServiceType(
                context,
                MobileServiceType.HMS
            )
        )
    }

    /**
     * Performs static image detection using the selected ML service.
     * @param callback Callback function to handle the detection result.
     * @param activity The activity context.
     * @param bitmap The input image bitmap.
     * @param apiKey The API key for Google ML service.
     */
    fun staticImageDetection(
        callback: (detectedValue: ResultData<List<Any>>) -> Unit,
        activity: Activity,
        bitmap: Bitmap,
        apiKey: String
    ) {
        objectDetectionService?.staticImageDetection(callback, activity, bitmap, apiKey)
    }
}