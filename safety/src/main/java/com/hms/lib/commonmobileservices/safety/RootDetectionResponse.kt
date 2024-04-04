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
package com.hms.lib.commonmobileservices.safety

/**
 * Data class representing the response from root detection.
 *
 * @property apkDigestSha256 The SHA-256 digest of the APK.
 * @property apkPackageName The package name of the APK.
 * @property basicIntegrity Indicates whether basic integrity check passed.
 * @property nonce A string used to differentiate the response.
 * @property timestampMs The timestamp of the response in milliseconds.
 */
data class RootDetectionResponse(
    val apkDigestSha256: String,
    val apkPackageName: String,
    val basicIntegrity: Boolean,
    val nonce: String,
    val timestampMs: Long
)