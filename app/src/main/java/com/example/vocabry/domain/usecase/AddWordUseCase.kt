package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.WordFunctions

/**
 *  UseCase trieda zodpovedná za pridanie nového slovíčka do databázy
 *
 *  Trieda implementuje operáciu, ktorá spúšťa zápis cez rozohranie [WordFunctions]
 *
 *  @param repository Inštancia implementácie rozhrania [WordFunctions], ktorá zabezpečuje prístup do úložiska dát ako je napr. roomDao
 */
class AddWordUseCase(
    private val repository: WordFunctions
) {
    /**
     *  Pridá nové slovíčko so zadaným prekladom,kategóriou a jazykom do databázy
     *
     *  @param word Slovenské slovo
     *  @param translation Preklad slovenského slova
     *  @param category Kategória do ktorej slovíčko patrí
     *  @param language Jazyk,do ktorého slovo prekladáme
     */
    suspend operator fun invoke(word: String,translation: String, category: String,language:String) {
        repository.addWord(word,translation,category,language)
    }
}
