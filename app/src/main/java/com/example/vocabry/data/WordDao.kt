package com.example.vocabry.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO rozhranie pre prácu s entitami slovíčok v databáze.
 * Definuje SQL dotazy a operácie na tabuľke `words`.
 */
@Dao
interface WordDao {

    /**
     * Získa všetky slovíčka z databázy pre zadaný jazyk.
     *
     * @param language Jazyk, podľa ktorého sa slovíčka filtrujú.
     * @return Zoznam entít [WordEntity] pre daný jazyk.
     */
    @Query("SELECT * from words where language = :language")
    suspend fun getAllWords(language:String): List<WordEntity>

    /**
     * Získa zoznam všetkých unikátnych kategórií.
     *
     * @return Zoznam názvov kategórií bez opakovania.
     */
    @Query("SELECT DISTINCT category FROM words")
    suspend fun getAllCategories(): List<String>

    /**
     * Získa zoznam všetkých unikátnych kategórií pre daný jazyk.
     *
     * @param language Jazyk, v ktorom hladáme kategórie
     * @return Zoznam názvov kategórií bez opakovania.
     */
    @Query("SELECT DISTINCT category FROM words WHERE language = :language")
    suspend fun getAllCategoriesByLanguage(language: String): List<String>

    /**
     * Získa všetky slovíčka, ktoré patria do konkrétnej kategórie a jazyka.
     *
     * @param category Názov kategórie.
     * @param language Jazyk, ktorého sa slovíčka týkajú.
     * @return Zoznam entít [WordEntity] v danej kategórii a jazyku.
     */
    @Query("SELECT * from words where category = :category AND language = :language")
    suspend fun getWordsByCategory(category: String, language:String): List<WordEntity>

    /**
     * Vloží nové slovíčko do databázy. Ak už existuje, ignoruje ho.
     *
     * @param word Entita slovíčka, ktorá sa má vložiť.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: WordEntity)

    /**
     * Odstráni slovíčko na základe jeho názvu, kategórie a jazyka.
     *
     * @param word Slovíčko, ktoré sa má odstrániť.
     * @param category Kategória, v ktorej sa slovíčko nachádza.
     * @param language Jazyk, ktorého sa slovíčko týka.
     */
    @Query("DELETE from words where word= :word AND category = :category AND language = :language")
    suspend fun deleteWord(word: String, category: String,language:String)

    /**
     * Získa všetky unikátne jazyky, ktoré sa v databáze nachádzajú.
     *
     * @return Zoznam názvov jazykov bez opakovania.
     */
    @Query("SELECT DISTINCT language from words")
    suspend fun getAllLanguages(): List<String>
}