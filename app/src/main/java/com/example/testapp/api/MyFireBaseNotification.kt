package com.example.testapp.api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

open class MyFireBaseNotification : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {

        val notificationIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(p0?.data?.get("title"))
            .setSmallIcon(R.drawable.ic_baseline_tv_24)
            .setContentIntent(notificationIntent)
            .setContentText(p0?.data?.get("body"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle())
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel: NotificationChannel = NotificationChannel(
                "channel_id",
                "title_channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())

    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        getSharedPreferences("token", MODE_PRIVATE).edit().putString("fcm", p0).apply()
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences("token", MODE_PRIVATE).getString("fcm", "empty")
    }

}