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
import com.hms.lib.commonmobileservices.imageclassification.common.ClassificationResult
import com.hms.lib.commonmobileservices.imageclassification.common.ImageLabel

/**
 * An interface representing image classification functionality.
 * Implementations of this interface provide methods for analyzing images and obtaining classification results.
 */
interface IImageClassification {
    /**
     * Analyzes the provided bitmap image and returns the classification results asynchronously.
     *
     * @param bitmap The bitmap image to be analyzed.
     * @param callback A callback function to receive the classification result asynchronously.
     * The result is encapsulated within a [ClassificationResult] object containing a list of [ImageLabel]s.
     */
    fun analyseImage(
        bitmap: Bitmap,
        callback: (classificationResult: ClassificationResult<List<ImageLabel>>) -> Unit
    )

    /**
     * Stops the image classification analyzer.
     * This method should be called when image classification is no longer needed to release resources.
     */
    fun stopAnalyzer()
}