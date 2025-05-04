package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import com.vasberc.domain.repo.MusicFilesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class GetFilesOfFolderUseCase(
    private val musicFilesRepo: MusicFilesRepo,
    private val listRepo: ListRepo
) {
    suspend operator fun invoke(
        folderPath: String
    ) = withContext(Dispatchers.IO) {
        val folder = musicFilesRepo.getFilesOfFolder(folderPath)
        return@withContext folder.copy(
            files = folder.files.map {
                it.copy(
                    listsAdded = listRepo.getListsOfFile(it.filePath)
                )
            }
        )
    }
}