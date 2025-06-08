package com.example.vocabry.domain.usecase


/**
 * UseCase zodpovedný za manipuláciu so skóre počas hry.
 *
 * Táto trieda udržiava aktuálne skóre ako vnútorný stav (in-memory) a umožňuje:
 *
 */
class AddScoreUseCase {
    private var currentScore = 0

    /**
     * Zvýši skóre o zadané množstvo a vráti nové skóre.
     *
     * @param amount Hodnota, o ktorú sa má skóre zvýšiť
     * @return Nové skóre po pripočítaní
     */
    operator fun invoke(amount: Int): Int {
        currentScore += amount
        return currentScore
    }

    /**
     * Vynuluje skóre na 0.
     */
    fun reset() {
        currentScore = 0
    }

}