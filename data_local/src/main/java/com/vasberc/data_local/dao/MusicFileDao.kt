package com.vasberc.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vasberc.data_local.entity.MusicEntity
import com.vasberc.data_local.entity.MusicWithLists
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicFileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMusicFiles(musicFiles: List<MusicEntity>)

    @Transaction
    @Query("SELECT * FROM music_entity")
    suspend fun getAllMusicFiles(): Flow<List<MusicWithLists>>
}