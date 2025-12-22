package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import org.koin.core.annotation.Factory

@Factory
class GetListsUseCase(
    private val listRepo: ListRepo
) {
    operator fun invoke() = listRepo.getAllLists()
}