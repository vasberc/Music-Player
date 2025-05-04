package com.vasberc.musicplayer.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaStyleNotificationHelper
import com.vasberc.musicplayer.R
import com.vasberc.musicplayer.service.MyMediaBrowserService
import com.vasberc.musicplayer.service.MyMediaBrowserService.Companion.ACTION_STOP
import com.vasberc.musicplayer.service.MyMediaBrowserService.Companion.NOTIFICATION_ID
import com.vasberc.presentation.utils.MusicPlayer
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
class NotificationManager(
    @InjectedParam
    private val myMediaBrowserService: MyMediaBrowserService
) {

    fun updateNotification(musicPlayer: MusicPlayer) {
        val notification = createNotification(musicPlayer)
        val notificationManager = myMediaBrowserService.getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    @OptIn(UnstableApi::class)
    fun createNotification(musicPlayer: MusicPlayer): Notification {
        val mediaSession = musicPlayer.mediaSession!!

        val stopPendingIntent = getStopPendingIntent()

        return NotificationCompat.Builder(myMediaBrowserService, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setStyle(
                MediaStyleNotificationHelper.MediaStyle(mediaSession)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setDeleteIntent(stopPendingIntent)
            .build()
    }

    private fun getStopPendingIntent(): PendingIntent? {
        val intent = Intent(ACTION_STOP).apply {
            setPackage(myMediaBrowserService.packageName)
        }

        val stopPendingIntent = PendingIntent.getBroadcast(
            myMediaBrowserService, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return stopPendingIntent
    }

    fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Music Playback",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = myMediaBrowserService.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID: String = "CHANNEL_ID"
    }
}