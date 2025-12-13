package com.vasberc.data_local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_entity")
data class MusicEntity(
    @ColumnInfo(name = "file_name")
    val fileName: String,

    @PrimaryKey
    @ColumnInfo(name = "file_path")
    val filePath: String,

    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val size: Long
)