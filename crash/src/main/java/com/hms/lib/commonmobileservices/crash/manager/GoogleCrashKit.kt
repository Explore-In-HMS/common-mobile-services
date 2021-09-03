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
        //
    }

    override fun log(var1: String) {
        FirebaseCrashlytics.getInstance().log(var1)
    }

    override fun log(var1: Int, var2: String) {
        //
    }

    override fun setCustomKey(var1: String, var2: String) {
        FirebaseCrashlytics.getInstance().setCustomKey(var1, var2)
    }

    override fun setCustomKey(var1: String, var2: Boolean) {
        FirebaseCrashlytics.getInstance().setCustomKey(var1, var2)
    }

    override fun setCustomKey(var1: String, var2: Double) {
        FirebaseCrashlytics.getInstance().setCustomKey(var1, var2)
    }

    override fun setCustomKey(var1: String, var2: Float) {
        FirebaseCrashlytics.getInstance().setCustomKey(var1, var2)
    }

    override fun setCustomKey(var1: String, var2: Int) {
        FirebaseCrashlytics.getInstance().setCustomKey(var1, var2)
    }

    override fun setCustomKey(var1: String, var2: Long) {
        FirebaseCrashlytics.getInstance().setCustomKey(var1, var2)
    }

    override fun setCustomKeys(var1: CustomKeysAndValues) {
        FirebaseCrashlytics.getInstance().setCustomKeys(var1)
    }

    override fun setUserId(var1: String) {
        FirebaseCrashlytics.getInstance().setUserId(var1)
    }

    override fun recordException(var1: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(var1)
    }

    override fun enableCrashCollection(enable: Boolean) {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(enable)
    }

}