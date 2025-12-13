package com.vasberc.data_local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Represents a MusicEntity with the lists it belongs to.
 */
data class MusicWithLists(
    @Embedded
    val music: MusicEntity,

    @Relation(
        parentColumn = "file_path",
        entityColumn = "item_path",
        associateBy = Junction(ListedItemEntity::class)
    )
    val lists: List<ListEntity>
)

