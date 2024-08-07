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

package com.hms.lib.commonmobileservices.speechtotext.manager

import com.hms.lib.commonmobileservices.speechtotext.GoogleSpeechToTextKit
import com.hms.lib.commonmobileservices.speechtotext.HuaweiSpeechToTextKit
import com.hms.lib.commonmobileservices.core.MobileServiceType

/**
 * Factory class for obtaining Speech-to-Text API implementations.
 */
class SpeechToTextFactory {
    /**
     * Gets the appropriate Speech-to-Text API service based on the mobile service type.
     *
     * @param type The mobile service type.
     * @return An instance of ISpeechToTextAPI for the specified service type.
     */
    fun getMLService(type: MobileServiceType): ISpeechToTextAPI? {
        return if (MobileServiceType.HMS === type) {
            HuaweiSpeechToTextKit()
        } else {
            GoogleSpeechToTextKit()
        }
    }
}