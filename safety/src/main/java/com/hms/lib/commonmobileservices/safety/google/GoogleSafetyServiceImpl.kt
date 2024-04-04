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
package com.hms.lib.commonmobileservices.safety.google

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Base64
import android.util.Log
import com.google.android.gms.safetynet.SafetyNet
import com.hms.lib.commonmobileservices.core.ResultCallback
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.safety.RootDetectionResponse
import com.hms.lib.commonmobileservices.safety.SafetyService
import com.hms.lib.commonmobileservices.safety.SafetyServiceResponse
import com.hms.lib.commonmobileservices.safety.common.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

/**
 * Implementation of the SafetyService interface for Google Mobile Services.
 * This class provides functionality related to safety services offered by Google Mobile Services,
 * such as user detection, root detection, checking for malicious apps, enabling app checks,
 * URL checking, and shutting down URL checks.
 *
 * @param context The application context.
 */
class GoogleSafetyServiceImpl(private val context: Context): SafetyService {

    private val mapper: GoogleSafetyMapper = GoogleSafetyMapper()
    private val rootDetectMapper: Mapper<JSONObject, RootDetectionResponse> = GoogleRootDetectMapper()

    /**
     * Initiates user detection using SafetyNet API.
     *
     * @param appKey The SITE_API_KEY value in Google Mobile Services.
     * @param callback The callback to handle the result of the user detection operation.
     */
    override fun userDetect(appKey: String, callback: ResultCallback<SafetyServiceResponse>) {
        SafetyNet.getClient(context).verifyWithRecaptcha(appKey)
            .addOnSuccessListener { response ->
                val responseToken = response.tokenResult
                if (responseToken != null && responseToken.isNotEmpty()) {
                    callback.onSuccess(mapper.map(response))
                }
            }.addOnFailureListener { exception ->
                callback.onFailure(exception)
            }
    }

    /**
     * Initiates root detection using SafetyNet API.
     *
     * @param appKey The app key for the SafetyNet API.
     * @param callback The callback to handle the result of the root detection operation.
     */
    override fun rootDetection(appKey: String, callback: ResultCallback<RootDetectionResponse>) {
        val nonce = ByteArray(24)
        try {
            val random: SecureRandom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                SecureRandom.getInstanceStrong()
            } else {
                SecureRandom.getInstance("SHA1PRNG")
            }
            random.nextBytes(nonce)
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, e.message!!)
        }

        SafetyNet.getClient(context).attest(nonce, appKey)
            .addOnSuccessListener { result ->
                val jwsStr = result.jwsResult
                val jwsSplit = jwsStr?.split(".")?.toTypedArray()
                val jwsPayloadStr = jwsSplit?.get(1)
                val payloadDetail = String(Base64.decode(jwsPayloadStr?.toByteArray(StandardCharsets.UTF_8), Base64.URL_SAFE), StandardCharsets.UTF_8)
                val jsonObject = JSONObject(payloadDetail)
                callback.onSuccess(rootDetectMapper.map(jsonObject))
            }.addOnFailureListener { exception ->
                callback.onFailure(exception)
            }
    }

    /**
     * Retrieves a list of harmful apps using SafetyNet API.
     *
     * @param callback The callback to handle the result of the malicious apps list operation.
     */
    override fun getMaliciousAppsList(callback: ResultCallback<CommonMaliciousAppResponse>) {
        SafetyNet.getClient(context).listHarmfulApps().addOnSuccessListener {
            callback.onSuccess(it.toCommonMaliciousAppList())
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Checks whether app checks are enabled using SafetyNet API.
     *
     * @param callback The callback to handle the result of the app checks enabled operation.
     */
    override fun isAppChecksEnabled(callback: ResultCallback<CommonVerifyAppChecksEnabledRes>) {
        SafetyNet.getClient(context).isVerifyAppsEnabled.addOnSuccessListener {
            callback.onSuccess(it.toCommonVerifyAppUserEnabled())
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Enables app checks using SafetyNet API.
     *
     * @param callback The callback to handle the result of the enabling app checks operation.
     */
    override fun enableAppsCheck(callback: ResultCallback<CommonVerifyAppChecksEnabledRes>) {
        SafetyNet.getClient(context).enableVerifyApps().addOnSuccessListener {
            callback.onSuccess(it.toCommonVerifyAppUserEnabled())
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Initiates URL check using SafetyNet API.
     *
     * @return A Work object representing the asynchronous operation.
     */
    override fun initURLCheck(): Work<Unit> {
        val worker = Work<Unit>()
        SafetyNet.getClient(context).initSafeBrowsing().addOnSuccessListener {
            worker.onSuccess(Unit)
        }.addOnFailureListener {
            worker.onFailure(it)
        }
        return worker
    }

    /**
     * Performs URL check using SafetyNet API.
     *
     * @param url The URL to be checked.
     * @param appKey The app key for the SafetyNet API.
     * @param threatType The threat type to be checked.
     * @param callback The callback to handle the result of the URL check operation.
     */
    override fun urlCheck(
        url: String,
        appKey: String,
        threatType: Int,
        callback: ResultCallback<CommonUrlCheckRes>
    ) {
        SafetyNet.getClient(context).lookupUri(url, appKey, threatType).addOnSuccessListener {
            callback.onSuccess(it.toCommonURLCheck())
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Shuts down URL check using SafetyNet API.
     *
     * @return A Work object representing the asynchronous operation.
     */
    override fun shutDownUrlCheck(): Work<Unit> {
        val worker = Work<Unit>()
        SafetyNet.getClient(context).shutdownSafeBrowsing().addOnSuccessListener {
            worker.onSuccess(Unit)
        }.addOnFailureListener {
            worker.onFailure(it)
        }
        return worker
    }
}