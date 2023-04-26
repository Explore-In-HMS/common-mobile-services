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
package com.hms.lib.commonmobileservices.translate.implementation

import com.hms.lib.commonmobileservices.translate.common.DeleteModelResult
import com.hms.lib.commonmobileservices.translate.common.DownloadModelResult
import com.hms.lib.commonmobileservices.translate.common.RequiresModelDownloadResult
import com.hms.lib.commonmobileservices.translate.common.TranslateResult

interface ITranslator {
    fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        callback: (
            translateResult: TranslateResult
        ) -> Unit
    )
    fun requiresModelDownload(
        langCode: String,
        callback: (
            requiresModelDownloadResult: RequiresModelDownloadResult
        ) -> Unit
    )
    fun downloadModel(
        langCode: String,
        requireWifi: Boolean = true,
        callback: (
            downloadResult: DownloadModelResult
        ) -> Unit
    )
    fun deleteModel(
        langCode: String,
        callback: (
            downloadResult: DeleteModelResult
        ) -> Unit
    )
    fun close()
}