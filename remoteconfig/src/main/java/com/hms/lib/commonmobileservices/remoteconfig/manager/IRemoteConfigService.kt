package com.hms.lib.commonmobileservices.remoteconfig.manager

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.remoteconfig.google.GoogleRemoteConfig
import com.hms.lib.commonmobileservices.remoteconfig.huawei.HuaweiRemoteConfig

interface IRemoteConfigService {
    fun setDefaultXml(xml: Int)
    fun fetchAndApply(interval:Long)
    fun getString(keyValue: String): String
    fun getBoolean(keyValue: String): Boolean
    fun getLong(keyValue: String): Long
    fun getDouble(keyValue: String): Double

    object Factory {
        fun create(context: Context): IRemoteConfigService {
            return when (Device.getMobileServiceType(context)) {
                MobileServiceType.GMS -> {
                    GoogleRemoteConfig()
                }
                MobileServiceType.HMS -> {
                    HuaweiRemoteConfig()
                }
                else -> {
                    throw Exception("Unknown service")
                }
            }
        }
    }
}