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

package com.hms.lib.commonmobileservices.site

/**
 * Data class representing the returned site information.
 *
 * @property id The unique identifier of the site.
 * @property name The name of the site.
 * @property locationLat The latitude of the site's location.
 * @property locationLong The longitude of the site's location.
 * @property phoneNumber The phone number associated with the site.
 * @property formatAddress The formatted address of the site.
 * @property distance The distance to the site (if available).
 * @property image The list of image references associated with the site.
 * @property averagePrice The average price of the site (if available).
 * @property point The rating of the site (if available).
 */
data class SiteServiceReturn (
    val id : String?,
    val name : String?,
    val locationLat : Double?,
    val locationLong : Double?,
    val phoneNumber : String?,
    val formatAddress : String?,
    val distance : Double?,
    val image : ArrayList<String>?,
    val averagePrice : Double?,
    val point : Double?,
)
