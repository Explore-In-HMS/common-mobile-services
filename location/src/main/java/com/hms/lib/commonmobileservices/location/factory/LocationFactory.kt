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
package com.hms.lib.commonmobileservices.location.factory

import android.app.Activity
import androidx.lifecycle.Lifecycle
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.location.CommonLocationClient

/**
 * Factory class for creating location clients.
 */
class LocationFactory {
    companion object {
        /**
         * Retrieves the appropriate location client based on the preferred type and device capabilities.
         *
         * @param activity The activity context.
         * @param lifecycle The lifecycle of the activity.
         * @param needBackgroundPermissions Flag indicating whether background permissions are needed.
         * @param preferredType The preferred type of location service.
         * @return A CommonLocationClient instance representing the location client.
         */
        fun getLocationClient(
            activity: Activity,
            lifecycle: Lifecycle,
            needBackgroundPermissions: Boolean = false,
            preferredType: MobileServiceType? = null
        ): CommonLocationClient? {
            return when (preferredType ?: Device.getMobileServiceType(activity, preferredType)) {
                MobileServiceType.HMS -> HuaweiLocationClientImpl(
                    activity,
                    lifecycle,
                    needBackgroundPermissions
                )

                MobileServiceType.GMS -> GoogleLocationClientImpl(
                    activity,
                    lifecycle,
                    needBackgroundPermissions
                )

                MobileServiceType.NON -> null
            }
        }
    }
}