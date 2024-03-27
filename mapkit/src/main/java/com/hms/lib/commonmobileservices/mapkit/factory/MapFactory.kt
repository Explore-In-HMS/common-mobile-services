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
package com.hms.lib.commonmobileservices.mapkit.factory

import android.content.Context
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.huawei.hms.maps.MapsInitializer

/**
 * A factory class for creating map objects. This class uses the Factory pattern to instantiate map objects
 * based on the specified mobile service type. It abstracts the complexity of map initialization and
 * allows for easy switching between different map services.
 */
class MapFactory {
    companion object {
        /**
         * Creates and returns a map object based on the specified [type] of mobile service.
         * It supports creating maps for both Google Maps (GMS) and Huawei Mobile Services (HMS).
         * The method initializes the necessary services and returns an instance of [CommonMap].
         *
         * @param context The application context used for initializing map services.
         * @param type The type of mobile service for which to create the map. This is an enum
         *             that can either be `MobileServiceType.HMS` for Huawei Maps or any other
         *             value for Google Maps.
         * @param apiKey The API key used for initializing the map services. This is optional
         *               and primarily used for Huawei Maps initialization.
         * @return An instance of [CommonMap] that corresponds to the specified mobile service type.
         */
        fun createAndGetMap(
            context: Context,
            type: MobileServiceType,
            apiKey: String? = null
        ): CommonMap {
            return if (type == MobileServiceType.HMS) {
                MapsInitializer.initialize(context)
                HuaweiCommonMapImpl(context, apiKey)
            } else {
                GoogleCommonMapImpl(context)
            }
        }
    }
}