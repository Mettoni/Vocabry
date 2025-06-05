package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.Word
import com.example.vocabry.domain.WordFunctions

/**
 *  UseCase trieda zodpovedná za získanie všetkých slov v určitom jazyku
 *
 *  Trieda implementuje operáciu, ktorá získava dáta pomocou rozhrania [WordFunctions]
 *
 *  @param repository Inštancia implementácie rozhrania [WordFunctions], ktorá zabezpečuje prístup do úložiska dát ako je napr. roomDao
 */
class GetListUseCase(private val repository: WordFunctions) {
    /**
     * Získa zoznam všetkých slovíčok v danom jazyku
     *
     * @param language Jazyk, pre ktorý sa majú získať slovíčka
     * @return Zoznam všetkych slovíčok v danom jazyku
     */
    suspend operator fun invoke(language:String): List<Word> {
        return repository.getAllWords(language)
    }
}