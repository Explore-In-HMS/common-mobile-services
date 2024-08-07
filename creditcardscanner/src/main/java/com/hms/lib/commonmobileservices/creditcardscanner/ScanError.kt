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
package com.hms.lib.commonmobileservices.creditcardscanner

import com.hms.lib.commonmobileservices.core.ErrorModel

/**
 * A data class representing an error that may occur during a scanning operation.
 *
 * @property errorString A descriptive error message indicating the nature of the error.
 * @property scanErrorType The type of the scanning error, which could be one of [ScanErrorType.USER_CANCELED],
 * [ScanErrorType.ERROR], or [ScanErrorType.DENIED].
 *
 * @constructor Creates a new instance of [ScanError].
 */
data class ScanError(
    val errorString: String,
    val scanErrorType: ScanErrorType = ScanErrorType.ERROR
) : ErrorModel(errorString) {

    /**
     * Enum class representing different types of scanning errors.
     */
    enum class ScanErrorType {
        USER_CANCELED, ERROR, DENIED
    }
}