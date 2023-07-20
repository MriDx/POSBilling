package dev.mridx.delayed_uploader.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dev.mridx.delayed_uploader.db.entity.model.NotificationDataModel

object NotificationUtils {

    private val CHANNEL_ID = "DUNotificationChannel"
    private val CHANNEL_NAME = "DUNotification"

    fun newNotification(context: Context, data: NotificationDataModel) {
        val notificationManager = NotificationManagerCompat.from(context.applicationContext)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            setupChannel(notificationManager)
        notificationManager.notify(data.notificationId, createNotification(context, data))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupChannel(notificationManager: NotificationManagerCompat) {
        val adminChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        adminChannel.description = "This is main notification for Delayed Uploader."
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager.createNotificationChannel(adminChannel)
    }

    private fun createNotification(context: Context, data: NotificationDataModel): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(data.icon)
            setContentTitle(data.title)
            setContentText(data.body)
            setAutoCancel(data.autoCancel)
            setLights(Color.RED, 1, 1)
            setStyle(NotificationCompat.BigTextStyle().bigText(data.body))
        }.build()
    }


}