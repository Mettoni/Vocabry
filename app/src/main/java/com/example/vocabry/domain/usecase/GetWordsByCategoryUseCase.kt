package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.Word
import com.example.vocabry.domain.WordFunctions

/**
 *  UseCase trieda zodpovedná za získanie všetkých slov v určitej kategórii a určitom jazyku
 *
 *  Trieda implementuje operáciu, ktorá získava dáta pomocou rozhrania [WordFunctions]
 *
 *  @param repository Inštancia implementácie rozhrania [WordFunctions], ktorá zabezpečuje prístup do úložiska dát ako je napr. roomDao
 */
class GetWordsByCategoryUseCase(private val repository:WordFunctions) {
    /**
     *  Získa zoznam všetkých slovíčok vo zvolenej kategórii a vo zvolenom jazyku
     *  @param category Kategória z ktorej sa majú slovíčka vyberať
     *  @param language Jazyk v ktorom majú byť slovíčka z kategórie
     */
    suspend operator fun invoke(category: String,language:String): List<Word> {
        return repository.getWordsByCategory(category,language)
    }
}