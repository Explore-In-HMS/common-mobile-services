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

import com.hms.lib.commonmobileservices.safety.RootDetectionResponse
import com.hms.lib.commonmobileservices.safety.common.Mapper
import org.json.JSONObject

/**
 * Mapper class for mapping JSONObject to RootDetectionResponse.
 * This mapper is used to map a JSONObject representing root detection response
 * to a RootDetectionResponse object.
 */
class GoogleRootDetectMapper : Mapper<JSONObject, RootDetectionResponse>() {

    /**
     * Maps the JSONObject to RootDetectionResponse.
     *
     * @param from The source JSONObject representing root detection response.
     * @return A RootDetectionResponse object mapped from the JSONObject.
     */
    override fun map(from: JSONObject): RootDetectionResponse = RootDetectionResponse(
        apkDigestSha256 = from.getString("apkDigestSha256"),
        apkPackageName = from.getString("apkPackageName"),
        basicIntegrity = from.getBoolean("basicIntegrity"),
        nonce = from.getString("nonce"),
        timestampMs = from.getLong("timestampMs")
    )
}