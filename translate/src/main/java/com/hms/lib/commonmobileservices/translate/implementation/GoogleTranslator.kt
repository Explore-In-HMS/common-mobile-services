package com.hms.lib.commonmobileservices.translate.implementation

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.*
import com.hms.lib.commonmobileservices.translate.common.DeleteModelResult
import com.hms.lib.commonmobileservices.translate.common.DownloadModelResult
import com.hms.lib.commonmobileservices.translate.common.RequiresModelDownloadResult
import com.hms.lib.commonmobileservices.translate.common.TranslateResult

class GoogleTranslator : ITranslator {

    private val modelManager = RemoteModelManager.getInstance()
    private lateinit var translator: Translator

    override fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        callback: (translateResult: TranslateResult) -> Unit
    ) {
        val sourceLang = TranslateLanguage.fromLanguageTag(sourceLanguage) ?: ""
        val targetLanguage = TranslateLanguage.fromLanguageTag(targetLanguage) ?: ""

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLang)
            .setTargetLanguage(targetLanguage)
            .build()
        translator = Translation.getClient(options)

        translator.translate(text)
            .addOnSuccessListener { translatedText ->
                callback(TranslateResult.Success(translatedText))
            }
            .addOnFailureListener { exception ->
                callback(TranslateResult.Error(exception))
            }
    }

    override fun requiresModelDownload(
        langCode: String,
        callback: (requiresModelDownloadResult: RequiresModelDownloadResult) -> Unit
    ) {
        val model = TranslateRemoteModel.Builder(langCode).build()
        modelManager.isModelDownloaded(model)
            .addOnSuccessListener { modelExist ->
                if (!modelExist) callback(RequiresModelDownloadResult.Required)
                else callback(RequiresModelDownloadResult.NotRequired)
            }
            .addOnFailureListener { exception ->
                callback(RequiresModelDownloadResult.Error(exception))
            }
    }

    override fun downloadModel(
        langCode: String,
        requireWifi: Boolean,
        callback: (downloadResult: DownloadModelResult) -> Unit
    ) {
        val languageTag = TranslateLanguage.fromLanguageTag(langCode) ?: ""
        val model = TranslateRemoteModel.Builder(languageTag).build()
        val conditionBuilder = DownloadConditions.Builder()
        if (requireWifi){
            conditionBuilder.requireWifi()
        }
        val conditions = conditionBuilder.build()

        modelManager.download(model, conditions)
            .addOnSuccessListener {
                callback(DownloadModelResult.Success)
            }
            .addOnFailureListener { exception ->
                callback(DownloadModelResult.Error(exception))
            }
    }

    override fun deleteModel(
        langCode: String,
        callback: (downloadResult: DeleteModelResult) -> Unit
    ) {
        val languageTag = TranslateLanguage.fromLanguageTag(langCode) ?: ""
        val model = TranslateRemoteModel.Builder(languageTag).build()

        modelManager.deleteDownloadedModel(model)
            .addOnSuccessListener {
                callback(DeleteModelResult.Success)
            }
            .addOnFailureListener { exception ->
                callback(DeleteModelResult.Error(exception))
            }
    }

    override fun close() {
        if (::translator.isInitialized)
            translator.close()
    }
}