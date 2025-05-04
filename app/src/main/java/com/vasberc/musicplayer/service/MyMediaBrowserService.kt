package com.vasberc.musicplayer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.vasberc.musicplayer.notification.NotificationManager
import com.vasberc.presentation.utils.MediaSessionListener
import com.vasberc.presentation.utils.MusicPlayer
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class MyMediaBrowserService : Service(), MediaSessionListener {

    private val musicPlayer: MusicPlayer by inject()
    private val notificationManager: NotificationManager by inject { parametersOf(this) }

    override fun onCreate() {
        super.onCreate()
        Timber.d("MediaBrowserService onCreate")
        notificationManager.createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("MediaBrowserService onStartCommand")
        val action = intent?.action
        when (action) {
            ACTION_PLAY -> {
                musicPlayer.initMediaPlayerAndStart(this, intent.getIntExtra(SONG_INDEX, 0))
            }
            ACTION_PAUSE -> {
                musicPlayer.pause(true)
                notificationManager.updateNotification(musicPlayer)

            }
            ACTION_RESUME -> {
                musicPlayer.start()
                notificationManager.updateNotification(musicPlayer)
            }
            ACTION_STOP -> {
                stopForegroundService()
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Timber.d("MediaBrowserService onDestroy")
        super.onDestroy()
        musicPlayer.release()
    }

    private fun startForegroundService() {
        val notification = notificationManager.createNotification(musicPlayer)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stopForegroundService() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onMusicStarted() {
        startForegroundService()
    }

    override fun onFinish() {
        stopForegroundService()
    }

    override fun onSongChanged() {
        notificationManager.updateNotification(musicPlayer)
    }

    companion object {
        const val ACTION_PLAY: String = "com.vasberc.action.PLAY"
        const val ACTION_PAUSE: String = "com.vasberc.action.PAUSE"
        const val ACTION_RESUME: String = "com.vasberc.action.RESUME"
        const val ACTION_STOP: String = "com.vasberc.action.STOP"
        const val SONG_INDEX: String = "SONG_INDEX"
        const val NOTIFICATION_ID: Int = 1
    }
}