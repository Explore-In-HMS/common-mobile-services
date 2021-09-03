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
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.hms.lib.commonmobileservices.push.BroadcastHelper
import com.hms.lib.commonmobileservices.push.model.MessageType
import com.hms.lib.commonmobileservices.push.model.Provider
import com.hms.lib.commonmobileservices.push.model.PushMessage
import com.hms.lib.commonmobileservices.push.model.Token
import com.hms.lib.commonmobileservices.push.model.mapper.FirebaseMessageMapper
import com.hms.lib.commonmobileservices.push.slider.SliderPushNotificationUtil
import com.hms.lib.commonmobileservices.push.slider.models.SliderPushNotification
import org.json.JSONObject

class GMSPushService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "GMSPushService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        BroadcastHelper.sendMessage(
            this,
            MessageType.NewToken,
            Bundle().also { it.putSerializable("token", Token(Provider.Google, token)) })
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            val data = message.data
            if (data["slider"].isNullOrEmpty() || !data["slider"].equals("true")) {
                val remoteMessage: PushMessage = FirebaseMessageMapper().map(message)
                BroadcastHelper.sendMessage(
                    this,
                    MessageType.MessageReceived,
                    Bundle().also { it.putSerializable("message", remoteMessage) })
            } else {
                try {
                    val data: JSONObject = JSONObject(data.toString())
                    val sliderPushNotificationMainModel: SliderPushNotification =
                        Gson().fromJson(
                            data.toString(),
                            SliderPushNotification::class.java
                        )
                    SliderPushNotificationUtil.show(this, sliderPushNotificationMainModel)
                } catch (e: Exception) {
                    Log.e(TAG, "Exception: ${e.message}")
                }
            }
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        BroadcastHelper.sendMessage(this, MessageType.DeletedMessages)
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        BroadcastHelper.sendMessage(
            this,
            MessageType.MessageSent,
            Bundle().also { it.putString("message_id", p0) })
    }

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