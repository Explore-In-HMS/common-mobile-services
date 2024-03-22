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

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.hms.lib.commonmobileservices.push.BroadcastHelper
import com.hms.lib.commonmobileservices.push.model.MessageType
import com.hms.lib.commonmobileservices.push.model.Provider
import com.hms.lib.commonmobileservices.push.model.PushMessage
import com.hms.lib.commonmobileservices.push.model.Token
import com.hms.lib.commonmobileservices.push.model.mapper.HuaweiMessageMapper
import com.hms.lib.commonmobileservices.push.slider.SliderPushNotificationUtil
import com.hms.lib.commonmobileservices.push.slider.models.SliderPushNotification
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import org.json.JSONObject

/**
 * This class represents a Huawei Push Service that extends the HmsMessageService class.
 * It handles new token, message received, deleted messages, message sent, and send error events.
 */
class HMSPushService : HmsMessageService() {
    /**
     * The companion object that holds the constant TAG value.
     */
    companion object {
        private const val TAG = "HMSPushService"
    }

    /**
     * This method is called when a new token is received.
     * It sends a broadcast message with the new token.
     * @param token The new token received.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        BroadcastHelper.sendMessage(
            this,
            MessageType.NewToken,
            Bundle().also { it.putSerializable("token", Token(Provider.Huawei, token)) })
    }

    /**
     * This method is called when a new message is received.
     * It checks if the message is a slider push notification or not.
     * If it is not a slider push notification, it sends a broadcast message with the received message.
     * If it is a slider push notification, it shows the notification.
     * @param message The received message.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.dataOfMap.isNotEmpty()) {
            val data = message.dataOfMap
            if (data["slider"].isNullOrEmpty() || !data["slider"].equals("true")) {
                val remoteMessage: PushMessage = HuaweiMessageMapper().map(message)
                BroadcastHelper.sendMessage(
                    this,
                    MessageType.MessageReceived,
                    Bundle().also { it.putSerializable("message", remoteMessage) })
            } else {
                try {
                    val jsonData = JSONObject(message.data.toString())
                    val sliderPushNotificationMainModel: SliderPushNotification =
                        Gson().fromJson(
                            jsonData.toString(),
                            SliderPushNotification::class.java
                        )
                    SliderPushNotificationUtil.show(this, sliderPushNotificationMainModel)
                } catch (e: Exception) {
                    Log.e(TAG, "Exception: ${e.message}")
                }
            }
        }
    }

    /**
     * This method is called when messages are deleted from the server.
     * It sends a broadcast message to notify the deletion.
     */
    override fun onDeletedMessages() {
        super.onDeletedMessages()
        BroadcastHelper.sendMessage(this, MessageType.DeletedMessages)
    }

    /**
     * This method is called when a message is successfully sent.
     * It sends a broadcast message with the message ID.
     * @param messageId The ID of the message sent.
     */
    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        BroadcastHelper.sendMessage(
            this,
            MessageType.MessageSent,
            Bundle().also { it.putString("message_id", p0) })
    }

    /**
     * This method is called when there is an error sending a message.
     * It sends a broadcast message with the message ID and the exception.
     * @param messageId The ID of the message that failed to send.
     * @param exception The exception that occurred.
     */
    override fun onSendError(p0: String, p1: Exception) {
        super.onSendError(p0, p1)
        BroadcastHelper.sendMessage(
            this,
            MessageType.SendError,
            Bundle().also {
                it.putString("message_id", p0)
                it.putSerializable("exception", p1)
            })
    }
}