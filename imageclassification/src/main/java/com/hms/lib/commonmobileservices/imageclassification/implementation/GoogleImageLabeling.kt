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
package com.hms.lib.commonmobileservices.imageclassification.implementation

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.hms.lib.commonmobileservices.core.ErrorModel
import com.hms.lib.commonmobileservices.imageclassification.common.ClassificationResult
import com.hms.lib.commonmobileservices.imageclassification.common.ImageLabel

/**
 * A class implementing image classification using Google's ML Kit Image Labeling API.
 *
 * This class implements the [IImageClassification] interface, providing methods to analyze images
 * and obtain classification results using Google's ML Kit Image Labeling API.
 *
 * @property imageLabeler The ImageLabeler instance used for labeling images.
 */
class GoogleImageLabeling(
    private val imageLabeler: ImageLabeler
) : IImageClassification {
    /**
     * Analyzes the provided bitmap image and returns the classification results asynchronously.
     *
     * @param bitmap The bitmap image to be analyzed.
     * @param callback A callback function to receive the classification result asynchronously.
     * The result is encapsulated within a [ClassificationResult] object containing a list of [ImageLabel]s.
     */
    override fun analyseImage(
        bitmap: Bitmap,
        callback: (classificationResult: ClassificationResult<List<ImageLabel>>) -> Unit
    ) {
        val image = InputImage.fromBitmap(bitmap, 0)
        imageLabeler.process(image)
            .addOnSuccessListener { labels ->
                callback(
                    ClassificationResult.Success(
                        labels.map {
                            ImageLabel(
                                name = it.text,
                                possibility = it.confidence
                            )
                        }
                    )
                )
            }
            .addOnFailureListener { e ->
                callback(
                    ClassificationResult.Error(
                        errorMessage = e.localizedMessage,
                        errorModel = ErrorModel(
                            message = e.message,
                            exception = e
                        )
                    )
                )
            }
    }

    /**
     * Stops the image classification analyzer.
     * This method is not implemented for Google's ML Kit Image Labeling API.
     */
    override fun stopAnalyzer() {}
}