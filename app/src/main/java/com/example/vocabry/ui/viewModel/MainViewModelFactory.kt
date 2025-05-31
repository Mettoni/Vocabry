package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GenerateButtonOptions
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase

class MainViewModelFactory(
    private val addWord: AddWordUseCase,
    private val removeWord: RemoveWordUseCase,
    private val getAllWords: GetListUseCase,
    private val generateButtons: GenerateButtonOptions,
    private val getWordsByCategoryUseCase: GetWordsByCategoryUseCase,
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                addWord,
                removeWord,
                getWordsByCategoryUseCase,
                getAllWords,
                generateButtons,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}