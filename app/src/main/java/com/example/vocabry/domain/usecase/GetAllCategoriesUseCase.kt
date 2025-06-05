package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.WordFunctions

/**
 *  UseCase trieda zodpovedná za získanie všetkých rôznych kategórii z databázy
 *
 *  Trieda implementuje operáciu, ktorá získava dáta pomocou rozhrania [WordFunctions]
 *
 *  @param repository Inštancia implementácie rozhrania [WordFunctions], ktorá zabezpečuje zápis do úložiska dát ako je napr. roomDao
 */
class GetAllCategoriesUseCase(private val repository: WordFunctions) {
    /**
     * Získa zoznam všetkých dostupných kategórii zo zdroja
     * @return Zoznam unikátnych názvov kategórii
     */
    suspend operator fun invoke():List<String> {
        return repository.getAllCategories()
    }
}
