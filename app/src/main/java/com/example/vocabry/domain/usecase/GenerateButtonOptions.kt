package com.example.vocabry.domain.usecase

import com.example.vocabry.data.WordFunctions

class GenerateButtonOptions(private val wordLis: WordFunctions) {
    operator fun invoke(correctWord:String): List<String> {
        return wordLis.getButtonOptions(correctWord)
    }
}