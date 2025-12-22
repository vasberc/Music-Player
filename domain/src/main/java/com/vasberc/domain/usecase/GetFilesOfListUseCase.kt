package com.vasberc.domain.usecase

import com.vasberc.domain.repo.MusicFilesRepo
import org.koin.core.annotation.Factory

@Factory
class GetFilesOfListUseCase(
    private val musicFilesRepo: MusicFilesRepo
) {
    operator fun invoke(listName: String) = musicFilesRepo.getFilesOfListFlow(listName)
}