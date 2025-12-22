package com.vasberc.domain.model

import androidx.compose.runtime.Stable

@Stable
data class MusicModel(
    val fileName: String,
    val filePath: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val size: Long,
    val listsAdded: List<String>
)