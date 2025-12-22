package com.vasberc.domain.usecase

import com.vasberc.domain.repo.MusicFilesRepo
import org.koin.core.annotation.Factory

@Factory
class GetFilesOfFolderUseCase(
    private val musicFilesRepo: MusicFilesRepo
) {
    operator fun invoke(
        folderPath: String
    ) = musicFilesRepo.getFilesOfFolderFlow(folderPath)
}