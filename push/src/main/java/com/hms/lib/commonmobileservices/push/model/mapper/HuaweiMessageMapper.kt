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

import com.hms.lib.commonmobileservices.push.model.Mapper
import com.hms.lib.commonmobileservices.push.model.PushMessage
import com.huawei.hms.push.RemoteMessage

/**
 * This class is a mapper that maps a RemoteMessage object to a PushMessage object.
 * It implements the Mapper interface with RemoteMessage as input and PushMessage as output.
 * It also contains a HuaweiNotificationMapper object to map the notification field of RemoteMessage.
 */
class HuaweiMessageMapper: Mapper<RemoteMessage, PushMessage>() {

    /**
     * An instance of HuaweiNotificationMapper to map the notification field of RemoteMessage.
     */
    private val notificationMapper: HuaweiNotificationMapper = HuaweiNotificationMapper()

    /**
     * This method maps a RemoteMessage object to a PushMessage object.
     * @param from The RemoteMessage object to be mapped.
     * @return The mapped PushMessage object.
     */
    override fun map(from: RemoteMessage): PushMessage =
        PushMessage(
            data = from.dataOfMap,
            from = from.from,
            to = from.to,
            collapseKey = from.collapseKey,
            messageId = from.messageId,
            messageType = from.messageType,
            sentTime = from.sentTime,
            ttl = from.ttl,
            originalPriority = from.originalUrgency,
            priority = from.urgency,
            notification = notificationMapper.map(from.notification!!)
        )
}
