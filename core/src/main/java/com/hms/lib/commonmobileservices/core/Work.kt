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

class Work<T> {
    private var successListener: ((result: T) -> Unit)? = null
    private var failureListener: ((error: Exception) -> Unit)? = null
    private var canceledListener: (() -> Unit)? = null

    fun onSuccess(result: T) {
        successListener?.run { this(result!!) }
    }

    fun onFailure(error: Exception) {
        failureListener?.run { this(error) }
    }

    fun onCanceled() {
        canceledListener?.run { this() }
    }

    fun addOnSuccessListener(listener: (result: T) -> Unit): Work<T> {
        successListener = listener
        return this
    }

    fun addOnFailureListener(listener: (error: Exception) -> Unit): Work<T> {
        failureListener = listener
        return this
    }

    fun addOnCanceledListener(listener: () -> Unit): Work<T> {
        canceledListener = listener
        return this
    }
}