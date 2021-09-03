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
package com.hms.lib.commonmobileservices.scene.faceview.service

import android.content.Context
import com.huawei.hms.scene.sdk.common.SdkType
import com.hms.lib.commonmobileservices.scene.faceview.IAugmentedFaceView
import com.hms.lib.commonmobileservices.core.MobileServiceType

class FaceViewFactory {
    companion object {
        fun createAndGetAugmentedFaceView(
            context: Context,
            type: MobileServiceType
        ): IAugmentedFaceView? {
            return if (type == MobileServiceType.HMS) {
                HuaweiAugmentedFaceView(
                    context,
                    SdkType.ML_KIT
                )
            } else {
                // You can add your other existing services here
                return null
            }
        }
    }
}
