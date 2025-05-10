package com.vasberc.domain.usecase

import com.vasberc.domain.repo.MusicFilesRepo
import org.koin.core.annotation.Factory

@Factory
class RefreshAllMusicFilesUseCase(
    private val musicFilesRepo: MusicFilesRepo
) {
    suspend operator fun invoke() {
        musicFilesRepo.refreshAllMusicFiles()
    }
}