package com.example.vocabry.data

interface WordFunctions {
    fun getAllWords() : List<String>
    fun addWord(word: String)
    fun removeWord(word: String)
    fun getButtonOptions(correctWord: String): List<String>
}