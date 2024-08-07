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
import android.content.Intent
import android.os.Bundle
import com.hms.lib.commonmobileservices.push.model.MessageType

/**
 * The BroadcastHelper class provides a convenient way to send broadcast messages with a specified message type and optional data bundle.
 */
object BroadcastHelper {
    /**
     * Sends a broadcast message with the specified message type and optional data bundle.
     *
     * @param context The context used to send the broadcast.
     * @param messageType The type of message to be sent.
     * @param bundle An optional data bundle to be included in the broadcast message.
     */
    fun sendMessage(context: Context, messageType: MessageType, bundle: Bundle? = null) {
        val intent = Intent("com.commonmobileservices.action.MESSAGING_EVENT")
        intent.putExtra("data", bundle)
        intent.putExtra("message_type", messageType.value)
        intent.setPackage(context.packageName)
        context.sendBroadcast(intent)
    }
}