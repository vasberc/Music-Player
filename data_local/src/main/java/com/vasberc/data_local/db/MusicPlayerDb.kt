package com.vasberc.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vasberc.data_local.dao.ListDao
import com.vasberc.data_local.dao.ListedItemDao
import com.vasberc.data_local.dao.MusicFileDao
import com.vasberc.data_local.entity.ListEntity
import com.vasberc.data_local.entity.ListedItemEntity
import com.vasberc.data_local.entity.MusicEntity

@Database(
    entities = [ListEntity::class, ListedItemEntity::class, MusicEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MusicPlayerDb: RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun listedItemDao(): ListedItemDao
    abstract fun musicFileDao(): MusicFileDao
}