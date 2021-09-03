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
package com.hms.lib.commonmobileservices.scene.arview.service

import android.content.Context
import com.hms.lib.commonmobileservices.scene.arview.IArView
import com.hms.lib.commonmobileservices.core.MobileServiceType

class ArViewFactory {
    companion object {
        fun createAndGetArView(
            context: Context,
            type: MobileServiceType
        ): IArView? {
            return if (type == MobileServiceType.HMS) {
                HuaweiArView(
                    context
                )
            } else {
                // You can add your other existing services here
                return null
            }
        }
    }
}