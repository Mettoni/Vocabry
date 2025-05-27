package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabry.domain.Word
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GenerateButtonOptions
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel (
    private val addWordUseCase: AddWordUseCase,
    private val removeWord: RemoveWordUseCase,
    private val getList: GetListUseCase,
    private val generateOptions: GenerateButtonOptions,
): ViewModel() {
    private val _wordList = MutableStateFlow<List<Word>>(emptyList())
    val wordList: StateFlow<List<Word>> = _wordList

    private val _options = MutableStateFlow<List<String>>(emptyList())
    val options: StateFlow<List<String>> = _options

    private val _correctWord = MutableStateFlow("")
    val correctWord: StateFlow<String> = _correctWord

    fun addWord(word: String,translation: String, category: String) {
        viewModelScope.launch {
            addWordUseCase(word,translation,category)
            refreshWords()
        }
    }
    fun removeWord(word:String,category: String) {
        viewModelScope.launch {
            removeWord(word,category)
            refreshWords()
        }
    }
    fun generateNewQuestion() {
        viewModelScope.launch {
            val allWords = getList()
            if(allWords.size >= 4) {
                val correct = allWords.random()
                val options = generateOptions(correct.word)

                _correctWord.value = correct.word
                _options.value = options
            }
        }
    }
    fun refreshWords() {
        viewModelScope.launch {
            _wordList.value = getList()
        }
    }

}