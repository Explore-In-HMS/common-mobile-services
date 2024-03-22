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
package com.hms.lib.commonmobileservices.mapkit

import android.location.Location

/**
 * Interface for a location source that provides location updates.
 */
interface LocationSource {
    /**
     * Activates the location source and starts providing location updates.
     *
     * @param listener The listener to be notified when the location changes.
     */
    fun activate(listener: OnLocationChangedListener)

    /**
     * Deactivates the location source and stops providing location updates.
     */
    fun deactivate()

    /**
     * Interface definition for a callback to be invoked when the location changes.
     */
    interface OnLocationChangedListener {
        /**
         * Called when the location has changed.
         *
         * @param location The new location.
         */
        fun onLocationChanged(location: Location)
    }
}
