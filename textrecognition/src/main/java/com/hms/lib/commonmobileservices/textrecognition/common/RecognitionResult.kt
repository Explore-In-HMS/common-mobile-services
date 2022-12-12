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
package com.hms.lib.commonmobileservices.textrecognition.common

import com.hms.lib.commonmobileservices.core.ErrorModel

sealed class RecognitionResult<out T> {
    data class Success<out T>(
        val data: T
    ) : RecognitionResult<T>()

    data class Error(
        val errorMessage: String? = null,
        val errorModel: ErrorModel? = null
    ) : RecognitionResult<Nothing>()
}
