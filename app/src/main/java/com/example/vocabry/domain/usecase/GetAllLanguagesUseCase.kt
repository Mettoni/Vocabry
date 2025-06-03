package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.WordFunctions

class GetAllLanguagesUseCase (private val repository: WordFunctions){
    suspend operator fun invoke(): List<String> {
        return repository.getAllLanguages()
    }
}