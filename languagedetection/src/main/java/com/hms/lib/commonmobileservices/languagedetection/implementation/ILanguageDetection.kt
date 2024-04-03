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
package com.hms.lib.commonmobileservices.languagedetection.implementation

import com.hms.lib.commonmobileservices.languagedetection.common.DetectionResult
import com.hms.lib.commonmobileservices.languagedetection.common.PossibleLanguage

/**
 * Interface defining language detection functionalities.
 */
interface ILanguageDetection {

    /**
     * Detects the language of the provided source text.
     * @param sourceText The text for which language detection is performed.
     * @param callback A callback function to handle the detection result.
     */
    fun detectLanguage(
        sourceText: String,
        callback: (detectResult: DetectionResult<String>) -> Unit
    )

    /**
     * Detects possible languages of the provided source text along with their confidence levels.
     * @param sourceText The text for which language detection is performed.
     * @param callback A callback function to handle the detection result.
     */
    fun detectPossibleLanguages(
        sourceText: String,
        callback: (detectResult: DetectionResult<List<PossibleLanguage>>) -> Unit
    )

    /**
     * Stops the language detector.
     */
    fun stopDetector()
}