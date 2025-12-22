package com.vasberc.musicplayer.player.music_model_mapper

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.vasberc.domain.model.MusicModel

internal fun MusicModel.asMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setUri(filePath)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setArtist(artist)
                .setDisplayTitle(title)
                .setAlbumTitle(album)
                .setDurationMs(duration)
                .build()
        )
        .build()
}