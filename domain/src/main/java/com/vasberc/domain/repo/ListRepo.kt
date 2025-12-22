package com.vasberc.domain.repo

import com.vasberc.domain.model.DomainResult
import kotlinx.coroutines.flow.Flow

interface ListRepo {
    suspend fun insertDefaultCategories()
    fun getAllLists(): Flow<List<String>>
    fun getListFilesPath(listName: String): Flow<List<String>>
    suspend fun addList(listName: String): DomainResult<Unit>
    suspend fun addListItem(listName: String, itemPath: String)
    suspend fun removeListItem(listName: String, itemPath: String)
    fun getListsOfFile(string: String): Flow<List<String>>
}