package com.example.vocabry.domain.usecase

/**
 * Use case, ktorý pridá slovo len v prípade, že ešte neexistuje v danej kategórii a jazyku.
 *
 * Tento use case najprv načíta všetky slová z kategórie a jazyka, následne overí,
 * či sa dané slovo už v zozname nachádza. Ak nie, zavolá [AddWordUseCase] a slovo pridá.
 *
 * @property getWordsByCategory Use case pre získanie slov podľa kategórie a jazyka.
 * @property addWordUseCase Use case pre pridanie nového slova do databázy.
 */
class AddWordIfNotExistsUseCase(
    private val getWordsByCategory: GetWordsByCategoryUseCase,
    private val addWordUseCase: AddWordUseCase
) {
    /**
     * Pokúsi sa pridať nové slovo, ak ešte neexistuje. Po pridaní môže voliteľne spustiť refresh.
     *
     * @param word Slovo, ktoré sa má pridať.
     * @param translation Preklad slova.
     * @param category Kategória, do ktorej slovo patrí.
     * @param language Jazyk slova.
     * @param afterAdd Optional: lambda, ktorá sa spustí, ak sa slovo pridá.
     *
     * @return `true` ak bolo slovo pridané, inak `false`.
     */
    suspend operator fun invoke(
        word: String,
        translation: String,
        category: String,
        language: String,
        afterAdd: (() -> Unit)? = null
    ): Boolean {
        val words = getWordsByCategory(category, language)
        val exists = words.any { it.word.equals(word.trim(), ignoreCase = true) }

        if (!exists) {
            addWordUseCase(word, translation, category, language)
            afterAdd?.invoke()
        }

        return !exists
    }
}