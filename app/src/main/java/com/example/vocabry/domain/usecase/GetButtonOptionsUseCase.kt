package com.example.vocabry.domain.usecase

import com.example.vocabry.domain.model.Word
import com.example.vocabry.domain.model.WordFunctions

/**
 *  UseCase trieda zodpovedná za generovanie možností do tlačítok pod otázkou
 *
 *  Trieda implementuje operáciu, ktorá generuje možnosti na základe správneho slovíčka pomocou dát získaných cez rozhranie [WordFunctions]
 *
 *  @param repository Inštancia implementácie rozhrania [WordFunctions], ktorá zabezpečuje prístup do úložiska dát ako je napr. roomDao
 */
class GetButtonOptionsUseCase(private val repository: WordFunctions) {

    /**
     *  Vytvorí možnosti možnosti do tlačítok
     *
     *  @param correctWord Predstavuje hádané slovíčko
     *  @param language Jazyk v ktorom majú byť slovíčka v tlačítkach
     *  @return Slovíčka ktoré sa budú nachádzať v tlačítkach pod otázkou
     */
    suspend operator fun invoke(correctWord:Word,language:String): List<Word> {
        return repository.getButtonOptions(correctWord,language)
    }
}