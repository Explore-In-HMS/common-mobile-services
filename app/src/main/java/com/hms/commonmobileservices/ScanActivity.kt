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
package com.hms.commonmobileservices

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hms.lib.commonmobileservices.core.handleSuccess
import com.hms.lib.commonmobileservices.databinding.ActivityScanBinding

import com.hms.lib.commonmobileservices.scan.manager.HuaweiGoogleScanManager


class ScanActivity : AppCompatActivity() {

    companion object {
        const val REQ_CODE = 1
    }

    lateinit var binding: ActivityScanBinding
    lateinit var scanManager: HuaweiGoogleScanManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scanManager = HuaweiGoogleScanManager(this)

        binding.scanDemoLauncherBtn.setOnClickListener {
            scanManager.performScan(this, REQ_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            scanManager.parseScanToTextData({ resultData ->
                resultData.handleSuccess {
                    setScanResult(it.data)
                }
            }, this, data)
        }
    }

    fun setScanResult(result: String?) {
        binding.scanResultTxt.text = "Result: $result"
    }
}