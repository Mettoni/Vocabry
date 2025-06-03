package com.example.vocabry.domain

data class Word(
    val word_id: Int = 0,
    val word: String,
    val translated: String,
    val category: String,
    val language: String
)

