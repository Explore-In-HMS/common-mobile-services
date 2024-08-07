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
package com.hms.lib.commonmobileservices.location.model

import android.location.Location
import java.lang.Exception

/**
 * Data class representing a common location result.
 * @property location The location data. Defaults to null.
 * @property state The state of the location result. Defaults to SUCCESS.
 * @property exception An exception associated with the location result. Defaults to null.
 */
data class CommonLocationResult(
    val location: Location? = null,
    val state: LocationResultState = LocationResultState.SUCCESS,
    val exception: Exception? = null
)

