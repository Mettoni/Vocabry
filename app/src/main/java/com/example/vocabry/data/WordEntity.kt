package com.example.vocabry.data

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Dátová trieda reprezentujúca entity tabuľky "words" v Room databáze
 *
 * Každý záznam je jedno slovíčko s jeho prekladom,kategóriou a jazykom
 *
 * @property word_id Primárny klúč, ktorý je automaticky generovaný
 * @property word Slovíčko v pôvodnom jazyku
 * @property translated Preklad slovíčka
 * @property category Kategória,do ktorej slovíčko patrí
 * @property language Jazyk, ku ktorému slovíčko patrí
 */
@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val word_id: Int = 0,
    val word: String,
    val translated: String,
    val category: String,
    val language: String
)