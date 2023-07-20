package com.hms.lib.commonmobileservices.ads.rewarded.common

interface IRewardItem {
    fun getAmount(): Int?

    /**
     * This function return name for hms instance type for gms instance
     */
    fun getTypeOrName(): String?
}