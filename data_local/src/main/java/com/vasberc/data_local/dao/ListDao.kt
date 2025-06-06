package com.vasberc.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasberc.data_local.entity.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(list: ListEntity)

    @Query("SELECT * FROM list_entity")
    fun getAllLists(): Flow<List<ListEntity>>
}