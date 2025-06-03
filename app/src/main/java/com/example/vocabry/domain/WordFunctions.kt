package com.example.vocabry.domain

interface WordFunctions {
    suspend fun getAllWords(language: String) : List<Word>
    suspend fun addWord(word: String,translation: String, category: String,language:String)
    suspend fun removeWord(word: String,category: String,language:String)
    suspend fun getAllCategories(): List<String>
    suspend fun getWordsByCategory(category:String,language:String): List<Word>
    suspend fun getButtonOptions(correctWord: Word,language: String): List<Word>
    suspend fun getAllLanguages(): List<String>
}