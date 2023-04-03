package com.hms.commonmobileservices.ads.manager

import com.hms.commonmobileservices.ads.GoogleAdsKit
import com.hms.commonmobileservices.ads.HuaweiAdsKit
import com.hms.lib.commonmobileservices.core.MobileServiceType

class AdsFactory {

    fun getAdsService(type: MobileServiceType): IAdsAPI {
        return if (MobileServiceType.HMS === type) {
            HuaweiAdsKit()
        } else {
            GoogleAdsKit()
        }
    }

}