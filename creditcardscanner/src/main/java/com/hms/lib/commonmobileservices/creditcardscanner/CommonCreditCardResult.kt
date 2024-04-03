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

import android.graphics.Bitmap
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult

/**
 * Represents the result of a credit card scanning operation.
 *
 * @property cardNumber The card number extracted from the scanned image.
 * @property issuer The issuer of the credit card.
 * @property expireDate The expiration date of the credit card.
 * @property organization The organization associated with the credit card.
 * @property type The type of the credit card.
 * @property originalBitmap The original bitmap image of the scanned credit card.
 * @property numberBitmap The bitmap image of the extracted card number.
 * @property errorCode The error code, if any, encountered during the scanning process.
 */
data class CommonCreditCardResult(
    val cardNumber: String? = null,
    val issuer: String? = null,
    val expireDate: String? = null,
    val organization: String? = null,
    val type: String? = null,
    val originalBitmap: Bitmap? = null,
    val numberBitmap: Bitmap? = null,
    val errorCode: Int? = null
)

/**
 * Maps an [MLBcrCaptureResult] to a [CommonCreditCardResult].
 *
 * @return The mapped [CommonCreditCardResult].
 */
fun MLBcrCaptureResult.mapToCommonCreditCardResult(): CommonCreditCardResult =
    CommonCreditCardResult(
        cardNumber = this.number,
        issuer = this.issuer,
        expireDate = this.expire,
        organization = this.organization,
        type = this.type,
        originalBitmap = this.originalBitmap,
        numberBitmap = this.numberBitmap,
        errorCode = this.errorCode
    )