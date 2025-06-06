package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GetButtonOptionsUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.NotificationUseCase

class QuestionScreenViewModelFactory(
    private val addWordUseCase: AddWordUseCase,
    private val getWordsByCategory: GetWordsByCategoryUseCase,
    private val getList: GetListUseCase,
    private val generateOptions: GetButtonOptionsUseCase,
    private val notifyUserUseCase: NotificationUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionScreenViewModel::class.java)) {
            return QuestionScreenViewModel(
                addWordUseCase,
                getWordsByCategory,
                getList,
                generateOptions,
                notifyUserUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}