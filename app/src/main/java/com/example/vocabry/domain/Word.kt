package com.example.vocabry.domain

/**
 * Dátová trieda reprezentujúca jedno slovíčko v aplikácii.
 *
 * @property word_id Unikátne ID slovíčka v databáze.
 * @property word Slovíčko v pôvodnom jazyku
 * @property translated Preklad slovíčka do cieľového jazyka
 * @property category Kategória, do ktorej slovíčko patrí (napr. "Ovocie", "Škola").
 * @property language Jazyk, do ktorého je slovíčko preložené (napr. "English", "German").
 */
data class Word(
    val word_id: Int = 0,
    val word: String,
    val translated: String,
    val category: String,
    val language: String
)

