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

import com.hms.lib.commonmobileservices.safety.SafetyServiceResponse
import com.hms.lib.commonmobileservices.safety.common.Mapper
import com.huawei.hms.support.api.entity.safetydetect.UserDetectResponse

/**
 * Mapper class for mapping Huawei SafetyDetect API responses to SafetyServiceResponse objects.
 */
class HuaweiSafetyMapper : Mapper<UserDetectResponse, SafetyServiceResponse>() {
    /**
     * Maps a UserDetectResponse object to a SafetyServiceResponse object.
     *
     * @param from The UserDetectResponse object to map from.
     * @return The mapped SafetyServiceResponse object.
     */
    override fun map(from: UserDetectResponse): SafetyServiceResponse = SafetyServiceResponse(
        responseToken = from.responseToken
    )
}