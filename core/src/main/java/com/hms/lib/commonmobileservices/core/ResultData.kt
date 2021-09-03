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


sealed class ResultData<out T>{
    data class Loading(val nothing: Nothing? = null): ResultData<Nothing>()
    data class Success<out T>(val data: T? = null): ResultData<T>()
    data class Failed(val error: String? = null,val errorModel: ErrorModel?=null): ResultData<Nothing>()
}

fun <T : Any, R : Any> ResultData<T>.handleSuccess(
    successBlock:
        (dataHolder: ResultData.Success<T>) -> R?
): ResultData<R> = when (this) {
    is ResultData.Success<T> -> ResultData.Success(successBlock.invoke(this))
    is ResultData.Failed -> this
    is ResultData.Loading -> this
}

suspend fun <T : Any, R : Any> ResultData<T>.handleSuccessSuspend(
        successBlock:
        suspend (dataHolder: ResultData.Success<T>) -> R?
): ResultData<R> = when (this) {
    is ResultData.Success<T> -> ResultData.Success(successBlock.invoke(this))
    is ResultData.Failed -> this
    is ResultData.Loading -> this
}

open class ErrorModel(
    var message: String?="Error",
    var code: Int?=0,
    var exception: Exception?=null
    )