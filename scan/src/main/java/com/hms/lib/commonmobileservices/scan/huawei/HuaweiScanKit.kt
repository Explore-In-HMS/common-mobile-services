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
package com.hms.lib.commonmobileservices.scan.huawei

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.hms.lib.commonmobileservices.scan.manager.IScanKitAPI
import com.hms.lib.commonmobileservices.core.ResultData
import java.lang.Exception

class HuaweiScanKit : IScanKitAPI  {
    override fun performScan(
        activity: Activity,
        scanResultCode:Int
    ) {
        if(ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED){
            val options = HmsScanAnalyzerOptions.Creator()
                .setHmsScanTypes(HmsScan.ALL_SCAN_TYPE)
                .create()
            ScanUtil.startScan(activity, scanResultCode, options)
        }else{
            val strings = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(activity, strings, 2)
        }
    }

    override fun parseScanToTextData(
        callback: (scanToTextResult: ResultData<String>) -> Unit,
        activity: Activity,
        data: Intent
    ) {
        try {
            (data.getParcelableExtra(ScanUtil.RESULT) as? HmsScan)?.let {
                callback.invoke(ResultData.Success(it.getOriginalValue()))
            }
        }
        catch (e:Exception){
            callback.invoke(ResultData.Failed())
        }
    }
}