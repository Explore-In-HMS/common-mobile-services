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

import com.hms.lib.commonmobileservices.imageclassification.implementation.HuaweiImageClassification
import com.hms.lib.commonmobileservices.imageclassification.implementation.IImageClassification
import com.huawei.hms.mlsdk.MLAnalyzerFactory
import com.huawei.hms.mlsdk.classification.MLLocalClassificationAnalyzerSetting

class HuaweiImageClassificationFactory: ImageClassificationFactory() {
    override fun create(): IImageClassification {
        return HuaweiImageClassification(
            MLAnalyzerFactory.getInstance().remoteImageClassificationAnalyzer
        )
    }

    override fun create(confidenceThreshold: Float): IImageClassification {
        val threshold = confidenceThreshold.coerceIn(0.0f,1.0f)
        val setting = MLLocalClassificationAnalyzerSetting.Factory()
            .setMinAcceptablePossibility(threshold)
            .create()

        val analyzer = MLAnalyzerFactory
            .getInstance()
            .getLocalImageClassificationAnalyzer(setting)

        return HuaweiImageClassification(
            analyzer
        )
    }
}