package com.example.vocabry.data

import com.example.vocabry.domain.Word
import com.example.vocabry.domain.WordFunctions

class RoomUseWordFunctions(private val dao: WordDao): WordFunctions {

    private val buttonList = mutableListOf<String>()

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

    override suspend fun getButtonOptions(correctWord: String): List<String> {
        val wordList = dao.getAllWords().map { it.word }

        buttonList.clear()
        buttonList.add(correctWord)

        while(buttonList.size < 4) {
            val newWord = wordList.random()
            if(!buttonList.contains(newWord)) {
                buttonList.add(newWord)
            }
        }
        return buttonList.shuffled()
    }
}