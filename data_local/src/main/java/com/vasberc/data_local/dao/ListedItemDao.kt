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

    @Query("DELETE FROM listed_items WHERE list = :listName AND item_path = :itemPath")
    suspend fun removeListedItem(listName: String, itemPath: String)

    @Query("SELECT * FROM listed_items WHERE list = :listName")
    suspend fun getListedItemsForList(listName: String): List<ListedItemEntity>

    @Query("SELECT list FROM listed_items WHERE item_path = :itemPath")
    suspend fun getListsForItem(itemPath: String): List<String>
}