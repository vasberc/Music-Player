package com.vasberc.domain.repo

interface ListRepo {
    suspend fun getAllLists(): List<String>
    suspend fun getListFiles(listName: String): List<String>
}