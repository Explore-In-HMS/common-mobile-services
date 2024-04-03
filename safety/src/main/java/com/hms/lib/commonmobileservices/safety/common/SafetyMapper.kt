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
package com.hms.lib.commonmobileservices.safety.common

import com.google.android.gms.safetynet.SafeBrowsingThreat
import com.google.android.gms.safetynet.SafetyNetApi
import com.huawei.hms.support.api.entity.safetydetect.MaliciousAppsListResp
import com.huawei.hms.support.api.entity.safetydetect.UrlCheckResponse
import com.huawei.hms.support.api.entity.safetydetect.UrlCheckThreat
import com.huawei.hms.support.api.entity.safetydetect.VerifyAppsCheckEnabledResp

/**
 * Extension function to map MaliciousAppsListResp to CommonMaliciousAppResponse.
 */
fun MaliciousAppsListResp.toCommonMaliciousAppList(): CommonMaliciousAppResponse {
    return CommonMaliciousAppResponse()
        .also { it.getMaliciousAppsList = maliciousAppsList.map { it.toCommonMaliciousAppData() } }
        .also { it.rtnCode = rtnCode }
        .also { it.errorReason = errorReason }
}

/**
 * Extension function to map SafetyNetApi.HarmfulAppsResponse to CommonMaliciousAppResponse.
 */
fun SafetyNetApi.HarmfulAppsResponse.toCommonMaliciousAppList(): CommonMaliciousAppResponse {
    return CommonMaliciousAppResponse()
        .also { it.getMaliciousAppsList = harmfulAppsList.map { it.toCommonMaliciousAppData() } }
        .also { it.rtnCode = hoursSinceLastScanWithHarmfulApp }
}

/**
 * Extension function to map com.huawei.hms.support.api.entity.safetydetect.MaliciousAppsData to CommonMaliciousAppsData.
 */
fun com.huawei.hms.support.api.entity.safetydetect.MaliciousAppsData.toCommonMaliciousAppData(): CommonMaliciousAppsData {
    return CommonMaliciousAppsData()
        .also { it.apkCategory = apkCategory }
        .also { it.apkPackageName = apkPackageName }
        .also { it.apkSha256 = apkSha256 }
}

/**
 * Extension function to map com.google.android.gms.safetynet.HarmfulAppsData to CommonMaliciousAppsData.
 */
fun com.google.android.gms.safetynet.HarmfulAppsData.toCommonMaliciousAppData(): CommonMaliciousAppsData {
    return CommonMaliciousAppsData()
        .also { it.apkCategory = apkCategory }
        .also { it.apkPackageName = apkPackageName }
        .also { it.apkSha256 = apkSha256.toString() }
}

/**
 * Extension function to map VerifyAppsCheckEnabledResp to CommonVerifyAppChecksEnabledRes.
 */
fun VerifyAppsCheckEnabledResp.toCommonVerifyAppUserEnabled(): CommonVerifyAppChecksEnabledRes {
    return CommonVerifyAppChecksEnabledRes().also { it.result = result }.also { it.errorReason = errorReason }.also { it.rtnCode = rtnCode }
}

/**
 * Extension function to map SafetyNetApi.VerifyAppsUserResponse to CommonVerifyAppChecksEnabledRes.
 */
fun SafetyNetApi.VerifyAppsUserResponse.toCommonVerifyAppUserEnabled(): CommonVerifyAppChecksEnabledRes {
    return CommonVerifyAppChecksEnabledRes().also { it.result = isVerifyAppsEnabled }
}

/**
 * Extension function to map UrlCheckResponse to CommonUrlCheckRes.
 */
fun UrlCheckResponse.toCommonURLCheck(): CommonUrlCheckRes {
    return CommonUrlCheckRes().also { it.urlCheckThreats = urlCheckResponse.map { it.toCommonThreatType() } }
}

/**
 * Extension function to map SafetyNetApi.SafeBrowsingResponse to CommonUrlCheckRes.
 */
fun SafetyNetApi.SafeBrowsingResponse.toCommonURLCheck(): CommonUrlCheckRes {
    return CommonUrlCheckRes().also { it.urlCheckThreats =  detectedThreats.map { it.toCommonThreatType() } }
}

/**
 * Extension function to map UrlCheckThreat to CommonUrlCheckThreat.
 */
fun UrlCheckThreat.toCommonThreatType(): CommonUrlCheckThreat {
    return CommonUrlCheckThreat().also { it.urlCheckResult = urlCheckResult }
}

/**
 * Extension function to map SafeBrowsingThreat to CommonUrlCheckThreat.
 */
fun SafeBrowsingThreat.toCommonThreatType(): CommonUrlCheckThreat {
    return CommonUrlCheckThreat().also { it.urlCheckResult = threatType }
}
