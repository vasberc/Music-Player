package com.vasberc.domain.usecase

import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.repo.ListRepo
import com.vasberc.domain.repo.MusicFilesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.koin.core.annotation.Factory

@Factory
class GetFilesOfListUseCase(
    private val musicFilesRepo: MusicFilesRepo,
    private val listRepo: ListRepo
) {
    suspend operator fun invoke(listName: String): FolderModel = coroutineScope {
        val allFiles = async(Dispatchers.IO) { musicFilesRepo.getAllMusicFiles() }
        val listFiles = async(Dispatchers.IO) { listRepo.getListFilesPath(listName) }
        val listFilesToReturn = allFiles.await().map { files -> files.files.filter { file -> listFiles.await().any { file.filePath == it } } }.flatten()
        val folder = FolderModel(
            name = listName,
            path = listName,
            files = listFilesToReturn.map { it.copy(listsAdded = listRepo.getListsOfFile(it.filePath)) }
        )
        return@coroutineScope folder
    }
}