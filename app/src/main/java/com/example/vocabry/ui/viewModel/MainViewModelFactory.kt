package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GetButtonOptionsUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.NotificationUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase

class MainViewModelFactory(
    private val addWord: AddWordUseCase,
    private val removeWord: RemoveWordUseCase,
    private val getAllWords: GetListUseCase,
    private val generateButtons: GetButtonOptionsUseCase,
    private val getWordsByCategoryUseCase: GetWordsByCategoryUseCase,
    private val notifyUserUseCase: NotificationUseCase
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
                notifyUserUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}