package com.vasberc.presentation.utils

import androidx.media3.session.MediaSession
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.model.MusicModel
import kotlinx.coroutines.flow.StateFlow

interface MusicPlayer {
    fun setCurrentFolder(folderModel: FolderModel)
    fun initMediaPlayerAndStart(mediaSessionListener: MediaSessionListener, intExtra: Int)
    fun actionPlay(index: Int)
    fun release()
    fun start()
    fun pause(fromUser: Boolean)

    val playingSongIndex: StateFlow<MusicModel?>
    val mediaSession: MediaSession?
    val isPlaying: StateFlow<Boolean>
}