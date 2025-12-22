package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import org.koin.core.annotation.Factory

@Factory
class AddListedItemUseCase(
    private val listRepo: ListRepo
) {
    suspend operator fun invoke(listName: String, itemPath: String) = listRepo.addListItem(listName, itemPath)
}