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
import android.util.Log
import com.huawei.hms.mlsdk.classification.MLImageClassificationAnalyzer
import com.huawei.hms.mlsdk.common.MLFrame

class HuaweiImageClassification(
    private val analyzer: MLImageClassificationAnalyzer
): IImageClassification {
    override fun analyseImage(bitmap: Bitmap) {
        val frame = MLFrame.fromBitmap(bitmap)
        analyzer.asyncAnalyseFrame(frame)
            .addOnSuccessListener {
                Log.d("Classification",it.toString())
            }.addOnFailureListener {
                Log.d("Classification",it.localizedMessage ?: "Error")
            }
    }

    override fun stopAnalyzer() {
        analyzer.stop()
    }
}