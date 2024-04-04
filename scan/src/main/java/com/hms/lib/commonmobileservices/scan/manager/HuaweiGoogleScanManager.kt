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

/**
 * A manager class for handling barcode scanning using Huawei and Google Scan Kits.
 * This class selects the appropriate Scan Kit service based on the device's Mobile Service Type.
 *
 * @property context The context used to determine the Mobile Service Type.
 */
class HuaweiGoogleScanManager(context: Context) {
    private var scanKitService: IScanKitAPI? = null

    init {
        scanKitService = ScanKitFactory().getScanKitService(Device.getMobileServiceType(context, MobileServiceType.HMS))!!
    }

    /**
     * Performs a barcode scan.
     *
     * @param activity The activity context.
     * @param resultCode The result code to be used for the scan result.
     */
    fun performScan(activity: Activity, resultCode: Int) {
        scanKitService?.performScan(activity, resultCode)
    }

    /**
     * Parses the barcode scan result into text data.
     *
     * @param callback Callback to receive the parsed scan result.
     * @param activity The activity context.
     * @param data The intent data containing the scan result.
     */
    fun parseScanToTextData(callback: (scanToTextResult: ResultData<String>) -> Unit, activity: Activity, data: Intent) {
        scanKitService?.parseScanToTextData(callback, activity, data)
    }
}