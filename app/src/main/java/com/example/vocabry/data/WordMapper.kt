package com.example.vocabry.data

import com.example.vocabry.domain.model.Word

/**
 * Konvertuje databázový objekt [WordEntity] na doménový model [Word].
 *
 * Používa sa na transformáciu údajov získaných z databázy tak,
 * aby boli vhodné pre logiku aplikácie (domain layer).
 *
 * @return objekt typu [Word] s údajmi zo [WordEntity]
 */
fun WordEntity.toDomain(): Word = Word(word_id,word,translated,category,language)
/**
 * Konvertuje doménový model [Word] na databázový objekt [WordEntity].
 *
 * Používa sa pred uložením údajov do databázy – prevádza čistý doménový model
 * do formátu, ktorý databáza očakáva.
 *
 * @return objekt typu [WordEntity] s údajmi z [Word]
 */
fun Word.toEntity(): WordEntity = WordEntity(word_id,word,translated,category,language)