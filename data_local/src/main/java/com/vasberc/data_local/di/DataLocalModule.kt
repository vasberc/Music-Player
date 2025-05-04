package com.vasberc.data_local.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vasberc.data_local.dao.ListDao
import com.vasberc.data_local.dao.ListedItemDao
import com.vasberc.data_local.db.MusicPlayerDb
import com.vasberc.data_local.entity.ListEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.inject

@Module
@ComponentScan("com.vasberc.data_local")
class DataLocalModule

@Single
fun provideDb(context: Context): MusicPlayerDb {
    return Room.databaseBuilder(
        context = context,
        klass = MusicPlayerDb::class.java,
        name = "music_player_db"
    ).build()
}

@Single
fun provideListDao(dataBase: MusicPlayerDb): ListDao {
    return dataBase.listDao()
}

@Single
fun provideListedItemDao(dataBase: MusicPlayerDb): ListedItemDao {
    return dataBase.listedItemDao()
}