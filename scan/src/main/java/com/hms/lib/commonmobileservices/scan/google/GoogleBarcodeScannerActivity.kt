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

package com.hms.lib.commonmobileservices.scan.google


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.hms.lib.commonmobileservices.scan.R
import java.io.IOException


/**
 * Activity for scanning barcodes using Google's barcode scanning APIs.
 */
class GoogleBarcodeScannerActivity : AppCompatActivity() {
    private var surfaceView: SurfaceView? = null
    private var intentData = ""
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_barcode_scanner)
        initComponents()
    }

    /**
     * Initializes the UI components.
     */
    private fun initComponents() {
        surfaceView = findViewById(R.id.surfaceView)
    }

    /**
     * Initializes barcode detectors and camera sources.
     */
    private fun initialiseDetectorsAndSources() {
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = barcodeDetector?.let {
            CameraSource.Builder(this, it)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build()
        }
        surfaceView?.holder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                openCamera()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource?.stop()
            }
        })
        barcodeDetector?.setProcessor(object :
            Detector.Processor<Barcode> {
            override fun release() {}
            override fun receiveDetections(detections: Detections<Barcode>) {
                val barCode =
                    detections.detectedItems
                if (barCode.size() > 0) {
                    setBarCode(barCode)
                }
            }
        })
    }

    /**
     * Opens the camera for scanning.
     */
    private fun openCamera() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        try {
            surfaceView?.holder?.let { cameraSource?.start(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Sets the barcode data and finishes the activity.
     *
     * @param barCode The detected barcode data.
     */
    private fun setBarCode(barCode: SparseArray<Barcode>) {
        intentData = barCode.valueAt(0).displayValue
        val resultIntent = Intent()
        resultIntent.putExtra("scan_result", intentData)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        cameraSource?.release()
    }

    override fun onResume() {
        super.onResume()
        initialiseDetectorsAndSources()
    }

    /**
     * Handles the result of the permission request.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) finish() else openCamera()
        } else finish()
    }

    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 201
    }
}
