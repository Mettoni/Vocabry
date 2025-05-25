package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GenerateButtonOptions
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase

class MainViewModelFactory(
    private val addWord: AddWordUseCase,
    private val removeWord: RemoveWordUseCase,
    private val getAllWords: GetListUseCase,
    private val generateButtons: GenerateButtonOptions
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            addWord,
            removeWord,
            getAllWords,
            generateButtons
        ) as T
    }
}