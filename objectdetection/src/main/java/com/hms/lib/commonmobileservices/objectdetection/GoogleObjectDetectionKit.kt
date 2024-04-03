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

package com.hms.lib.commonmobileservices.objectdetection

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.app.ActivityCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.hms.lib.commonmobileservices.core.ResultData
import com.hms.lib.commonmobileservices.objectdetection.manager.IObjectDetectionAPI

/**
 * Object detection implementation for Google's ML Kit.
 */
class GoogleObjectDetectionKit : IObjectDetectionAPI {

    override fun staticImageDetection(
        callback: (detectedValue: ResultData<List<Any>>) -> Unit,
        activity: Activity,
        bitmap: Bitmap,
        apiKey: String
    ) {
        val strings = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
        ActivityCompat.requestPermissions(activity, strings, 2)

        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val options = ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                .enableMultipleObjects()
                .enableClassification()
                .build()

            val objectDetector = ObjectDetection.getClient(options)

            val image = InputImage.fromBitmap(bitmap, 0)

            objectDetector.process(image).let { result ->
                result.addOnSuccessListener {
                    callback.invoke(ResultData.Success(it))
                }.addOnFailureListener {
                    callback.invoke(ResultData.Failed())
                }
            }

        } else {
            ActivityCompat.requestPermissions(activity, strings, 2)
            callback.invoke(ResultData.Failed("You have to give permission"))
        }
    }
}