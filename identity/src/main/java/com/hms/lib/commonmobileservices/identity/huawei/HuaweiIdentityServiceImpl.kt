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
package com.hms.lib.commonmobileservices.identity.huawei

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.util.Log
import com.hms.lib.commonmobileservices.identity.IdentityService
import com.hms.lib.commonmobileservices.identity.common.Mapper
import com.hms.lib.commonmobileservices.identity.model.UserAddressResponse
import com.huawei.hms.identity.Address
import com.huawei.hms.identity.entity.GetUserAddressResult
import com.huawei.hms.identity.entity.UserAddress
import com.huawei.hms.identity.entity.UserAddressRequest
import com.huawei.hms.support.api.client.Status

class HuaweiIdentityServiceImpl(private val context: Context) : IdentityService {

    override fun getUserAddress() {

        val task = Address.getAddressClient(context).getUserAddress(UserAddressRequest())
        task.apply {
            addOnSuccessListener {
                Log.i(IdentityService.TAG, "HMS - onSuccess result code: ${it.returnCode}")

                try {
                    val status: Status = it.status
                    val activity = (context as Activity)

                    if (result.returnCode == 0 && status.hasResolution()) {

                        Log.i(IdentityService.TAG, "HMS - the result has resolution")
                        status.startResolutionForResult(
                            activity,
                            IdentityService.requestCode
                        )

                    } else {
                        Log.i(IdentityService.TAG, "HMS - the result hasn't resolution")
                    }

                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }

            }.addOnFailureListener {
                Log.e(IdentityService.TAG, "HMS - onFailed resultCode: ${it.message}")
            }
        }

    }
}