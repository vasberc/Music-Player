package com.vasberc.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vasberc.data_local.dao.ExampleDao
import com.vasberc.data_local.entity.ExampleEntity

@Database(
    entities = [ExampleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExampleDb: RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
}