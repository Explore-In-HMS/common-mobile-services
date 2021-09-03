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
import com.google.android.gms.safetynet.SafetyNetApi
import com.hms.lib.commonmobileservices.safety.RootDetectionResponse
import com.hms.lib.commonmobileservices.safety.SafetyService
import com.hms.lib.commonmobileservices.safety.SafetyServiceResponse
import com.hms.lib.commonmobileservices.safety.common.Mapper
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

class GoogleSafetyServiceImpl(private val context: Context): SafetyService {

    private val mapper: Mapper<SafetyNetApi.RecaptchaTokenResponse, SafetyServiceResponse> = GoogleSafetyMapper()
    private val rootDetectMapper: Mapper<JSONObject, RootDetectionResponse> = GoogleRootDetectMapper()

    override fun userDetect(appKey: String,callback: SafetyService.SafetyServiceCallback<SafetyServiceResponse>){

        /**
         * App key value is the SITE_API_KEY value in Google Mobile Services.
         */
        SafetyNet.getClient(context).verifyWithRecaptcha(appKey)
            .addOnSuccessListener(){
                val responseToken = it.tokenResult
                if(responseToken.isNotEmpty()){
                    callback.onSuccessUserDetect(mapper.map(it))
                }
            }.addOnFailureListener(){
                callback.onFailUserDetect(it)
            }
    }

    override fun rootDetection(
        appKey: String,
        callback: SafetyService.SafetyRootDetectionCallback<RootDetectionResponse>
    ){
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
            .addOnSuccessListener{ result ->
                val jwsStr = result.jwsResult
                val jwsSplit = jwsStr.split(".").toTypedArray()
                val jwsPayloadStr = jwsSplit[1]
                val payloadDetail = String(Base64.decode(jwsPayloadStr.toByteArray(StandardCharsets.UTF_8), Base64.URL_SAFE), StandardCharsets.UTF_8)
                val jsonObject = JSONObject(payloadDetail)
                callback.onSuccessRootDetect(rootDetectMapper.map(jsonObject))
        }.addOnFailureListener{ e->
            callback.onFailRootDetect(e)
        }
    }
}