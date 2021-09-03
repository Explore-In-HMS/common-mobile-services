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
package com.hms.lib.commonmobileservices.identity.huawei

import com.hms.lib.commonmobileservices.identity.common.Mapper
import com.hms.lib.commonmobileservices.identity.model.UserAddressResponse
import com.huawei.hms.identity.entity.UserAddress

class HuaweiIdentityMapper : Mapper<UserAddress, UserAddressResponse>() {
    override fun map(from: UserAddress): UserAddressResponse = UserAddressResponse(
        title = from.name,
        addressLine = from.addressLine1 + from.addressLine2,
        province = from.administrativeArea,
        county = from.locality,
        buildingNo = from.addressLine4,
        aptNo = from.addressLine5
    )
}