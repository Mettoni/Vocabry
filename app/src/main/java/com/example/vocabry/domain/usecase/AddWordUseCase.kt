package com.example.vocabry.domain.usecase

import com.example.vocabry.data.WordFunctions

class AddWordUseCase(
    private val wordLis: WordFunctions
) {
    operator fun invoke(word: String) {
        wordLis.addWord(word)
    }
}