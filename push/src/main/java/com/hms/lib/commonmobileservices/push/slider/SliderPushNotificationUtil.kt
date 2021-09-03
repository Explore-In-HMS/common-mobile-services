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
package com.hms.lib.commonmobileservices.push.slider

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.hms.lib.commonmobileservices.push.R
import com.hms.lib.commonmobileservices.push.slider.models.SliderItem
import com.hms.lib.commonmobileservices.push.slider.models.SliderPushNotification
import com.hms.lib.commonmobileservices.push.slider.receivers.SliderPushNotificationReceiver
import com.squareup.picasso.Picasso
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


object SliderPushNotificationUtil {

    @SuppressLint("StaticFieldLeak")
    private val TAG = "SliderPushNotification"
    private var channelId: String? = null
    private var channelName: String? = "Slider Push Notification"
    private var notificationManager: NotificationManager? = null
    private var sliderPushNotificationItemModel: List<SliderItem>? = null

    @SuppressLint("WrongConstant")
    fun show(context: Context, sliderPushNotificationMainModel: SliderPushNotification) {

        try {

            StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitAll().build())

            // Notification Manager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager = context.getSystemService(NotificationManager::class.java)
            }
            notificationManager?.cancelAll()
            sliderPushNotificationItemModel = sliderPushNotificationMainModel.items
            channelId = getRandomString(4)

            // RemoteView for Collapsed & Expanded View
            val collapsedView = RemoteViews(context.packageName, R.layout.notification_collapsed)
            val expandedViewFlipper =
                RemoteViews(context.packageName, R.layout.notification_expanded)

            // Notification
            val notification = NotificationCompat.Builder(context, channelId!!)
                .setSmallIcon(
                    context.packageManager.getApplicationInfo(
                        context.packageName,
                        1
                    ).icon
                )
                .setContentTitle(sliderPushNotificationMainModel.header)
                .setContentText(sliderPushNotificationMainModel.callToAction)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedViewFlipper) //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build()

            // Collapsed Heading
            collapsedView.setTextViewText(
                R.id.textViewCollapsedHeaderSPN,
                sliderPushNotificationMainModel.header
            )

            // Collapsed Call To Action
            collapsedView.setTextViewText(
                R.id.textViewCollapsedContentSPN,
                sliderPushNotificationMainModel.callToAction
            )

            // Set Click Event to Next & Prev Buttons
            val buttonIntent = Intent(context, SliderPushNotificationReceiver::class.java)
            buttonIntent.putExtra("channel_id", channelId)
            buttonIntent.action = "prev"

            val prevPendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    buttonIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            expandedViewFlipper.setOnClickPendingIntent(
                R.id.buttonPreviousSPN,
                prevPendingIntent
            )

            buttonIntent.action = "next"
            val nextPendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    buttonIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            expandedViewFlipper.setOnClickPendingIntent(
                R.id.buttonNextSPN,
                nextPendingIntent
            )

            // Loop For Adding Campings to Flipper
            for (i in sliderPushNotificationItemModel!!.indices) {

                val notificationUniqueId = System.currentTimeMillis().toInt()

                // Campaing Model
                val campaing: SliderItem = sliderPushNotificationItemModel!![i]

                // Init
                val title: String = campaing.title
                val image: String = campaing.image
                val url: String = campaing.url

                // Create Item for Campings
                val item = RemoteViews(context.packageName, R.layout.notification_expanded_item)

                // Set Click Event
                val clickIntent = Intent(context, SliderPushNotificationReceiver::class.java)
                clickIntent.putExtra("title", title)
                clickIntent.putExtra("url", url)
                val clickPendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationUniqueId,
                    clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                item.setOnClickPendingIntent(R.id.imageViewExpandedItemSPN, clickPendingIntent)

                // Set Camping Title
                item.setTextViewText(R.id.textViewTitleSPN, title)

                // Set Camping Image
                try {
                    item.setImageViewBitmap(R.id.imageViewExpandedItemSPN, urlToBitmap(image))
                } catch (e: Exception) {
                    val builder = Picasso.Builder(context)
                    builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                    builder.build().load(image).into(
                        item,
                        R.id.imageViewExpandedItemSPN,
                        1,
                        notification
                    )
                }

                // Add Camping Item to Expanded Notification
                expandedViewFlipper.addView(R.id.viewFlipperExpandedSPN, item)
            }

            // Custom Notification Sound
            val soundUri =
                Uri.parse("android.resource://" + context.packageName + "/" + R.raw.custom_notification_sound)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create Notification Channel
                val notificationChannel =
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.setSound(soundUri, audioAttributes)
                notificationChannel.importance = NotificationManager.IMPORTANCE_HIGH

                // Set Notification Channel to NotificationManager
                notificationManager!!.createNotificationChannel(notificationChannel)
                notificationManager!!.createNotificationChannelGroup(
                    NotificationChannelGroup(
                        "1371",
                        "SPN"
                    )
                )

                // Set Notification
                notificationManager!!.notify(1, notification)


                // Delete Notification Olds Channel
                for (i in notificationManager!!.notificationChannels) {
                    if (!i.id.equals(channelId)) {
                        notificationManager!!.deleteNotificationChannel(i.id);
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun getRandomString(sizeOfRandomString: Int): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        val allowed_characters = "0123456789qwertyuiopasdfghjklzxcvbnm"
        for (i in 0 until sizeOfRandomString) sb.append(
            allowed_characters[random.nextInt(
                allowed_characters.length
            )]
        )
        return sb.toString()
    }

    private fun urlToBitmap(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitAll().build())
            //bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
            val url1 = URL(url)
            val conn = url1.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.doOutput = true
            conn.connect()
            val stream = conn.inputStream
            bitmap = BitmapFactory.decodeStream(stream)
        } catch (e: Exception) {
            Log.e(TAG, "urlToBitmap: ${e.message}")
            e.printStackTrace()
        }
        return bitmap
    }
}