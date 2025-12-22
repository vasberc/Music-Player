package com.vasberc.domain.usecase

import com.vasberc.domain.repo.MusicFilesRepo
import org.koin.core.annotation.Factory

@Factory
class GetAllMusicFilesUseCase(
    private val musicFilesRepo: MusicFilesRepo
) {
    operator fun invoke() = musicFilesRepo.allMusicFilesFlow
}