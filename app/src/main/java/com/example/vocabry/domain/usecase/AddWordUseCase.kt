package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.WordFunctions

class AddWordUseCase(
    private val wordLis: WordFunctions
) {
    suspend operator fun invoke(word: String,translation: String, category: String,language:String) {
        wordLis.addWord(word,translation,category,language)
    }
}