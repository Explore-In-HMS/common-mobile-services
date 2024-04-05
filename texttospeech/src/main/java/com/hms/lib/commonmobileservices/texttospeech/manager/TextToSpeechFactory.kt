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

package com.hms.lib.commonmobileservices.texttospeech.manager

import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.texttospeech.GoogleTextToSpeechKit
import com.hms.lib.commonmobileservices.texttospeech.HuaweiTextToSpeechKit

/**
 * Factory class for obtaining text-to-speech services.
 */
class TextToSpeechFactory {
    /**
     * Retrieves the appropriate text-to-speech service based on the mobile service type.
     *
     * @param type The mobile service type.
     * @return An instance of the text-to-speech service corresponding to the provided type.
     */
    fun getMLService(type: MobileServiceType): ITextToSpeechAPI? {
        return if (MobileServiceType.HMS === type) {
            HuaweiTextToSpeechKit()
        } else {
            GoogleTextToSpeechKit()
        }
    }
}