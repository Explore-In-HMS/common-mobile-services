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

package com.hms.lib.commonmobileservices.crash.manager

import android.content.Context
import com.hms.lib.commonmobileservices.core.MobileServiceType

/**
 * Factory class responsible for creating instances of CrashService based on the provided MobileServiceType.
 */
class CrashKitFactory {
    /**
     * Gets the appropriate CrashService based on the provided MobileServiceType.
     *
     * @param context The context needed for initializing the CrashService.
     * @param type The MobileServiceType indicating the type of CrashService to retrieve.
     * @return An instance of CrashService corresponding to the provided type, or null if the type is not supported.
     */
    fun getCrashService(context: Context, type: MobileServiceType): CrashService? {
        return when (type) {
            MobileServiceType.HMS -> HuaweiCrashKit(context)
            MobileServiceType.GMS -> GoogleCrashKit(context)
            else -> null
        }
    }
}