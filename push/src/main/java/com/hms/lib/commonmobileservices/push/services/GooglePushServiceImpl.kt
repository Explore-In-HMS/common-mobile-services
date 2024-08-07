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
import com.google.firebase.messaging.FirebaseMessaging
import com.hms.lib.commonmobileservices.core.Work
import com.hms.lib.commonmobileservices.push.model.Provider
import com.hms.lib.commonmobileservices.push.PushService
import com.hms.lib.commonmobileservices.push.model.Token

/**
 * This class provides a service implementation for handling Google push notifications.
 * @param context The application context.
 */
class GooglePushServiceImpl(context: Context) : PushService(context) {
    companion object {
        private const val TAG = "GooglePushServiceImpl"
    }

    private val firebaseMessaging = FirebaseMessaging.getInstance()

    /**
     * Enables or disables auto initialization of Firebase Cloud Messaging.
     * @param enable True to enable auto initialization, false to disable.
     */
    override fun autoInitEnabled(enable: Boolean) {
        firebaseMessaging.isAutoInitEnabled = enable
    }

    /**
     * Retrieves the token associated with the Google push service.
     * @return A Work object representing the asynchronous token retrieval process.
     */
    override fun getToken(): Work<Token> {
        val work: Work<Token> = Work()
        firebaseMessaging.token
            .addOnSuccessListener { work.onSuccess(Token(Provider.Google, it)) }
            .addOnFailureListener { work.onFailure(it) }
            .addOnCanceledListener { work.onCanceled() }
        return work
    }

    /**
     * Subscribes to a specified topic for receiving push notifications.
     * @param topic The topic to subscribe to.
     * @return A Work object representing the asynchronous subscription process.
     */
    override fun subscribeToTopic(topic: String): Work<Unit> {
        val work: Work<Unit> = Work()
        firebaseMessaging.subscribeToTopic(topic)
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
        firebaseMessaging.unsubscribeFromTopic(topic)
            .addOnSuccessListener { work.onSuccess(Unit) }
            .addOnFailureListener { work.onFailure(it) }
            .addOnCanceledListener { work.onCanceled() }
        return work
    }

    /**
     * Checks if auto initialization of Firebase Cloud Messaging is enabled.
     * @return True if auto initialization is enabled, false otherwise.
     */
    override fun isAutoInitEnabled(): Boolean {
        return firebaseMessaging.isAutoInitEnabled
    }
}
