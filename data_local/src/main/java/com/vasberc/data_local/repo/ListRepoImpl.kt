package com.vasberc.data_local.repo

import com.vasberc.data_local.dao.ListDao
import com.vasberc.data_local.dao.ListedItemDao
import com.vasberc.data_local.entity.ListEntity
import com.vasberc.data_local.entity.ListedItemEntity
import com.vasberc.domain.repo.ListRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class ListRepoImpl(
    private val listDao: ListDao,
    private val listedItemDao: ListedItemDao
): ListRepo {

    override suspend fun getAllLists(): List<String> {
        listDao.insertList(ListEntity("Favorites", false))//default
        return listDao.getAllLists().map { it.name }
    }

    override suspend fun getListFiles(listName: String): List<String> = listedItemDao.getListedItemsForList(listName).map { it.itemPath }

    override suspend fun addList(listName: String) {
        listDao.insertList(ListEntity(listName, true))
    }

    override suspend fun addListItem(listName: String, itemPath: String) {
        listedItemDao.insertListedItem(ListedItemEntity(itemPath = itemPath, list = listName))
    }

    override suspend fun getListsOfFile(string: String): List<String> {
        return listedItemDao.getListsForItem(string)
    }
}