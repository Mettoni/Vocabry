package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase

/**
 * Factory trieda pre vytváranie inštancií [WordAddingScreenViewModel] so závislosťami potrebnými pre prácu so slovíčkami.
 *
 * @property addWordUseCase Use case pre pridanie nového slovíčka.
 * @property removeWordUseCase Use case pre odstránenie slovíčka.
 * @property getWordsByCategory Use case pre získanie slov podľa kategórie.
 * @throws IllegalArgumentException ak požadovaný typ nie je [WordAddingScreenViewModel].
 */
class WordAddingScreenViewModelFactory(
    private val addWordUseCase: AddWordUseCase,
    private val removeWordUseCase: RemoveWordUseCase,
    private val getWordsByCategory: GetWordsByCategoryUseCase
) : ViewModelProvider.Factory {

    /**
     * Vytvorí a vráti inštanciu [WordAddingScreenViewModel], ak je požadovaný typ správny.
     *
     * @param modelClass Trieda ViewModelu, ktorú je potrebné vytvoriť.
     * @return Inštancia [WordAddingScreenViewModel].
     * @throws IllegalArgumentException ak požadovaný typ nie je [WordAddingScreenViewModel].
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordAddingScreenViewModel::class.java)) {
            return WordAddingScreenViewModel(
                addWordUseCase,
                removeWordUseCase,
                getWordsByCategory
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}