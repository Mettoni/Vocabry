package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.model.WordFunctions

/**
 *  UseCase trieda zodpovedná za odobratie slovíčka z databázy
 *
 *  Trieda implementuje operáciu, ktorá získava dáta pomocou rozhrania [WordFunctions]
 *
 *  @param repository Inštancia implementácie rozhrania [WordFunctions], ktorá zabezpečuje prístup do úložiska dát ako je napr. roomDao
 */
class RemoveWordUseCase(private val repository: WordFunctions) {
    /**
     * Odstráni slovíčko so zadaným prekladom,kategóriou a jazykom z databázy
     *
     * @param word Slovenské slovo ktoré chceme odstrániť
     * @param category Kategória do ktorej slovíčko patrí
     * @param language Jazyk v ktorom je slovíčko
     */
    suspend operator fun invoke(word:String,category:String,language:String) {
        repository.removeWord(word,category,language)
    }
}