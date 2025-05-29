package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.Word
import com.example.vocabry.domain.WordFunctions

class GenerateButtonOptions(private val wordLis: WordFunctions) {
    suspend operator fun invoke(correctWord:Word): List<Word> {
        return wordLis.getButtonOptions(correctWord)
    }
}