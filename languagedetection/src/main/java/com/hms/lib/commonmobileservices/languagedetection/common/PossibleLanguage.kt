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
package com.hms.lib.commonmobileservices.languagedetection.common

/**
 * Data class representing a possible language detected during language identification.
 * @param langCode The language code representing the detected language.
 * @param confidence The confidence score indicating the level of confidence in the detection result.
 */
data class PossibleLanguage(
    val langCode: String,
    val confidence: Float
)
