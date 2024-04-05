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
package com.hms.lib.commonmobileservices.translate.common

/**
 * Sealed interface representing the result of a translation operation.
 * It can indicate a successful translation or an error occurred during the process.
 */
sealed interface TranslateResult {
    /**
     * Represents a successful translation result.
     *
     * @property translatedText The translated text.
     */
    data class Success(val translatedText: String) : TranslateResult

    /**
     * Represents an error that occurred during the translation process,
     * encapsulating the exception that occurred.
     *
     * @property exception The exception that occurred during the translation process.
     */
    data class Error(val exception: Exception) : TranslateResult
}