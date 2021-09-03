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
package com.hms.lib.commonmobileservices.scan.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.ResultData

class HuaweiGoogleScanManager(context: Context) {
    private var scanKitService: IScanKitAPI ?=null

    init {
        scanKitService = ScanKitFactory().getScanKitService(Device.getMobileServiceType(context, MobileServiceType.HMS))!!
    }

    fun performScan(activity:Activity, resultCode:Int) {
        scanKitService?.performScan(activity,resultCode)
    }

    fun parseScanToTextData(callback: (scanToTextResult: ResultData<String>) -> Unit, activity:Activity, data:Intent){
        scanKitService?.parseScanToTextData(callback,activity,data)
    }
}