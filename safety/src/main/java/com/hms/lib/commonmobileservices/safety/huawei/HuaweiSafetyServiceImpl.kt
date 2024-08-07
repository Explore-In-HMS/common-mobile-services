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
package com.hms.lib.commonmobileservices.safety.huawei

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Base64
import android.util.Log
import com.hms.lib.commonmobileservices.core.ResultCallback
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.safety.RootDetectionResponse
import com.hms.lib.commonmobileservices.safety.SafetyService
import com.hms.lib.commonmobileservices.safety.SafetyServiceResponse
import com.hms.lib.commonmobileservices.safety.common.*
import com.huawei.hms.support.api.entity.safetydetect.UserDetectResponse
import com.huawei.hms.support.api.safetydetect.SafetyDetect
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom


/**
 * Implementation of the SafetyService interface for Huawei Mobile Services.
 *
 * This class provides methods for performing various safety checks and operations using Huawei Mobile Services.
 *
 * @property context The context used to access Huawei Mobile Services APIs.
 */
class HuaweiSafetyServiceImpl(private val context: Context) : SafetyService {

    private val mapper: Mapper<UserDetectResponse, SafetyServiceResponse> = HuaweiSafetyMapper()
    private val rootDetectMapper: Mapper<JSONObject, RootDetectionResponse> =
        HuaweiRootDetectMapper()

    val TAG = "CommonMobileServicesSafetySDK"

    /**
     *   App key value is the app_id value in Huawei Mobile Services.
     */
    /**
     * Performs user detection using Huawei SafetyDetect API.
     *
     * @param appKey The app key value, which is the app_id value in Huawei Mobile Services.
     * @param callback The callback to be invoked with the result of the user detection operation.
     */
    override fun userDetect(
        appKey: String,
        callback: ResultCallback<SafetyServiceResponse>
    ) {

        val client = SafetyDetect.getClient(context)
        client.userDetection(appKey).addOnSuccessListener {
            val responseToken = it.responseToken
            if (responseToken.isNotEmpty()) {
                callback.onSuccess(mapper.map(it))
            }
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Performs root detection using Huawei SafetyDetect API.
     *
     * @param appKey The app key value, which is the app_id value in Huawei Mobile Services.
     * @param callback The callback to be invoked with the result of the root detection operation.
     */
    override fun rootDetection(
        appKey: String,
        callback: ResultCallback<RootDetectionResponse>
    ) {
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
        SafetyDetect.getClient(context)
            .sysIntegrity(nonce, appKey)
            .addOnSuccessListener { result ->
                val jwsStr = result.result
                val jwsSplit = jwsStr.split(".").toTypedArray()
                val jwsPayloadStr = jwsSplit[1]
                val payloadDetail = String(
                    Base64.decode(
                        jwsPayloadStr.toByteArray(StandardCharsets.UTF_8),
                        Base64.URL_SAFE
                    ), StandardCharsets.UTF_8
                )
                val jsonObject = JSONObject(payloadDetail)
                callback.onSuccess(rootDetectMapper.map(jsonObject))
            }
            .addOnFailureListener { e ->
                callback.onFailure(e)
            }
    }

    /**
     * Retrieves the list of malicious apps detected by Huawei SafetyDetect API.
     *
     * @param callback The callback to be invoked with the result containing the list of malicious apps.
     */
    override fun getMaliciousAppsList(callback: ResultCallback<CommonMaliciousAppResponse>) {
        SafetyDetect.getClient(context).maliciousAppsList.addOnSuccessListener {
            callback.onSuccess(it.toCommonMaliciousAppList())
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Checks if app checks are enabled using Huawei SafetyDetect API.
     *
     * @param callback The callback to be invoked with the result indicating whether app checks are enabled.
     */
    override fun isAppChecksEnabled(callback: ResultCallback<CommonVerifyAppChecksEnabledRes>) {
        SafetyDetect.getClient(context).isVerifyAppsCheck.addOnSuccessListener {
            callback.onSuccess(it.toCommonVerifyAppUserEnabled())
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Enables app checks using Huawei SafetyDetect API.
     *
     * @param callback The callback to be invoked with the result indicating whether app checks are enabled.
     */
    override fun enableAppsCheck(callback: ResultCallback<CommonVerifyAppChecksEnabledRes>) {
        SafetyDetect.getClient(context).enableAppsCheck().addOnSuccessListener {
            callback.onSuccess(it.toCommonVerifyAppUserEnabled())
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Initializes URL check using Huawei SafetyDetect API.
     *
     * @return A Work object representing the initialization task.
     */
    override fun initURLCheck(): Work<Unit> {
        val worker = Work<Unit>()
        SafetyDetect.getClient(context).initUrlCheck().addOnSuccessListener {
            worker.onSuccess(Unit)
        }.addOnFailureListener {
            worker.onFailure(it)
        }
        return worker
    }

    /**
     * Performs URL check using Huawei SafetyDetect API.
     *
     * @param url The URL to be checked.
     * @param appKey The app key value, which is the app_id value in Huawei Mobile Services.
     * @param threatType The type of threat to check for.
     * @param callback The callback to be invoked with the result of the URL check operation.
     */
    override fun urlCheck(
        url: String,
        appKey: String,
        threatType: Int,
        callback: ResultCallback<CommonUrlCheckRes>
    ) {
        SafetyDetect.getClient(context).urlCheck(url, appKey, threatType).addOnSuccessListener {
            callback.onSuccess(it.toCommonURLCheck())
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    /**
     * Shuts down URL check using Huawei SafetyDetect API.
     *
     * @return A Work object representing the shutdown task.
     */
    override fun shutDownUrlCheck(): Work<Unit> {
        val worker = Work<Unit>()
        SafetyDetect.getClient(context).shutdownUrlCheck().addOnSuccessListener {
            worker.onSuccess(Unit)
        }.addOnFailureListener {
            worker.onFailure(it)
        }
        return worker
    }
}