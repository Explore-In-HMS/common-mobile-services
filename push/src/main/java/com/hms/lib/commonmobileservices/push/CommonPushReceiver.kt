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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hms.lib.commonmobileservices.push.model.MessageType
import com.hms.lib.commonmobileservices.push.model.PushMessage
import com.hms.lib.commonmobileservices.push.model.Token

private const val TAG = "CommonPushReceiver"

/**
 * A base BroadcastReceiver class for handling common push notification events.
 * Extend this class to implement custom handling for push notification events.
 */
open class CommonPushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action.equals("com.commonmobileservices.action.MESSAGING_EVENT")) {
                it.extras?.let { bundle ->
                    val type = bundle["message_type"]
                    val data: Bundle = bundle["data"] as Bundle
                    when (type) {
                        MessageType.NewToken.value -> {
                            onNewToken(data["token"] as Token)
                        }
                        MessageType.MessageReceived.value -> {
                            onMessageReceived(data["message"] as PushMessage)
                        }
                        MessageType.DeletedMessages.value -> {
                            onDeletedMessages()
                        }
                        MessageType.MessageSent.value -> {
                            onMessageSent(data.getString("message_id")!!)
                        }
                        MessageType.SendError.value -> {
                            onSendError(
                                data.getString("message_id")!!,
                                data.getSerializable("exception") as Exception
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Called when a push message is received.
     * Override this method to handle received push messages.
     * @param remoteMessage The received push message.
     */
    open fun onMessageReceived(remoteMessage: PushMessage) {
    }

    /**
     * Called when messages are deleted from the server.
     * Override this method to handle deleted messages event.
     */
    open fun onDeletedMessages() {
    }

    /**
     * Called when a message is successfully sent to the server.
     * Override this method to handle message sent event.
     * @param messageId The ID of the sent message.
     */
    open fun onMessageSent(messageId: String) {
    }

    /**
     * Called when an error occurs while sending a message.
     * Override this method to handle message send error event.
     * @param messageId The ID of the message.
     * @param exception The exception that occurred during send.
     */
    open fun onSendError(messageId: String, exception: Exception) {
    }

    /**
     * Called when a new token is generated.
     * Override this method to handle token generation event.
     * @param token The new token generated.
     */
    open fun onNewToken(token: Token) {
    }
}