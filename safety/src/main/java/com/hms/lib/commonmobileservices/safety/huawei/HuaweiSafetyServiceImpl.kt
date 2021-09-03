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
import com.hms.lib.commonmobileservices.safety.RootDetectionResponse
import com.hms.lib.commonmobileservices.safety.SafetyService
import com.hms.lib.commonmobileservices.safety.SafetyServiceResponse
import com.hms.lib.commonmobileservices.safety.common.Mapper
import com.huawei.hms.support.api.entity.safetydetect.SysIntegrityResp
import com.huawei.hms.support.api.entity.safetydetect.UserDetectResponse
import com.huawei.hms.support.api.safetydetect.SafetyDetect
import com.huawei.hms.support.api.safetydetect.SafetyDetectStatusCodes
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom


class HuaweiSafetyServiceImpl(private val context: Context): SafetyService {

    private val mapper: Mapper<UserDetectResponse, SafetyServiceResponse> = HuaweiSafetyMapper()
    private val rootDetectMapper: Mapper<JSONObject, RootDetectionResponse> = HuaweiRootDetectMapper()

    val TAG = "CommonMobileServicesSafetySDK"

         /**
         App key value is the app_id value in Huawei Mobile Services.
        */
        override fun userDetect(
             appKey: String,
             callback: SafetyService.SafetyServiceCallback<SafetyServiceResponse>
         ){

            val client = SafetyDetect.getClient(context)
            client.userDetection(appKey).addOnSuccessListener {
                val responseToken = it.responseToken
                if(responseToken.isNotEmpty()){
                    callback.onSuccessUserDetect(mapper.map(it))
                }
            }.addOnFailureListener {
                callback.onFailUserDetect(it)
            }
    }

    @SuppressLint("LongLogTag")
    override fun rootDetection(
        appKey: String,
        callback: SafetyService.SafetyRootDetectionCallback<RootDetectionResponse>
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
                val payloadDetail = String(Base64.decode(jwsPayloadStr.toByteArray(StandardCharsets.UTF_8), Base64.URL_SAFE), StandardCharsets.UTF_8)
                val jsonObject = JSONObject(payloadDetail)
                callback.onSuccessRootDetect(rootDetectMapper.map(jsonObject))
            }
            .addOnFailureListener { e ->
                callback.onFailRootDetect(e)
            }
    }
}