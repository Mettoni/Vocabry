package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.GetAllCategoriesUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase

/**
 * Factory trieda pre vytvorenie inštancie [CategoryViewModel] so zadanými UseCase závislosťami.
 *
 * Táto trieda implementuje [ViewModelProvider.Factory], aby bolo možné vytvárať ViewModel
 * s parametrami, ktoré nie sú podporované štandardným ViewModelProviderom.
 *
 * @property getAllCategoriesUseCase UseCase na načítanie všetkých kategórií.
 * @property getWordsByCategoryUseCase UseCase na načítanie slov podľa kategórie a jazyka.
 * @throws IllegalArgumentException ak požadovaný modelClass nie je podporovaný.
 */
class CategoryViewModelFactory(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getWordsByCategoryUseCase: GetWordsByCategoryUseCase
) : ViewModelProvider.Factory {
    /**
     * Vytvára inštanciu [CategoryViewModel] pomocou poskytnutých UseCase závislostí.
     *
     * @param modelClass Trieda požadovaného ViewModelu.
     * @return Inštancia požadovaného ViewModelu.
     * @throws IllegalArgumentException ak požadovaný modelClass nie je podporovaný.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(
                getAllCategoriesUseCase,
                getWordsByCategoryUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")

    }
}