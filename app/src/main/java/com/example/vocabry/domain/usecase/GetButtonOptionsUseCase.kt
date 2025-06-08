package com.example.vocabry.domain.usecase

import com.example.vocabry.data.WordRepository
import com.example.vocabry.domain.model.Word
import com.example.vocabry.domain.model.WordFunctions

/**
 *  UseCase trieda zodpovedná za generovanie možností do tlačítok pod otázkou
 *
 *  Trieda implementuje operáciu, ktorá generuje možnosti na základe správneho slovíčka pomocou dát získaných cez rozhranie [WordFunctions]
 *
 *  @param repository Inštancia implementácie rozhrania [WordFunctions], ktorá zabezpečuje prístup do úložiska dát ako je napr. roomDao
 */
class GetButtonOptionsUseCase(
    private val repository: WordRepository
) {

    /**
     * Vygeneruje zoznam 4 slov, z ktorých jedno je správne (zadané) a ostatné sú náhodne vybrané.
     * Preferuje slová z rovnakej kategórie, ak ich je dostatok.
     *
     * @param correctWord Slovo, ktoré je správna odpoveď.
     * @param language Jazyk, z ktorého sa majú slová vyberať.
     * @return Zoznam slov pre tlačidlá, vrátane správneho.
     */
    suspend operator fun invoke(correctWord: Word, language: String): List<Word> {
        val categoryWords = repository
            .getWordsByCategory(correctWord.category, language)
            .filter { it.word != correctWord.word }

        val wordList = if (correctWord.category == "Chyby" || categoryWords.size < 3) {
            repository.getAllWords(language)
                .filter { it.word != correctWord.word }
        } else {
            categoryWords
        }

        val buttonList = mutableListOf<Word>()
        buttonList.add(correctWord)

        while (buttonList.size < 4) {
            val newWord = wordList.random()
            if (buttonList.none { it.word == newWord.word }) {
                buttonList.add(newWord)
            }
        }

        return buttonList.shuffled()
    }
}