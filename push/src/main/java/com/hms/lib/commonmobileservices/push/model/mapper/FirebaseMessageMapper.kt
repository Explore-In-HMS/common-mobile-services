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

package com.hms.lib.commonmobileservices.push.model.mapper

import com.google.firebase.messaging.RemoteMessage
import com.hms.lib.commonmobileservices.push.model.Mapper
import com.hms.lib.commonmobileservices.push.model.PushMessage

/**
 * This class is responsible for mapping Firebase RemoteMessage objects to PushMessage objects.
 * It implements the Mapper interface with RemoteMessage as the input and PushMessage as the output.
 * It uses the FirebaseNotificationMapper class to map the notification portion of the RemoteMessage.
 */
class FirebaseMessageMapper : Mapper<RemoteMessage, PushMessage>() {

    /**
     * The notification mapper used to map the notification portion of the RemoteMessage.
     */
    private val notificationMapper: FirebaseNotificationMapper = FirebaseNotificationMapper()

    /**
     * Maps a RemoteMessage object to a PushMessage object.
     *
     * @param from The RemoteMessage object to map.
     * @return The mapped PushMessage object.
     */
    override fun map(from: RemoteMessage): PushMessage =
        PushMessage(
            data = from.data,
            senderId = from.senderId,
            from = from.from,
            to = from.to,
            collapseKey = from.collapseKey,
            messageId = from.messageId,
            messageType = from.messageType,
            sentTime = from.sentTime,
            ttl = from.ttl,
            originalPriority = from.originalPriority,
            priority = from.priority,
            notification = notificationMapper.map(from.notification!!)
        )

}
