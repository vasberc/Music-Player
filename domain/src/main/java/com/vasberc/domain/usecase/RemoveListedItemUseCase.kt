package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import org.koin.core.annotation.Factory

@Factory
class RemoveListedItemUseCase(
    private val listRepo: ListRepo
) {
    suspend operator fun invoke(listName: String, itemPath: String) = listRepo.removeListItem(listName, itemPath)
}