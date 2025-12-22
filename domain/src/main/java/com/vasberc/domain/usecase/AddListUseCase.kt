package com.vasberc.domain.usecase

import com.vasberc.domain.model.DomainResult
import com.vasberc.domain.repo.ListRepo
import org.koin.core.annotation.Factory

@Factory
class AddListUseCase(
    private val listRepo: ListRepo
) {
    suspend operator fun invoke(listName: String): DomainResult<Unit> {
        return when (val result = listRepo.addList(listName)) {
            is DomainResult.Success -> DomainResult.Success(Unit)
            is DomainResult.Error -> {
                if (result.message.contains("unique", true)) {
                    DomainResult.Error("A list with this name already exists.")
                } else {
                    DomainResult.Error("An unknown error occurred while creating the list.")
                }
            }
        }
    }
}

