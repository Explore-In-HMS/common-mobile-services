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
package com.hms.lib.commonmobileservices.core

/**
 * A callback interface for handling asynchronous results.
 *
 * @param T The type of result returned by the asynchronous operation.
 */
interface ResultCallback<T> {
    /**
     * Called when the asynchronous operation succeeds.
     *
     * @param result The result of the asynchronous operation, if any. Defaults to null.
     */
    fun onSuccess(result: T? = null)

    /**
     * Called when the asynchronous operation fails.
     *
     * @param error The exception that occurred during the operation.
     */
    fun onFailure(error: Exception)

    /**
     * Called when the asynchronous operation is cancelled.
     */
    fun onCancelled()
}