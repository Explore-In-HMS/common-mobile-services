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

package com.hms.lib.commonmobileservices.push.services

import android.content.Context
import android.text.TextUtils
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.push.model.Provider
import com.hms.lib.commonmobileservices.push.PushService
import com.hms.lib.commonmobileservices.push.model.Token
import com.huawei.agconnect.AGConnectOptionsBuilder
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import com.huawei.hms.push.HmsMessaging

/**
 * This class provides a service implementation for handling Huawei push notifications.
 * @param context The application context.
 */
class HuaweiPushServiceImpl(private val context: Context) : PushService(context) {
    companion object {
        private const val TAG = "HuaweiPushServiceImpl"
    }

    private val hmsMessaging = HmsMessaging.getInstance(context)

    /**
     * Enables or disables auto initialization of Huawei Push Kit.
     * @param enable True to enable auto initialization, false to disable.
     */
    override fun autoInitEnabled(enable: Boolean) {
        hmsMessaging.isAutoInitEnabled = enable
    }

    /**
     * Retrieves the token associated with the Huawei push service.
     * @return A Work object representing the asynchronous token retrieval process.
     */
    override fun getToken(): Work<Token> {
        val work: Work<Token> = Work()
        object : Thread() {
            override fun run() {
                try {
                    val appId: String =
                        AGConnectOptionsBuilder().build(context).getString("client/app_id")
                    val token: String = HmsInstanceId.getInstance(context).getToken(appId, "HCM")
                    if (!TextUtils.isEmpty(token)) {
                        work.onSuccess(Token(Provider.Huawei, token))
                    }
                } catch (e: ApiException) {
                    work.onFailure(e)
                }
            }
        }.start()

        return work
    }

    /**
     * Subscribes to a specified topic for receiving push notifications.
     * @param topic The topic to subscribe to.
     * @return A Work object representing the asynchronous subscription process.
     */
    override fun subscribeToTopic(topic: String): Work<Unit> {
        val work: Work<Unit> = Work()
        hmsMessaging.subscribe(topic)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(it) }
            .addOnCanceledListener { work.onCanceled() }
        return work
    }

    /**
     * Unsubscribes from a specified topic for receiving push notifications.
     * @param topic The topic to unsubscribe from.
     * @return A Work object representing the asynchronous unsubscription process.
     */
    override fun unsubscribeFromTopic(topic: String): Work<Unit> {
        val work: Work<Unit> = Work()
        hmsMessaging.unsubscribe(topic)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(it) }
            .addOnCanceledListener { work.onCanceled() }
        return work
    }

    /**
     * Checks if auto initialization of Huawei Push Kit is enabled.
     * @return True if auto initialization is enabled, false otherwise.
     */
    override fun isAutoInitEnabled(): Boolean {
        return hmsMessaging.isAutoInitEnabled
    }
}
