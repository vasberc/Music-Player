package com.vasberc.domain.usecase

import com.vasberc.domain.repo.MusicFilesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class GetFilesOfFolderUseCase(
    private val musicFilesRepo: MusicFilesRepo
) {
    suspend operator fun invoke(
        folderPath: String
    ) = withContext(Dispatchers.IO) { musicFilesRepo.getFilesOfFolder(folderPath) }
}