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
 * Sealed interface representing the result of a model download operation.
 * It can either indicate a successful download or an error with an associated exception.
 */
sealed interface DownloadModelResult {
    /**
     * Represents a successful download operation.
     */
    object Success : DownloadModelResult

    /**
     * Represents an error that occurred during the download operation,
     * encapsulating the exception that occurred.
     *
     * @property exception The exception that occurred during the download operation.
     */
    data class Error(val exception: Exception) : DownloadModelResult
}