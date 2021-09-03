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
package com.hms.lib.commonmobileservices.identity

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.identity.common.Mapper
import com.hms.lib.commonmobileservices.identity.huawei.HuaweiIdentityMapper
import com.hms.lib.commonmobileservices.identity.huawei.HuaweiIdentityServiceImpl
import com.hms.lib.commonmobileservices.identity.model.UserAddressResponse
import com.huawei.hms.identity.entity.UserAddress


interface IdentityService {

    companion object {
        val TAG: String = "Identity Kit"
        val requestCode: Int = 1001
        val mapper: Mapper<UserAddress, UserAddressResponse> = HuaweiIdentityMapper()
    }

    fun getUserAddress()

    object Factory {
        fun create(context: Context): IdentityService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.HMS -> {
                    HuaweiIdentityServiceImpl(context)
                }
                else -> {
                    throw Exception("This SDK not supported on GMS")
                }
            }
        }
    }
}