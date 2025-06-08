package com.example.vocabry.domain.model

/**
 * Rozohranie ktoré definuje všetky operácie súvisiace so slovnou zásobou,
 * ktoré sú potrebné pre aplikáciu
 */
interface WordFunctions {
    /**
     * Načíta všetky slovíčka pre daný jazyk
     *
     * @param language Jazyk, pre ktorý sa majú získať slovíčka
     * @return Zoznam všetkych slovíčok v danom jazyku
     */
    suspend fun getAllWords(language: String) : List<Word>

    /**
     * Pridá nové slovíčko do databázy
     * @param word Slovenské slovo
     * @param translation Preklad slovenského slova
     * @param category Kategória do ktorej slovíčko patrí
     * @param language Jazyk,do ktorého slovo prekladáme
     */
    suspend fun addWord(word: String,translation: String, category: String,language:String)

    /**
     * Odstránime slovíčko z databázy
     *
     * @param word Slovenské slovo ktoré chceme odstrániť
     * @param category Kategória do ktorej slovíčko patrí
     * @param language Jazyk v ktorom je slovíčko
     */
    suspend fun removeWord(word: String,category: String,language:String)
    /**
     * Získa zoznam všetkých dostupných kategórii zo zdroja
     * @return Zoznam unikátnych názvov kategórii
     */
    suspend fun getAllCategories(): List<String>
    /**
     * Získa všetky jedinečné názvy kategórií pre daný jazyk.
     *
     * Táto suspend funkcia prehľadá uložené slová a vráti zoznam názvov
     * kategórií, ktoré patria k zadanému jazyku. Výsledný zoznam môže byť
     * prázdny, ak sa v danom jazyku nenachádzajú žiadne slová.
     *
     * @param language Jazyk, podľa ktorého sa filtrujú kategórie.
     * @return Zoznam názvov kategórií ako reťazce.
     */
    suspend fun getAllCategoriesByLanguage(language: String): List<String>
    /**
     *  Získa zoznam všetkých slovíčok vo zvolenej kategórii a vo zvolenom jazyku
     *  @param category Kategória z ktorej sa majú slovíčka vyberať
     *  @param language Jazyk v ktorom majú byť slovíčka z kategórie
     */
    suspend fun getWordsByCategory(category:String,language:String): List<Word>
    /**
     *  Vytvorí možnosti možnosti do tlačítok
     *
     *  @param correctWord Predstavuje hádané slovíčko
     *  @param language Jazyk v ktorom majú byť slovíčka v tlačítkach
     *  @return Slovíčka ktoré sa budú nachádzať v tlačítkach pod otázkou
     */
    suspend fun getButtonOptions(correctWord: Word, language: String): List<Word>
    /**
     * Získa zoznam všetkých dostupných jazykov zo zdroja
     * @return Zoznam unikátnych jazykov
     */
    suspend fun getAllLanguages(): List<String>
}