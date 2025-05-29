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
    private val removeWordUseCase: RemoveWordUseCase,
    private val getList: GetListUseCase,
    private val generateOptions: GenerateButtonOptions,
): ViewModel() {
    private val _wordList = MutableStateFlow<List<Word>>(emptyList())
    val wordList: StateFlow<List<Word>> = _wordList

    private val _options = MutableStateFlow<List<Word>>(emptyList())
    val options: StateFlow<List<Word>> = _options

    private val _correctWord = MutableStateFlow<Word?>(null)
    val correctWord: StateFlow<Word?> = _correctWord

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    fun addWord(word: String,translation: String, category: String) {
        viewModelScope.launch {
            addWordUseCase(word,translation,category)
            refreshWords()
        }
    }
    fun removeWord(word:String,category: String) {
        viewModelScope.launch {
            removeWordUseCase(word,category)
            refreshWords()
        }
    }
    fun generateNewQuestion() {
        viewModelScope.launch {
            val allWords = getList()
            if(allWords.size >= 4) {
                val correct = allWords.random()
                val options = generateOptions(correct)

                _correctWord.value = correct
                _options.value = options
            }
        }
    }
    fun refreshWords() {
        viewModelScope.launch {
            _wordList.value = getList()
        }
    }
    fun addScore(score:Int) {
        _score.value += score
    }

}