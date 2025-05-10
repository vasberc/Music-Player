package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import com.vasberc.domain.repo.MusicFilesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class AppStartUseCase(
    private val listRepo: ListRepo,
    private val musicFilesRepo: MusicFilesRepo
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        musicFilesRepo.refreshAllMusicFiles()
        listRepo.insertDefaultCategories()
    }
}