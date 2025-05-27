package com.example.vocabry.data

import com.example.vocabry.domain.Word


fun WordEntity.toDomain(): Word = Word(word_id,word,translated,category)

fun Word.toEntity(): WordEntity = WordEntity(word_id,word,translated,category)