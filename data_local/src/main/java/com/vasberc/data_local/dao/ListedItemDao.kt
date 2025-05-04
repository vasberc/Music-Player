package com.vasberc.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasberc.data_local.entity.ListedItemEntity

@Dao
interface ListedItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListedItem(listedItem: ListedItemEntity)

    @Query("SELECT * FROM listed_items WHERE list = :listName")
    suspend fun getListedItemsForList(listName: String): List<ListedItemEntity>
}