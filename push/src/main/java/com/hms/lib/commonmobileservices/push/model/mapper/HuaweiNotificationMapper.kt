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

class HuaweiNotificationMapper: Mapper<RemoteMessage.Notification, PushMessage.Notification>() {
    override fun map(from: RemoteMessage.Notification): PushMessage.Notification =
        PushMessage.Notification(
            title = from.title,
            titleLocalizationKey = from.titleLocalizationKey,
            titleLocalizationArgs = from.titleLocalizationArgs,
            body = from.body,
            bodyLocalizationKey = from.bodyLocalizationKey,
            bodyLocalizationArgs = from.bodyLocalizationArgs,
            icon = from.icon,
            imageUrl = from.imageUrl,
            sound = from.sound,
            tag = from.tag,
            color = from.color,
            clickAction = from.clickAction,
            channelId = from.channelId,
            link = from.link,
            ticker = from.ticker,
            notificationPriority = from.importance,
            visibility = from.visibility,
            lightSettings = from.lightSettings,
            localOnly = from.isLocalOnly,
            defaultSound = from.isDefaultSound,
            vibrateTimings = from.vibrateConfig
        )
}
