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
package com.hms.lib.commonmobileservices.languagedetection.factory

import com.hms.lib.commonmobileservices.languagedetection.implementation.HuaweiLanguageDetection
import com.hms.lib.commonmobileservices.languagedetection.implementation.ILanguageDetection
import com.huawei.hms.mlsdk.langdetect.MLLangDetectorFactory
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetectorSetting

class HuaweiLanguageDetectorFactory: LanguageDetectionFactory() {

    override fun create(): ILanguageDetection {
        val mlRemoteLangDetector = MLLangDetectorFactory.getInstance()
            .remoteLangDetector

        return HuaweiLanguageDetection(mlRemoteLangDetector)
    }

    override fun create(confidenceThreshold: Float): ILanguageDetection {
        val threshold = confidenceThreshold.coerceIn(0.0f,1.0f)
        val setting = MLRemoteLangDetectorSetting.Factory()
            .setTrustedThreshold(threshold)
            .create()
        val mlRemoteLangDetector = MLLangDetectorFactory.getInstance()
            .getRemoteLangDetector(setting)

        return HuaweiLanguageDetection(mlRemoteLangDetector)
    }
}