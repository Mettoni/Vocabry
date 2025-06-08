package com.example.vocabry.data

import com.example.vocabry.domain.model.Word
import com.example.vocabry.domain.model.WordFunctions

/**
 * Implementácia rozhrania [WordFunctions], ktorá poskytuje prístup k slovíčkam
 * cez [WordDao] vrstvu databázy.
 *
 * Táto trieda slúži ako repository v rámci clean architektúry, zabezpečuje
 * mapovanie medzi databázovou entitou a doménovým modelom.
 *
 * @param dao Databázový prístupový objekt pre prácu so slovíčkami.
 */
class WordRepository(private val dao: WordDao): WordFunctions {


    /**
     * Získa všetky slovíčka pre daný jazyk.
     *
     * @param language Jazyk, podľa ktorého sa filtrujú slovíčka.
     * @return Zoznam doménových objektov [Word].
     */
    override suspend fun getAllWords(language:String): List<Word> {
        return dao.getAllWords(language).map { it.toDomain() }
    }


    /**
     * Pridá nové slovíčko do databázy.
     *
     * @param word Slovíčko v pôvodnom jazyku.
     * @param translation Preklad slovíčka.
     * @param category Kategória, do ktorej patrí.
     * @param language Jazyk, ktorého sa slovíčko týka.
     */
    override suspend fun addWord(word: String, translation: String, category: String,language:String) {
        dao.insertWord(WordEntity(word = word, translated = translation, category = category, language = language))
    }

    /**
     * Odstráni slovíčko z databázy podľa jeho názvu, kategórie a jazyka.
     *
     * @param word Názov slovíčka.
     * @param category Kategória, z ktorej sa má odstrániť.
     * @param language Jazyk, ktorého sa týka.
     */
    override suspend fun removeWord(word: String, category: String,language:String) {
        dao.deleteWord(word,category,language)
    }

    /**
     * Získa všetky dostupné kategórie.
     *
     * @return Zoznam názvov kategórií.
     */
    override suspend fun getAllCategories(): List<String> {
        return dao.getAllCategories()
    }

    /**
     * Získa všetky dostupné kategórie v danom jazyku.
     *
     * @param language Jazyk v ktorom hladáme kategórie
     * @return Zoznam názvov kategórií.
     */
    override suspend fun getAllCategoriesByLanguage(language: String): List<String> {
        return dao.getAllCategoriesByLanguage(language)
    }

    /**
     * Získa všetky slovíčka z určitej kategórie a jazyka.
     *
     * @param category Kategória, podľa ktorej sa filtruje.
     * @param language Jazyk, ktorého sa týka.
     * @return Zoznam doménových objektov [Word].
     */
    override suspend fun getWordsByCategory(category: String,language:String): List<Word> {
        return dao.getWordsByCategory(category,language).map{it.toDomain()}
    }

    /**
     * Vygeneruje štyri možnosti odpovedí do tlačidiel vrátane správnej odpovede.
     *
     * Ak je kategória "Chyby" alebo ak kategória nemá dosť slov na naplnenie všetkých tlačidiel, vyberie z celej databázy.
     *
     * @param correctWord Správna odpoveď.
     * @param language Jazyk, ktorého sa otázka týka.
     * @return Zoznam náhodných slov (vrátane správnej odpovede), premiešaných.
     */
    override suspend fun getButtonOptions(correctWord: Word,language: String): List<Word> {
        val categoryWords = dao.getWordsByCategory(correctWord.category,language).filter { it.word != correctWord.word }
        val wordList = if(correctWord.category == "Chyby"|| categoryWords.size < 3) {
            dao.getAllWords(language).filter{it.word != correctWord.word}
        } else {
            categoryWords
        }

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

    /**
     * Získa všetky dostupné jazyky, ktoré sa aktuálne nachádzajú v databáze.
     *
     * @return Zoznam názvov jazykov.
     */
    override suspend fun getAllLanguages(): List<String> {
        return dao.getAllLanguages()
    }
}