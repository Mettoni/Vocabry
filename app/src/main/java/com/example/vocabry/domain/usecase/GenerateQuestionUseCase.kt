package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.model.Word

/**
 * Výsledok generovania otázky.
 *
 * Táto trieda reprezentuje rôzne možné výstupy z procesu generovania otázky:
 * - správne slovo, ktoré má byť uhádnuté
 * - zoznam možností (odpovedí)
 * - príznaky stavu ako `success`, `notEnoughWords` a `gameFinished`
 *
 * @property correctWord Slovo, ktoré má byť správne uhádnuté.
 * @property options Zoznam odpovedí, ktoré sa zobrazia používateľovi.
 * @property success Označuje, či bola otázka úspešne vygenerovaná.
 * @property notEnoughWords Označuje, že neexistuje dostatok slov pre vytvorenie otázky.
 * @property gameFinished Označuje, že hra sa skončila (neexistujú ďalšie nové otázky).
 */
data class QuestionGenerationResult(
    val correctWord: Word?,
    val options: List<Word>,
    val success: Boolean = false,
    val notEnoughWords: Boolean = false,
    val gameFinished: Boolean = false
)


/**
 * UseCase, ktorý generuje otázku na základe kategórie a jazyka.
 *
 * Tento use case využíva ostatné use case-y na získanie všetkých slov,
 * filtrovanie podľa kategórie a výber správnych/náhodných odpovedí.
 *
 * @property getListUseCase UseCase pre získanie všetkých slov v danom jazyku.
 * @property getWordsByCategoryUseCase UseCase pre získanie slov v konkrétnej kategórii a jazyku.
 * @property getButtonOptionsUseCase UseCase pre vygenerovanie 4 náhodných odpovedí vrátane správnej.
 */
class GenerateQuestionUseCase(
    private val getListUseCase: GetListUseCase,
    private val getWordsByCategoryUseCase: GetWordsByCategoryUseCase,
    private val getButtonOptionsUseCase: GetButtonOptionsUseCase
) {
    /**
     * Generuje novú otázku.
     *
     * @param category Vybraná kategória, z ktorej sa otázka generuje.
     * @param language Jazyk, v ktorom sa používateľ učí.
     * @param alreadyUsedWords Zoznam slov, ktoré už boli použité v aktuálnej hre.
     * @return Výsledok obsahujúci buď úspešne vygenerovanú otázku, alebo informáciu o chybe/stave.
     */
    suspend fun invoke(
        category: String,
        language: String,
        alreadyUsedWords: List<Word>
    ): QuestionGenerationResult {
        val allWords = getListUseCase(language)
        if (allWords.size < 4) {
            return QuestionGenerationResult(
                correctWord = null,
                options = emptyList(),
                notEnoughWords = true
            )
        }

        val wordsInCategory = getWordsByCategoryUseCase(category, language)
        val unused = wordsInCategory.filterNot { used ->
            alreadyUsedWords.any { it.word == used.word }
        }

        if (unused.isEmpty()) {
            return QuestionGenerationResult(
                correctWord = null,
                options = emptyList(),
                gameFinished = true
            )
        }

        val correct = unused.random()
        val options = getButtonOptionsUseCase(correct, language)

        return QuestionGenerationResult(
            correctWord = correct,
            options = options,
            success = true
        )
    }
}