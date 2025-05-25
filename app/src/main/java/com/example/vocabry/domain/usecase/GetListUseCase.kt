package com.example.vocabry.domain.usecase

import com.example.vocabry.data.WordFunctions

class GetListUseCase(private val wordLis: WordFunctions) {
    operator fun invoke(): List<String> {
        return wordLis.getAllWords()
    }
}