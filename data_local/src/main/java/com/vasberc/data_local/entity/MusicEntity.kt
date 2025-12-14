package com.vasberc.data_local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "music_entity", indices = [Index(value = ["folder_name"])])
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
    val size: Long,
    @ColumnInfo(name = "folder_name")
    val folderName: String
)