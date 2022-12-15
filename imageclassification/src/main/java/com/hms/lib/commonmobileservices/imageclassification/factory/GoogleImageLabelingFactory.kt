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
package com.hms.lib.commonmobileservices.imageclassification.factory

import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.hms.lib.commonmobileservices.imageclassification.implementation.GoogleImageLabeling
import com.hms.lib.commonmobileservices.imageclassification.implementation.IImageClassification

class GoogleImageLabelingFactory : ImageClassificationFactory() {
    override fun create(): IImageClassification {
        return GoogleImageLabeling(
            ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        )
    }

    override fun create(confidenceThreshold: Float): IImageClassification {
        val threshold = confidenceThreshold.coerceIn(0.0f, 1.0f)
        val options = ImageLabelerOptions.Builder()
            .setConfidenceThreshold(threshold)
            .build()

        return GoogleImageLabeling(
            ImageLabeling.getClient(options)
        )
    }
}