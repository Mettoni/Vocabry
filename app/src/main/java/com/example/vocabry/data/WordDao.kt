package com.example.vocabry.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * from words")
    suspend fun getAllWords(): List<WordEntity>

    @Query("SELECT DISTINCT category FROM words")
    suspend fun getAllCategories(): List<String>

    @Query("SELECT * from words where category = :category")
    suspend fun getByCategory(category: String): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: WordEntity)

    @Query("DELETE from words where word= :word AND category = :category")
    suspend fun deleteWord(word: String, category: String)
}