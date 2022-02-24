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

package com.hms.lib.commonmobileservices.push

import android.content.Context
import com.hms.lib.commonmobileservices.core.Device
import com.hms.lib.commonmobileservices.core.MobileServiceType
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.push.model.Token
import com.hms.lib.commonmobileservices.push.services.GooglePushServiceImpl
import com.hms.lib.commonmobileservices.push.services.HuaweiPushServiceImpl

private lateinit var INSTANCE: PushService

abstract class PushService(private val context: Context) {
    fun initialize(autoInitEnabled: Boolean = false) {
        autoInitEnabled(autoInitEnabled)
    }

    abstract fun autoInitEnabled(enable: Boolean)
    abstract fun getToken(): Work<Token>
    abstract fun subscribeToTopic(topic: String): Work<Unit>
    abstract fun unsubscribeFromTopic(topic: String): Work<Unit>
    abstract fun isAutoInitEnabled(): Boolean

    companion object {
        fun getInstance(context: Context): PushService {
            synchronized(PushService::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = if (Device.getMobileServiceType(context) == MobileServiceType.GMS) {
                        GooglePushServiceImpl(context)
                    } else {
                        HuaweiPushServiceImpl(context)
                    }
                }
                return INSTANCE
            }
        }
    }
}
