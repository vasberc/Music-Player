package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class GetCategoriesUseCase(
    private val listRepo: ListRepo
) {
    operator fun invoke() = listRepo.getAllLists()
}