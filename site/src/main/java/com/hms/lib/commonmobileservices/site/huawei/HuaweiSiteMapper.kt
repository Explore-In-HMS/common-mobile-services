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

package com.hms.lib.commonmobileservices.site.huawei

import com.hms.lib.commonmobileservices.site.SiteServiceReturn
import com.hms.lib.commonmobileservices.site.common.Mapper
import com.huawei.hms.site.api.model.Site

class HuaweiSiteMapper : Mapper<SiteServiceReturn, Site>(){
    override fun mapToEntity(from: Site): SiteServiceReturn = SiteServiceReturn(
        id = from.siteId,
        name = from.name,
        locationLat = from.location.lat,
        locationLong = from.location.lng,
        phoneNumber =  from.poi.internationalPhone,
        formatAddress = from.formatAddress,
        distance = from.distance,
        image = ArrayList(),
        averagePrice = from.poi.priceLevel.toDouble(),
        point = from.poi.rating
    )
}
