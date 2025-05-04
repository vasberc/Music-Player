package com.vasberc.data_local.repo

import com.vasberc.data_local.dao.ListDao
import com.vasberc.data_local.dao.ListedItemDao
import com.vasberc.data_local.entity.ListEntity
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

    init {
        CoroutineScope(Dispatchers.IO).launch {
            //Add default list
            listDao.insertList(ListEntity("Favorites", false))
        }
    }
    override suspend fun getAllLists(): List<String> = listDao.getAllLists().map { it.name }

    override suspend fun getListFiles(listName: String): List<String> = listedItemDao.getListedItemsForList(listName).map { it.itemPath }
}