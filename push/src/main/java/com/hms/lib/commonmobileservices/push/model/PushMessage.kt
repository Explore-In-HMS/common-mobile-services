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

package com.hms.lib.commonmobileservices.push.model

import android.net.Uri
import java.io.Serializable

/**
 * Represents a push message containing notification data.
 * @property data Optional map containing additional data sent with the message.
 * @property senderId The ID of the sender.
 * @property from The sender of the message.
 * @property to The recipient of the message.
 * @property collapseKey The collapse key for grouping messages.
 * @property messageId The ID of the message.
 * @property messageType The type of the message.
 * @property sentTime The time when the message was sent.
 * @property ttl The time-to-live of the message.
 * @property originalPriority The original priority of the message.
 * @property priority The priority of the message.
 * @property notification The notification data associated with the message.
 */
data class PushMessage(
    val data: Map<String, String>? = null,
    val senderId: String? = null,
    val from: String? = null,
    val to: String? = null,
    val collapseKey: String? = null,
    val messageId: String? = null,
    val messageType: String? = null,
    val sentTime: Long = 0,
    val ttl: Int = 0,
    val originalPriority: Int = 0,
    val priority: Int = 0,
    val notification: Notification
) : Serializable {
    companion object {
        const val PRIORITY_UNKNOWN = 0
        const val PRIORITY_HIGH = 1
        const val PRIORITY_NORMAL = 2
    }

    /**
     * Represents the notification data of a push message.
     * @property title The title of the notification.
     * @property titleLocalizationKey The key for localized title string.
     * @property titleLocalizationArgs Arguments for formatting the localized title string.
     * @property body The body of the notification.
     * @property bodyLocalizationKey The key for localized body string.
     * @property bodyLocalizationArgs Arguments for formatting the localized body string.
     * @property icon The icon for the notification.
     * @property imageUrl The URL of the image for the notification.
     * @property sound The sound to play for the notification.
     * @property tag The tag for the notification.
     * @property color The color of the notification.
     * @property clickAction The action triggered when the notification is clicked.
     * @property channelId The channel ID of the notification.
     * @property link The link associated with the notification.
     * @property ticker The ticker text of the notification.
     * @property notificationPriority The priority of the notification.
     * @property visibility The visibility of the notification.
     * @property notificationCount The count of notifications.
     * @property lightSettings The settings for notification light.
     * @property eventTime The time of the event associated with the notification.
     * @property sticky Whether the notification is sticky.
     * @property localOnly Whether the notification is for local use only.
     * @property defaultSound Whether the default sound is used for the notification.
     * @property defaultVibrateSettings Whether default vibrate settings are used for the notification.
     * @property defaultLightSettings Whether default light settings are used for the notification.
     * @property vibrateTimings The timings for vibration of the notification.
     */
    data class Notification(
        val title: String? = null,
        val titleLocalizationKey: String? = null,
        val titleLocalizationArgs: Array<String>? = null,
        val body: String? = null,
        val bodyLocalizationKey: String? = null,
        val bodyLocalizationArgs: Array<String>? = null,
        val icon: String? = null,
        val imageUrl: Uri? = null,
        val sound: String? = null,
        val tag: String? = null,
        val color: String? = null,
        val clickAction: String? = null,
        val channelId: String? = null,
        val link: Uri? = null,
        val ticker: String? = null,
        val notificationPriority: Int? = null,
        val visibility: Int? = null,
        val notificationCount: Int? = null,
        val lightSettings: IntArray? = null,
        val eventTime: Long? = null,
        val sticky: Boolean = false,
        val localOnly: Boolean = false,
        val defaultSound: Boolean = false,
        val defaultVibrateSettings: Boolean = false,
        val defaultLightSettings: Boolean = false,
        val vibrateTimings: LongArray? = null,
    ) : Serializable
}
