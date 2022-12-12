package com.hms.lib.commonmobileservices.remoteconfig.huawei

import android.util.Log
import com.hms.lib.commonmobileservices.remoteconfig.manager.IRemoteConfigService
import com.huawei.agconnect.remoteconfig.AGConnectConfig

class HuaweiRemoteConfig:IRemoteConfigService {
    private var config: AGConnectConfig = AGConnectConfig.getInstance()
    override fun setDefaultXml(xml: Int) {
        config.applyDefault(xml)
    }

    override fun fetchAndApply(interval:Long) {
        config.fetch(interval)
            .addOnSuccessListener { configValues -> // Apply Network Config to Current Config
                config.apply(configValues)
            }.addOnFailureListener { Log.e("FetchAndApply",it.message.toString()) }
    }

    override fun getString(keyValue: String): String {
        return config.getValueAsString(keyValue)
    }

    override fun getBoolean(keyValue: String): Boolean {
        return config.getValueAsBoolean(keyValue)
    }

    override fun getLong(keyValue: String): Long {
        return config.getValueAsLong(keyValue)
    }

    override fun getDouble(keyValue: String): Double {
        return config.getValueAsDouble(keyValue)
    }
}