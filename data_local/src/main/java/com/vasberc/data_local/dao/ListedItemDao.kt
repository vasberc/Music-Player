package com.vasberc.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasberc.data_local.entity.ListedItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListedItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListedItem(listedItem: ListedItemEntity)

    @Query("DELETE FROM listed_items WHERE list = :listName AND item_path = :itemPath")
    suspend fun removeListedItem(listName: String, itemPath: String)

    @Query("SELECT * FROM listed_items WHERE list = :listName")
    fun getListedItemsForList(listName: String): Flow<List<ListedItemEntity>>

    @Query("SELECT list FROM listed_items WHERE item_path = :itemPath")
    fun getListsForItem(itemPath: String): Flow<List<String>>
}