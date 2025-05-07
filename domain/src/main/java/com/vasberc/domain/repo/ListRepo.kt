package com.vasberc.domain.repo

interface ListRepo {
    suspend fun insertDefaultCategories()
    suspend fun getAllLists(): List<String>
    suspend fun getListFilesPath(listName: String): List<String>
    suspend fun addList(listName: String)
    suspend fun addListItem(listName: String, itemPath: String)
    suspend fun removeListItem(listName: String, itemPath: String)
    suspend fun getListsOfFile(string: String): List<String>
}