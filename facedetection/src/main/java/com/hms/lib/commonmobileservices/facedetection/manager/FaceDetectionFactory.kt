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

package com.hms.lib.commonmobileservices.facedetection.manager

import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.facedetection.GoogleFaceDetectionKit
import com.hms.lib.commonmobileservices.facedetection.HuaweiFaceDetectionKit

/**
 * A factory class for providing implementations of face detection API based on the given [MobileServiceType].
 */
class FaceDetectionFactory {

    /**
     * Returns an instance of a face detection API implementation based on the provided [type].
     * If [type] is [MobileServiceType.HMS], returns an instance of HuaweiFaceDetectionKit.
     * If [type] is not [MobileServiceType.HMS], returns an instance of GoogleFaceDetectionKit.
     *
     * @param type The type of mobile service for which face detection API implementation is requested.
     * @return An instance of IFaceDetectionAPI representing the appropriate face detection API implementation.
     */
    fun getMLService(type: MobileServiceType): IFaceDetectionAPI {
        return if (MobileServiceType.HMS === type) {
            HuaweiFaceDetectionKit()
        } else {
            GoogleFaceDetectionKit()
        }
    }
}