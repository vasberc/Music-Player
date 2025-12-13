package com.vasberc.data_local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "listed_items",
    primaryKeys = ["item_path", "list"],
    indices = [Index(value = ["list"]), Index(value = ["item_path"])],
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["name"],
            childColumns = ["list"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MusicEntity::class,
            parentColumns = ["file_path"],
            childColumns = ["item_path"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ListedItemEntity(
    @ColumnInfo(name = "item_path")
    val itemPath: String,
    val list: String
)