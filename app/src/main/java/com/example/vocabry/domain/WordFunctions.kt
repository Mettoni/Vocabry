package com.example.vocabry.domain

interface WordFunctions {
    suspend fun getAllWords() : List<Word>
    suspend fun addWord(word: String,translation: String, category: String)
    suspend fun removeWord(word: String,category: String)
    suspend fun getButtonOptions(correctWord: String): List<String>
}