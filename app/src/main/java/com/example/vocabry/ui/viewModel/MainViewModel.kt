package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GenerateButtonOptions
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel (
    private val addWord: AddWordUseCase,
    private val removeWord: RemoveWordUseCase,
    private val getList: GetListUseCase,
    private val generateOptions: GenerateButtonOptions,
): ViewModel() {
    private val _wordList = MutableStateFlow<List<String>>(emptyList())
    val wordList: StateFlow<List<String>> = _wordList

    private val _options = MutableStateFlow<List<String>>(emptyList())
    val options: StateFlow<List<String>> = _options

    private val _correctWord = MutableStateFlow("")
    val correctWord: StateFlow<String> = _correctWord

    fun addWord(word:String) {
        addWord.invoke(word)
        refreshWords()
    }
    fun removeWord(word:String) {
        removeWord.invoke(word)
        refreshWords()
    }
    fun generateNewQuestion() {
        val allWords = getList()
        if(allWords.size >= 4) {
            val correct = allWords.random()
            val options = generateOptions.invoke(correct)

            _correctWord.value = correct
            _options.value = options
        }
    }
    fun refreshWords() {
        _wordList.value = getList()
    }

}