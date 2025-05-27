package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.Word
import com.example.vocabry.domain.WordFunctions

class GetListUseCase(private val wordLis: WordFunctions) {
    suspend operator fun invoke(): List<Word> {
        return wordLis.getAllWords()
    }
}