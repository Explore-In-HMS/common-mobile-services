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
 * A sealed class representing different states of asynchronous operations.
 *
 * @param T The type of data contained in the result.
 */
sealed class ResultData<out T> {
    /**
     * Represents a loading state.
     *
     * @property nothing Represents that there is no data associated with the loading state. Defaults to null.
     */
    data class Loading(val nothing: Nothing? = null) : ResultData<Nothing>()

    /**
     * Represents a successful state.
     *
     * @property data The data associated with the successful state, if any. Defaults to null.
     */
    data class Success<out T>(val data: T? = null) : ResultData<T>()

    /**
     * Represents a failed state.
     *
     * @property error A string describing the error, if any. Defaults to null.
     * @property errorModel An optional error model providing additional information about the error. Defaults to null.
     */
    data class Failed(val error: String? = null, val errorModel: ErrorModel? = null) :
        ResultData<Nothing>()
}

/**
 * Handles the success state of a ResultData instance.
 *
 * @param T The type of data contained in the original ResultData.
 * @param R The type of data to be returned after handling the success state.
 * @param successBlock The block of code to be executed when the original ResultData is in a success state.
 * @return A new ResultData instance representing the result of handling the success state.
 */
fun <T : Any, R : Any> ResultData<T>.handleSuccess(
    successBlock: (dataHolder: ResultData.Success<T>) -> R?
): ResultData<R> = when (this) {
    is ResultData.Success<T> -> ResultData.Success(successBlock.invoke(this))
    is ResultData.Failed -> this
    is ResultData.Loading -> this
}

/**
 * Represents additional information about an error.
 *
 * @property message A string describing the error. Defaults to "Error".
 * @property code An integer code representing the error. Defaults to 0.
 * @property exception An optional exception associated with the error. Defaults to null.
 */
open class ErrorModel(
    var message: String? = "Error",
    var code: Int? = 0,
    var exception: Exception? = null
)

/**
 * Handles the success state of a suspending ResultData instance.
 *
 * @param T The type of data contained in the original ResultData.
 * @param R The type of data to be returned after handling the success state.
 * @param successBlock The block of code to be executed when the original ResultData is in a success state.
 * @return A new ResultData instance representing the result of handling the success state.
 */
suspend fun <T : Any, R : Any> ResultData<T>.handleSuccessSuspend(
    successBlock: suspend (dataHolder: ResultData.Success<T>) -> R?
): ResultData<R> = when (this) {
    is ResultData.Success<T> -> ResultData.Success(successBlock.invoke(this))
    is ResultData.Failed -> this
    is ResultData.Loading -> this
}