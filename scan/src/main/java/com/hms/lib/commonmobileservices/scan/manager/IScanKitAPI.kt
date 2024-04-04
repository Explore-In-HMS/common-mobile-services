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
import android.content.Intent
import android.graphics.Bitmap
import com.hms.lib.commonmobileservices.core.ResultData

/**
 * Interface for defining common methods required for barcode scanning.
 */
interface IScanKitAPI {
    /**
     * Performs a barcode scan.
     *
     * @param activity The activity context.
     * @param scanResultCode The result code to be used for the scan result.
     */
    fun performScan(activity: Activity, scanResultCode: Int)

    /**
     * Parses the barcode scan result into text data.
     *
     * @param callback Callback to receive the parsed scan result.
     * @param activity The activity context.
     * @param data The intent data containing the scan result.
     */
    fun parseScanToTextData(callback: (scanToTextResult: ResultData<String>) -> Unit, activity: Activity, data: Intent)
}