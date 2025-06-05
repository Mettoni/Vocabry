package com.example.vocabry.data

import com.example.vocabry.domain.Word
import com.example.vocabry.domain.WordFunctions
import kotlin.compareTo
import kotlin.text.category


class WordRepository(private val dao: WordDao): WordFunctions {

    override suspend fun getAllWords(language:String): List<Word> {
        return dao.getAllWords(language).map { it.toDomain() }
    }

    override suspend fun addWord(word: String, translation: String, category: String,language:String) {
        dao.insertWord(WordEntity(word = word, translated = translation, category = category, language = language))
    }

    override suspend fun removeWord(word: String, category: String,language:String) {
        dao.deleteWord(word,category,language)
    }

    override suspend fun getAllCategories(): List<String> {
        return dao.getAllCategories()
    }

    override suspend fun getWordsByCategory(category: String,language:String): List<Word> {
        return dao.getByCategory(category,language).map{it.toDomain()}
    }

    override suspend fun getButtonOptions(correctWord: Word,language: String): List<Word> {
        val categoryWords = dao.getByCategory(correctWord.category,language).filter { it.word != correctWord.word }
        val wordList = if(correctWord.category == "Chyby"|| categoryWords.size < 3) {
            dao.getAllWords(language).filter{it.word != correctWord.word}
        } else {
            categoryWords
        }
        //
        val buttonList = mutableListOf<WordEntity>()
        buttonList.clear()
        buttonList.add(correctWord.toEntity())

        while(buttonList.size < 4) {
            val newWord = wordList.random()
            if(buttonList.none { it.word == newWord.word }) {
                buttonList.add(newWord)
            }
        }
        return buttonList.shuffled().map {it.toDomain()}
    }

    override suspend fun getAllLanguages(): List<String> {
        return dao.getAllLanguages()
    }
}