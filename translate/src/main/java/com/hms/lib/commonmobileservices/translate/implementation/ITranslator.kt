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