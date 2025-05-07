package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import org.koin.core.annotation.Single

@Single
class AppStartUseCase(
    private val listRepo: ListRepo
) {
    suspend operator fun invoke() {
        listRepo.insertDefaultCategories()
    }
}