package com.vasberc.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vasberc.data_local.dao.ListDao
import com.vasberc.data_local.dao.ListedItemDao
import com.vasberc.data_local.entity.ListEntity
import com.vasberc.data_local.entity.ListedItemEntity

@Database(
    entities = [ListEntity::class, ListedItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MusicPlayerDb: RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun listedItemDao(): ListedItemDao
}