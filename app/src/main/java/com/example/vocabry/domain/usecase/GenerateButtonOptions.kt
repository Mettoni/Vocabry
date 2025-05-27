package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.WordFunctions

class GenerateButtonOptions(private val wordLis: WordFunctions) {
    suspend operator fun invoke(correctWord:String): List<String> {
        return wordLis.getButtonOptions(correctWord)
    }
}