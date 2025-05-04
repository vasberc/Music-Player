package com.vasberc.musicplayer.player

import android.content.Context
import android.content.Intent
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.model.MusicModel
import com.vasberc.musicplayer.service.MyMediaBrowserService
import com.vasberc.presentation.utils.MediaSessionListener
import com.vasberc.presentation.utils.MusicPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Single

@UnstableApi
@Single
internal class MusicPlayerImpl(
    private val context: Context
) : MusicPlayer, Player.Listener {

    private val playbackAttributes
        get() = AudioAttributes.Builder()
            .setUsage(USAGE_MEDIA)
            .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
            .build()

    private val currentPosition: Int
        get() = mediaSession?.player?.currentMediaItemIndex ?: 0

    override var mediaSession: MediaSession? = null
        private set
    private var mediaSessionListener: MediaSessionListener? = null

    private var currentFolder: FolderModel? = null

    private val _playingSongIndex: MutableStateFlow<MusicModel?> = MutableStateFlow(null)
    override val playingSongIndex = _playingSongIndex.asStateFlow()

    private fun iniSession() {
        val player = ExoPlayer.Builder(context).build()
        mediaSession = MediaSession.Builder(context, player).build()
        mediaSession?.player?.apply {
            setAudioAttributes(this@MusicPlayerImpl.playbackAttributes, true)
            prepare()
            addListener(this@MusicPlayerImpl)
        }
    }

    override fun setCurrentFolder(folderModel: FolderModel) {
        if (mediaSession == null) {
            iniSession()
        }
        if (currentFolder != folderModel) {
            currentFolder = folderModel
            mediaSession?.player?.apply {
                setMediaItems(currentFolder?.files?.map { it.asMediaItem() } ?: listOf())
            }
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        if (reason != Player.MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED) {
            mediaSessionListener?.onSongChanged()
        }
        _playingSongIndex.update { currentFolder?.files?.get(currentPosition) }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_ENDED) {
            mediaSessionListener?.onFinish()
            _playingSongIndex.update { null }
        }
    }

    override fun actionPlay(index: Int) {
        val intent = Intent(MyMediaBrowserService.ACTION_PLAY).apply {
            putExtra(MyMediaBrowserService.SONG_INDEX, index)
            setPackage(context.packageName)
        }
        context.sendBroadcast(intent)
    }

    override fun release() {
        currentFolder = null
        _playingSongIndex.update { null }
        mediaSession?.release()
        mediaSession?.player?.release()
        mediaSession = null
    }

    override fun start() {
        mediaSession?.player?.play()
    }

    override fun pause(fromUser: Boolean) {
        mediaSession?.player?.pause()
    }

    override fun initMediaPlayerAndStart(
        mediaSessionListener: MediaSessionListener,
        songIndex: Int
    ) {
        this.mediaSessionListener = mediaSessionListener
        mediaSession?.player?.apply {
            seekToDefaultPosition(songIndex)
            play()
            mediaSessionListener.onMusicStarted()
        }
    }

}