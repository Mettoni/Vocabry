package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.WordFunctions

class GetAllCategoriesUseCase(private val repo: WordFunctions) {
    suspend operator fun invoke():List<String> {
        return repo.getAllCategories()
    }
}