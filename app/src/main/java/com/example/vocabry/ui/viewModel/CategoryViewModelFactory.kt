package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.GetAllCategoriesUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase

class CategoryViewModelFactory(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getWordsByCategoryUseCase: GetWordsByCategoryUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(
            getAllCategoriesUseCase,
            getWordsByCategoryUseCase
        ) as T
    }
}