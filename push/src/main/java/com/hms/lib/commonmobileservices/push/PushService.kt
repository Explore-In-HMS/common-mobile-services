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

/**
 * This abstract class represents a push notification service.
 *
 * @param context The context of the application.
 */
abstract class PushService(private val context: Context) {
    /**
     * Initializes the push service.
     *
     * @param autoInitEnabled Whether auto initialization is enabled or not.
     */
    fun initialize(autoInitEnabled: Boolean = false) {
        autoInitEnabled(autoInitEnabled)
    }

    /**
     * Enables or disables auto initialization.
     *
     * @param enable Whether to enable or disable auto initialization.
     */
    abstract fun autoInitEnabled(enable: Boolean)

    /**
     * Gets the token for the push service.
     *
     * @return A Work object containing the token.
     */
    abstract fun getToken(): Work<Token>

    /**
     * Subscribes to a topic for push notifications.
     *
     * @param topic The topic to subscribe to.
     * @return A Work object indicating the success or failure of the subscription.
     */
    abstract fun subscribeToTopic(topic: String): Work<Unit>

    /**
     * Unsubscribes from a topic for push notifications.
     *
     * @param topic The topic to unsubscribe from.
     * @return A Work object indicating the success or failure of the unsubscription.
     */
    abstract fun unsubscribeFromTopic(topic: String): Work<Unit>

    /**
     * Gets whether auto initialization is enabled or not.
     *
     * @return Whether auto initialization is enabled or not.
     */
    abstract fun isAutoInitEnabled(): Boolean

    /**
     * Companion object that provides a way to get an instance of the push service.
     */
    companion object {
        /**
         * Gets an instance of the push service.
         *
         * @param context The context of the application.
         * @return An instance of the push service.
         */
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
