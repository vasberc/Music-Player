package com.vasberc.data_local.di

import android.content.Context
import androidx.room.Room
import com.vasberc.data_local.dao.ExampleDao
import com.vasberc.data_local.db.ExampleDb
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.vasberc.data_local")
class DataLocalModule

@Single
fun provideDb(context: Context): ExampleDb {
    return Room.databaseBuilder(
        context = context,
        klass = ExampleDb::class.java,
        name = "example_db"
    ).build()
}

@Single
fun provideExampleDao(dataBase: ExampleDb): ExampleDao {
    return dataBase.exampleDao()
}