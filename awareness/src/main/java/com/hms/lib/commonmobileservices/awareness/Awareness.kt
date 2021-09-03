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

package com.hms.lib.commonmobileservices.awareness

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Handler
import androidx.work.*
import com.hms.lib.commonmobileservices.awareness.service.AwarenessWorkManager
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import java.util.concurrent.TimeUnit


class Awareness(var context: Activity) {
     fun setWorkManager() {
         Handler().post {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                 context.runWithPermissions(
                     Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                     Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                     Manifest.permission.ACTIVITY_RECOGNITION) {
                     runWorkManager()
                 }
             }
             else{
                 context.runWithPermissions(
                     Manifest.permission.ACCESS_FINE_LOCATION,
                     Manifest.permission.ACCESS_COARSE_LOCATION) {
                     runWorkManager()
                 }
             }
         }
    }

    private fun runWorkManager(){
        val data = Data.Builder().putInt("intKey", 1).build()

        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .build()

        val myWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<AwarenessWorkManager>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(data)
                .build()

        WorkManager.getInstance(context).enqueue(myWorkRequest)
    }
}