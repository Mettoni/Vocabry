package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.model.WordFunctions

/**
 *  UseCase trieda zodpovedná za získanie všetkých unikátnych jazykov
 *
 *  Trieda implementuje operáciu, ktorá získava dáta pomocou rozhrania [WordFunctions]
 *
 *  @param repository Inštancia implementácie rozhrania [WordFunctions], ktorá zabezpečuje prístup do úložiska dát ako je napr. roomDao
 */
class GetAllLanguagesUseCase (private val repository: WordFunctions){
    /**
     * Získa zoznam všetkých dostupných jazykov zo zdroja
     * @return Zoznam unikátnych jazykov
     */
    suspend operator fun invoke(): List<String> {
        return repository.getAllLanguages()
    }
}