package com.example.vocabry.data

class UseWordFunctions: WordFunctions {
    private val wordList = mutableListOf<String>()
    private val buttonList = mutableListOf<String>()

    override fun getAllWords(): List<String> = wordList

    override fun addWord(word: String) {
        wordList.add(word)
    }

    override fun removeWord(word: String) {
        wordList.remove(word)
    }

    override fun getButtonOptions(correctWord: String): List<String> {
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