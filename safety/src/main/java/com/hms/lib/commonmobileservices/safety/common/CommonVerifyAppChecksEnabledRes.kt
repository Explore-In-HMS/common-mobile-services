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

/**
 * Class representing a common response containing information about whether app checks are enabled.
 */
class CommonVerifyAppChecksEnabledRes {
    /**
     * Boolean value indicating whether app checks are enabled.
     */
    var result: Boolean? = null

    /**
     * Error reason in case of failure.
     */
    var errorReason: String? = null

    /**
     * Return code indicating the status of the response.
     */
    var rtnCode: Int? = null
}