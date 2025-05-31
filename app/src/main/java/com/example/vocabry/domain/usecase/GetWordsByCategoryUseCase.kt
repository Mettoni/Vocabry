package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.Word
import com.example.vocabry.domain.WordFunctions

class GetWordsByCategoryUseCase(private val repo:WordFunctions) {
    suspend operator fun invoke(category: String): List<Word> {
        return repo.getWordsByCategory(category)
    }
}