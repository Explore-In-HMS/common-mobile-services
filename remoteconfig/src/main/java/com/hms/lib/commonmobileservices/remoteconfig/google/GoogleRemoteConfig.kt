package com.hms.lib.commonmobileservices.remoteconfig.google

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.hms.lib.commonmobileservices.remoteconfig.manager.IRemoteConfigService

class GoogleRemoteConfig:IRemoteConfigService {
    private var config: FirebaseRemoteConfig = Firebase.remoteConfig
    override fun setDefaultXml(xml: Int) {
        config.setDefaultsAsync(xml)
    }

    override fun fetchAndApply(interval:Long) {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = interval
        }
        config.setConfigSettingsAsync(configSettings)
        config.fetchAndActivate().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Log.e("FetchAndApply",task.exception?.message.toString())
                                }
            else{
                Log.e("FetchAndApply",task.exception?.message.toString())
            }
        }
    }

    override fun getString(keyValue: String): String {
        return config.getString(keyValue)
    }

    override fun getBoolean(keyValue: String): Boolean {
        return config.getBoolean(keyValue)
    }

    override fun getLong(keyValue: String): Long {
        return config.getLong(keyValue)
    }

    override fun getDouble(keyValue: String): Double {
        return config.getDouble(keyValue)
    }
}