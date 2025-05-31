package com.example.vocabry.data

import com.example.vocabry.domain.Word
import com.example.vocabry.domain.WordFunctions

class WordRepository(private val dao: WordDao): WordFunctions {

    override suspend fun getAllWords(): List<Word> { //= wordList
        return dao.getAllWords().map { it.toDomain() }
    }

    override suspend fun addWord(word: String, translation: String, category: String) {
        //wordList.add(word)
        //možno budem potrebovať withContext(Dispatchers.IO) keby aplikácie neresponduje
        dao.insertWord(WordEntity(word = word, translated = translation, category = category))
    }

    override suspend fun removeWord(word: String, category: String) {
        //wordList.remove(word)
        //možno budem potrebovať withContext(Dispatchers.IO) keby aplikácie neresponduje
        dao.deleteWord(word,category)
    }

    override suspend fun getAllCategories(): List<String> {
        return dao.getAllCategories()
    }

    override suspend fun getWordsByCategory(category: String): List<Word> {
        return dao.getByCategory(category).map{it.toDomain()}
    }

    override suspend fun getButtonOptions(correctWord: Word): List<Word> {
        val wordList = dao.getAllWords().filter{it.word != correctWord.word}
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
}