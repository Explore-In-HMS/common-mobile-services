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
package com.hms.lib.commonmobileservices.location.common

class CommonActivityIdentificationData {

    companion object{
        const val VEHICLE = 100
        const val BIKE = 101
        const val FOOT = 102
        const val STILL = 103
        const val OTHERS = 104
        const val WALKING = 107
        const val RUNNING = 108
    }
    var possibility: Int?=null
    var identificationActivity:Int?=null
}