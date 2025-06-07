package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GetButtonOptionsUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.NotificationUseCase

/**
 * Factory trieda pre vytváranie inštancií [QuestionScreenViewModel] s potrebnými use case závislosťami.
 *
 * @property addWordUseCase Use case pre pridávanie slov.
 * @property getWordsByCategory Use case pre získanie slov podľa kategórie.
 * @property getList Use case pre získanie počtu otázok.
 * @property generateOptions Use case pre generovanie možností odpovede.
 * @property notifyUserUseCase Use case pre notifikáciu používateľa.
 * @throws IllegalArgumentException ak požadovaný typ nie je [QuestionScreenViewModel].
 */
class QuestionScreenViewModelFactory(
    private val addWordUseCase: AddWordUseCase,
    private val getWordsByCategory: GetWordsByCategoryUseCase,
    private val getList: GetListUseCase,
    private val generateOptions: GetButtonOptionsUseCase,
    private val notifyUserUseCase: NotificationUseCase
) : ViewModelProvider.Factory {

    /**
     * Vytvára a vracia inštanciu [QuestionScreenViewModel], ak požadovaný typ zodpovedá.
     *
     * @param modelClass Trieda ViewModelu, ktorú je potrebné vytvoriť.
     * @return Inštancia [QuestionScreenViewModel].
     * @throws IllegalArgumentException ak požadovaný typ nie je [QuestionScreenViewModel].
     */
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