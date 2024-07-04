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
 * Represents a unit of work that can be executed asynchronously.
 * This class allows attaching success, failure, and cancellation listeners.
 *
 * @param T The type of result produced upon successful execution of the work.
 */
class Work<T> {
    private var successListener: ((result: T) -> Unit)? = null
    private var failureListener: ((error: Exception) -> Unit)? = null
    private var canceledListener: (() -> Unit)? = null

    /**
     * Executes the success listener with the provided result.
     *
     * @param result The result of the successful execution.
     */
    fun onSuccess(result: T) {
        successListener?.run { this(result!!) }
    }

    /**
     * Executes the failure listener with the provided error.
     *
     * @param error The exception representing the failure.
     */
    fun onFailure(error: Exception) {
        failureListener?.run { this(error) }
    }

    /**
     * Executes the cancellation listener.
     */
    fun onCanceled() {
        canceledListener?.run { this() }
    }

    /**
     * Adds a success listener to the work.
     *
     * @param listener The success listener to be added.
     * @return The current instance of Work.
     */
    fun addOnSuccessListener(listener: (result: T) -> Unit): Work<T> {
        successListener = listener
        return this
    }

    /**
     * Adds a failure listener to the work.
     *
     * @param listener The failure listener to be added.
     * @return The current instance of Work.
     */
    fun addOnFailureListener(listener: (error: Exception) -> Unit): Work<T> {
        failureListener = listener
        return this
    }

    /**
     * Adds a cancellation listener to the work.
     *
     * @param listener The cancellation listener to be added.
     * @return The current instance of Work.
     */
    fun addOnCanceledListener(listener: () -> Unit): Work<T> {
        canceledListener = listener
        return this
    }
}