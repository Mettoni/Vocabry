package com.example.vocabry.domain

interface WordFunctions {
    suspend fun getAllWords() : List<Word>
    suspend fun addWord(word: String,translation: String, category: String)
    suspend fun removeWord(word: String,category: String)
    suspend fun getAllCategories(): List<String>
    suspend fun getWordsByCategory(category:String): List<Word>
    suspend fun getButtonOptions(correctWord: Word): List<Word>
}