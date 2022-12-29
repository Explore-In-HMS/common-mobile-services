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
package com.hms.lib.commonmobileservices.crash.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.crashlytics.CustomKeysAndValues
import com.huawei.agconnect.crash.AGConnectCrash

class HuaweiCrashKit(var context: Context) : CrashService {
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
    }

    override fun testIt(context: Context) {
        AGConnectCrash.getInstance().testIt(context)
    }

    override fun log(log: String) {
        AGConnectCrash.getInstance().log(log)
    }

    override fun log(log1: Int, log2: String) {
        AGConnectCrash.getInstance().log(log1, log2)
    }

    override fun setCustomKey(key: String, value: String) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Boolean) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Double) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Float) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Int) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Long) {
        AGConnectCrash.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKeys(keysAndValues: CustomKeysAndValues) {
        //
    }

    override fun setUserId(id: String) {
        AGConnectCrash.getInstance().setUserId(id)
    }

    override fun recordException(throwable: Throwable) {
        AGConnectCrash.getInstance().recordException(throwable)
    }

    override fun enableCrashCollection(enabled: Boolean) {
        AGConnectCrash.getInstance().enableCrashCollection(enabled)
    }
}