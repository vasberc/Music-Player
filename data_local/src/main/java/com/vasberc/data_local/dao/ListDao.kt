package com.vasberc.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasberc.data_local.entity.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {
    // Use ABORT so that inserting a list with the same primary key will throw a SQLiteConstraintException
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertList(list: ListEntity)

    @Query("SELECT * FROM list_entity")
    fun getAllLists(): Flow<List<ListEntity>>
}