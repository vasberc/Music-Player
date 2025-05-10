package com.vasberc.data_local.repo

import com.vasberc.data_local.dao.ListDao
import com.vasberc.data_local.dao.ListedItemDao
import com.vasberc.data_local.entity.ListEntity
import com.vasberc.data_local.entity.ListedItemEntity
import com.vasberc.domain.repo.ListRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ListRepoImpl(
    private val listDao: ListDao,
    private val listedItemDao: ListedItemDao
): ListRepo {
    override suspend fun insertDefaultCategories() {
        listDao.insertList(ListEntity("Favorites", false))//default
    }

    override fun getAllLists(): Flow<List<String>> {
        return listDao.getAllLists().map { list -> list.map { it.name } }
    }

    override fun getListFilesPath(listName: String): Flow<List<String>> = listedItemDao.getListedItemsForList(listName).map { list -> list.map { it.itemPath } }

    override suspend fun addList(listName: String) {
        listDao.insertList(ListEntity(listName, true))
    }

    override suspend fun addListItem(listName: String, itemPath: String) {
        listedItemDao.insertListedItem(ListedItemEntity(itemPath = itemPath, list = listName))
    }

    override suspend fun removeListItem(listName: String, itemPath: String) {
        listedItemDao.removeListedItem(itemPath = itemPath, listName = listName)
    }

    override fun getListsOfFile(string: String): Flow<List<String>> {
        return listedItemDao.getListsForItem(string)
    }
}