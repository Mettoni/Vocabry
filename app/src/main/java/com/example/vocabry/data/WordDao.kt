package com.example.vocabry.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * from words where language = :language")
    suspend fun getAllWords(language:String): List<WordEntity>

    @Query("SELECT DISTINCT category FROM words")
    suspend fun getAllCategories(): List<String>

    @Query("SELECT * from words where category = :category AND language = :language")
    suspend fun getByCategory(category: String,language:String): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: WordEntity)

    @Query("DELETE from words where word= :word AND category = :category AND language = :language")
    suspend fun deleteWord(word: String, category: String,language:String)

    @Query("SELECT DISTINCT language from words")
    suspend fun getAllLanguages(): List<String>
}