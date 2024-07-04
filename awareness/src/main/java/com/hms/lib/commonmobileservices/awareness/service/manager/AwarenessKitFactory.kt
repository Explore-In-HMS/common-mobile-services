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

package com.hms.lib.commonmobileservices.awareness.service.manager

import android.content.Context
import com.hms.lib.commonmobileservices.core.MobileServiceType

/**
 * Factory class for obtaining instances of awareness service implementations based on the provided mobile service type.
 */
class AwarenessKitFactory {

    /**
     * Retrieves an instance of an awareness service based on the provided mobile service type.
     *
     * @param context The application context.
     * @param type The mobile service type (HMS for Huawei Mobile Services, GMS for Google Mobile Services).
     * @return An instance of the awareness service corresponding to the provided mobile service type,
     *         or null if the type is not recognized or supported.
     */
    fun getAwarenessService(context: Context, type: MobileServiceType): IAwarenessAPI? {
        return when (type) {
            MobileServiceType.HMS -> HuaweiAwarenessKit(context)
            MobileServiceType.GMS -> GoogleAwarenessKit(context)
            else -> null
        }
    }
}