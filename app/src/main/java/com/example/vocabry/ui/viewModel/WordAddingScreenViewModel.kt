package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabry.domain.model.Word
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WordAddingScreenViewModel(
    private val addWordUseCase: AddWordUseCase,
    private val removeWordUseCase: RemoveWordUseCase,
    private val getWordsByCategory: GetWordsByCategoryUseCase,
    private val getList: GetListUseCase
): ViewModel() {
    private val _wordList = MutableStateFlow<List<Word>>(emptyList())
    val wordList: StateFlow<List<Word>> = _wordList

    fun addWord(word: String,translation: String, category: String,language:String) {
        viewModelScope.launch {
            addWordUseCase(word,translation,category,language)
            refreshWords(language)
        }
    }

    fun loadWordsByCategory(category: String,language:String){
        viewModelScope.launch {
            _wordList.value = getWordsByCategory(category,language)
        }
    }

    fun removeWord(word:String,category: String,language:String) {
        viewModelScope.launch {
            removeWordUseCase(word,category,language)
            loadWordsByCategory(category,language)
        }
    }

    fun checkIfWordExists(word: String, category: String,language:String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val wordsInCategory = getWordsByCategory(category,language)
            val exists = wordsInCategory.any {
                it.word.equals(word.trim(), ignoreCase = true)
            }
            onResult(exists)
        }
    }

    fun refreshWords(language:String) {
        viewModelScope.launch {
            _wordList.value = getList(language)
        }
    }
}