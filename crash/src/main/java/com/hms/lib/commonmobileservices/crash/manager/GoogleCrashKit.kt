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
import com.google.firebase.crashlytics.FirebaseCrashlytics

class GoogleCrashKit(var context: Context) : CrashService {
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(
            "com.hms.lib.commonmobileservices.productadvisor",
            Context.MODE_PRIVATE
        )
    }

    override fun testIt(context: Context) {
        throw RuntimeException("Test Crash")
    }

    override fun log(log: String) {
        FirebaseCrashlytics.getInstance().log(log)
    }

    override fun log(log1: Int, log2: String) {
        //
    }

    override fun setCustomKey(key: String, value: String) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Boolean) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Double) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Float) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Int) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Long) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    override fun setCustomKeys(keysAndValues: CustomKeysAndValues) {
        FirebaseCrashlytics.getInstance().setCustomKeys(keysAndValues)
    }

    override fun setUserId(id: String) {
        FirebaseCrashlytics.getInstance().setUserId(id)
    }

    override fun recordException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    override fun enableCrashCollection(enabled: Boolean) {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(enabled)
    }

}