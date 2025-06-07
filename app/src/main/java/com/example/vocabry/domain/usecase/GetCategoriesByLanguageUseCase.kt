package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.model.WordFunctions
/**
 * Use case na získanie všetkých kategórií pre daný jazyk.
 *
 * Tento use case slúži na oddelenie doménovej logiky od prezentačnej vrstvy.
 * Využíva [WordFunctions] repository na získanie jedinečných kategórií
 * existujúcich pre konkrétny jazyk.
 *
 * @property repository Rozhranie na prácu so slovami a ich kategóriami.
 */
class GetCategoriesByLanguageUseCase(private val repository: WordFunctions) {
    /**
     * Získa všetky kategórie dostupné pre daný jazyk.
     *
     * @param language Jazyk, pre ktorý sa majú kategórie načítať.
     * @return Zoznam názvov kategórií ako reťazce.
     */
    suspend operator fun invoke(language: String): List<String> {
        return repository.getAllCategoriesByLanguage(language)
    }
}