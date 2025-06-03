package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.GetAllLanguagesUseCase

class LanguageViewModelFactory(
    private val getAllLanguagesUseCase: GetAllLanguagesUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
            return LanguageViewModel(getAllLanguagesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}