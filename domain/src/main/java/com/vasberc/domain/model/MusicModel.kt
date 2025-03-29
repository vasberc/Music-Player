package com.vasberc.domain.model

data class MusicModel(
    val fileName: String,
    val filePath: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val size: Long
)