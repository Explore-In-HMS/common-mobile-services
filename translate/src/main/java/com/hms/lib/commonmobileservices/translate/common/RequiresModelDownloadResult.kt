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
 * Sealed interface representing the result of a check for model download requirement.
 * It can indicate whether a model download is required, not required, or an error occurred during the process.
 */
sealed interface RequiresModelDownloadResult {
    /**
     * Indicates that a model download is required.
     */
    object Required : RequiresModelDownloadResult

    /**
     * Indicates that no model download is required.
     */
    object NotRequired : RequiresModelDownloadResult

    /**
     * Represents an error that occurred during the model download requirement check,
     * encapsulating the exception that occurred.
     *
     * @property exception The exception that occurred during the model download requirement check.
     */
    data class Error(val exception: Exception) : RequiresModelDownloadResult
}