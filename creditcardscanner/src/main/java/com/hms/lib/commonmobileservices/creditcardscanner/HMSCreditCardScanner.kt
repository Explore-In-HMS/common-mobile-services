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
package com.hms.lib.commonmobileservices.creditcardscanner

import android.content.Context
import android.graphics.Bitmap
import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureConfig
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureFactory
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult
import com.hms.lib.commonmobileservices.core.ResultData

class HMSCreditCardScanner(private val context: Context) : CreditCardScanner {

    override fun scan(callback: (ResultData<CommonCreditCardResult>) -> Unit) {
        val config =
            MLBcrCaptureConfig.Factory() // Set the expected result type of bank card recognition.
                // MLBcrCaptureConfig.RESULT_NUM_ONLY: Recognize only the bank card number.
                // MLBcrCaptureConfig.RESULT_SIMPLE: Recognize only the bank card number and validity period.
                // MLBcrCaptureConfig.ALL_RESULT: Recognize information such as the bank card number, validity period, issuing bank, card organization, and card type.
                .setResultType(MLBcrCaptureConfig.RESULT_ALL) // Set the recognition screen display orientation.
                // MLBcrCaptureConfig.ORIENTATION_AUTO: adaptive mode. The display orientation is determined by the physical sensor.
                // MLBcrCaptureConfig.ORIENTATION_LANDSCAPE: landscape mode.
                // MLBcrCaptureConfig.ORIENTATION_PORTRAIT: portrait mode.
                .setOrientation(MLBcrCaptureConfig.ORIENTATION_AUTO)
                .create()
        val bankCapture = MLBcrCaptureFactory.getInstance().getBcrCapture(config)
        bankCapture.captureFrame(context, object : MLBcrCapture.Callback {
            override fun onSuccess(bankCardResult: MLBcrCaptureResult) {
                // Processing for successful recognition.
                callback.invoke(ResultData.Success(bankCardResult.mapToCommonCreditCardResult()))
            }

            override fun onCanceled() {
                // Processing for recognition request cancelation.
                callback.invoke(
                    ResultData.Failed(context.getString(R.string.user_cancelled),
                        ScanError(context.getString(R.string.user_cancelled),
                            ScanError.ScanErrorType.USER_CANCELED
                        )
                    ))
            }

            // Callback method used when no text is recognized or a system exception occurs during recognition.
            // retCode: result code.
            // bitmap: bank card image that fails to be recognized.
            override fun onFailure(retCode: Int, bitmap: Bitmap) {
                callback.invoke(
                    ResultData.Failed(context.getString(R.string.scan_fail),
                        ScanError(context.getString(R.string.scan_fail))
                    ))
            }

            override fun onDenied() {
                // Processing for recognition request deny scenarios, for example, the camera is unavailable.
                callback.invoke(
                    ResultData.Failed(context.getString(R.string.scan_denied),
                        ScanError(context.getString(R.string.scan_denied),
                            ScanError.ScanErrorType.DENIED
                        )
                    ))
            }
        })
    }
}