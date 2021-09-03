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
package com.hms.lib.commonmobileservices.push.slider.receivers

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hms.lib.commonmobileservices.push.R

class SliderPushNotificationReceiver : BroadcastReceiver() {

    private val TAG = "SliderPushNotification"
    private var channelId: String? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != null) {
            channelId = intent.getStringExtra("channel_id")
            when (intent.action) {
                "prev" -> {
                    updateNotification(context, "prev")
                }
                "next" -> {
                    updateNotification(context, "next")
                }
            }
        }

        if (intent.hasExtra("url")) {
            val url = intent.getStringExtra("url")
            if (url != null) {
                if (url.startsWith("http")) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    browserIntent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    context.startActivity(browserIntent)
                } else {
                    Log.e(TAG, "Url is not start with 'http'")
                }
            } else {
                Log.e(TAG, "Url is null")
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Cancel Notification
                val notificationManager = NotificationManagerCompat.from(context)
                val notificationChannel = notificationManager.getNotificationChannel(channelId!!)
                if (notificationChannel != null) {
                    notificationChannel.importance = NotificationManager.IMPORTANCE_HIGH
                    notificationManager.createNotificationChannel(notificationChannel)
                    Log.d(TAG, "Notification Channel set HIGH")
                }
                notificationManager.cancel(1)

                // Close Notification Bar
                val closeIntent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                context.sendBroadcast(closeIntent)
            }
        }
    }

    @SuppressLint("WrongConstant")
    fun updateNotification(context: Context, action: String) {
        var notificationManager: NotificationManager? = null
        var notification: Notification? = null

        // RemoteView
        val expandedViewFlipper = RemoteViews(context.packageName, R.layout.notification_expanded)
        if (action == "next") {
            expandedViewFlipper.showNext(R.id.viewFlipperExpandedSPN)
        } else {
            expandedViewFlipper.showPrevious(R.id.viewFlipperExpandedSPN)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Notification
            notification = NotificationCompat.Builder(context, channelId!!)
                .setSmallIcon(
                    context.packageManager.getApplicationInfo(
                        context.packageName,
                        1
                    ).icon
                )
                .setCustomBigContentView(expandedViewFlipper)
                .build()
            notificationManager = context.getSystemService(NotificationManager::class.java)
            val notificationChannel = notificationManager.getNotificationChannel(channelId)
            if (notificationChannel != null) {
                notificationChannel.importance = NotificationManager.IMPORTANCE_LOW
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
        notificationManager?.notify(1, notification)
    }
}