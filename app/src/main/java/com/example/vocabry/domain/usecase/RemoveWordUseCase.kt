package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.WordFunctions

class RemoveWordUseCase(private val wordLis: WordFunctions) {
    suspend operator fun invoke(word:String,category:String) {
        wordLis.removeWord(word,category)
    }
}