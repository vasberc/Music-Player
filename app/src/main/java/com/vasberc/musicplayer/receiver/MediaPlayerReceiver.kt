package com.vasberc.musicplayer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vasberc.musicplayer.service.MyMediaBrowserService

class MediaPlayerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, MyMediaBrowserService::class.java).apply {
            action = intent.action
            putExtra(MyMediaBrowserService.SONG_INDEX, intent.getIntExtra(MyMediaBrowserService.SONG_INDEX, 0))
            setPackage(context.packageName)
        }
        context.startService(serviceIntent)
    }
}