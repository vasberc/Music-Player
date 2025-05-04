package com.vasberc.domain.model

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi

data class MusicModel(
    val fileName: String,
    val filePath: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val size: Long,
    val listsAdded: List<String>
) {
    @OptIn(UnstableApi::class)
    fun asMediaItem(): MediaItem {
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
}