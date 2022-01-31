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



fun MaliciousAppsListResp.toCommonMaliciousAppList(): CommonMaliciousAppResponse{
    return CommonMaliciousAppResponse()
        .also { it -> it.getMaliciousAppsList = maliciousAppsList.map { it.toCommonMaliciousAppData() } }
        .also { it.rtnCode = rtnCode }
        .also { it.errorReason = errorReason }
}

fun SafetyNetApi.HarmfulAppsResponse.toCommonMaliciousAppList(): CommonMaliciousAppResponse{
    return CommonMaliciousAppResponse()
        .also { it.getMaliciousAppsList = harmfulAppsList.map { it.toCommonMaliciousAppData() } }
        .also { it.rtnCode = hoursSinceLastScanWithHarmfulApp }
}

fun com.huawei.hms.support.api.entity.safetydetect.MaliciousAppsData.toCommonMaliciousAppData(): CommonMaliciousAppsData{
    return CommonMaliciousAppsData()
        .also { it.apkCategory = apkCategory }
        .also { it.apkPackageName = apkPackageName }
        .also { it.apkSha256 = apkSha256 }
}

fun com.google.android.gms.safetynet.HarmfulAppsData.toCommonMaliciousAppData(): CommonMaliciousAppsData{
    return CommonMaliciousAppsData()
        .also { it.apkCategory = apkCategory }
        .also { it.apkPackageName = apkPackageName }
        .also { it.apkSha256 = apkSha256.toString() }
}

fun VerifyAppsCheckEnabledResp.toCommonVerifyAppUserEnabled(): CommonVerifyAppChecksEnabledRes{
    return CommonVerifyAppChecksEnabledRes().also { it.result = result }.also { it.errorReason = errorReason }.also { it.rtnCode = rtnCode }
}

fun SafetyNetApi.VerifyAppsUserResponse.toCommonVerifyAppUserEnabled() : CommonVerifyAppChecksEnabledRes{
    return  CommonVerifyAppChecksEnabledRes().also { it.result = isVerifyAppsEnabled }
}

fun UrlCheckResponse.toCommonURLCheck(): CommonUrlCheckRes{
    return CommonUrlCheckRes().also { it.urlCheckThreats = urlCheckResponse.map { it.toCommonThreatType() } }
}

fun SafetyNetApi.SafeBrowsingResponse.toCommonURLCheck(): CommonUrlCheckRes{
    return CommonUrlCheckRes().also { it.urlCheckThreats =  detectedThreats.map { it.toCommonThreatType() }}
}

fun UrlCheckThreat.toCommonThreatType(): CommonUrlCheckThreat{
    return CommonUrlCheckThreat().also { it.urlCheckResult = urlCheckResult }
}

fun SafeBrowsingThreat.toCommonThreatType(): CommonUrlCheckThreat{
    return CommonUrlCheckThreat().also { it.urlCheckResult = threatType }
}
