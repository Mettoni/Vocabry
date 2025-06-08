package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.AddScoreUseCase
import com.example.vocabry.domain.usecase.AddWordIfNotExistsUseCase
import com.example.vocabry.domain.usecase.GenerateQuestionUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase

/**
 * Factory trieda pre vytváranie inštancií [QuestionScreenViewModel] s potrebnými use case závislosťami.
 *
 * @property getWordsByCategory Use case pre získanie slov podľa kategórie.
 * @property getList Use case pre získanie počtu otázok.
 * @property generateQuestionUseCase Usecase ktorý generuje otázky pre vzdelávanie.
 * @throws IllegalArgumentException ak požadovaný typ nie je [QuestionScreenViewModel].
 */
class QuestionScreenViewModelFactory(
    private val getWordsByCategory: GetWordsByCategoryUseCase,
    private val getList: GetListUseCase,
    private val addWordIfNotExistsUseCase: AddWordIfNotExistsUseCase,
    private val generateQuestionUseCase: GenerateQuestionUseCase,
    private val addScoreUseCase: AddScoreUseCase
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
                getWordsByCategory,
                getList,
                addWordIfNotExistsUseCase,
                generateQuestionUseCase,
                addScoreUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}