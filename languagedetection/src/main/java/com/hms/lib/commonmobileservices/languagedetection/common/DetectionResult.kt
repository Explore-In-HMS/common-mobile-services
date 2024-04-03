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

import com.hms.lib.commonmobileservices.core.ErrorModel

/**
 * Sealed class representing the result of a detection operation.
 * @param T The type of data contained in the result.
 */
sealed class DetectionResult<out T> {

    /**
     * Represents a successful detection result.
     * @property data The data resulting from the detection operation.
     */
    data class Success<out T>(
        val data: T
    ) : DetectionResult<T>()

    /**
     * Represents an error that occurred during the detection operation.
     * @property errorMessage The error message describing the failure.
     * @property errorModel An optional error model providing additional details about the failure.
     */
    data class Error(
        val errorMessage: String? = null,
        val errorModel: ErrorModel? = null
    ) : DetectionResult<Nothing>()
}
