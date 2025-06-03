package com.example.vocabry.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val word_id: Int = 0,
    val word: String,
    val translated: String,
    val category: String,
    val language: String
)