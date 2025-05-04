package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class AddListedItemUseCase(
    private val listRepo: ListRepo
) {
    suspend operator fun invoke(listName: String, itemPath: String) = withContext(Dispatchers.IO) {
        listRepo.addListItem(listName, itemPath)
    }
}