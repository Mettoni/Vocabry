package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabry.domain.model.Word
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel pre obrazovku pridávania slov.
 * Zodpovedá za manipuláciu so slovami – pridávanie, mazanie, načítavanie podľa kategórií a jazyka.
 *
 * @param addWordUseCase Use case pre pridanie slova.
 * @param removeWordUseCase Use case pre odstránenie slova.
 * @param getWordsByCategory Use case na získanie slov podľa kategórie a jazyka.
 */
class WordAddingScreenViewModel(
    private val addWordUseCase: AddWordUseCase,
    private val removeWordUseCase: RemoveWordUseCase,
    private val getWordsByCategory: GetWordsByCategoryUseCase
): ViewModel() {
    private val _wordList = MutableStateFlow<List<Word>>(emptyList())
    val wordList: StateFlow<List<Word>> = _wordList
    /**
     * Pridá nové slovo a aktualizuje celý zoznam slov pre daný jazyk.
     *
     * @param word Slovo, ktoré sa má pridať.
     * @param translation Preklad slova.
     * @param category Kategória slova.
     * @param language Jazyk slova.
     * @onFinished Callback, ktorý sa zavolá po dokončení operácie. Predvolene je prázdny.
     */
    fun addWord(word: String,translation: String, category: String,language:String, onFinished: () -> Unit = {}) {
        viewModelScope.launch {
            addWordUseCase(word,translation,category,language)
            loadWordsByCategory(category, language)
            onFinished()
        }
    }

    /**
     * Načíta slová z danej kategórie a jazyka.
     *
     * @param category Názov kategórie.
     * @param language Jazyk slov.
     */
    fun loadWordsByCategory(category: String,language:String){
        viewModelScope.launch {
            _wordList.value = getWordsByCategory(category,language)
        }
    }
    /**
     * Odstráni slovo z danej kategórie a znovu načíta slová v tejto kategórii.
     *
     * @param word Slovo, ktoré sa má odstrániť.
     * @param category Kategória, z ktorej sa má slovo odstrániť.
     * @param language Jazyk slov.
     * onFinished Callback, ktorý sa zavolá po dokončení operácie. Predvolene je prázdny.
     */
    fun removeWord(word:String,category: String,language:String,onFinished: () -> Unit = {}) {
        viewModelScope.launch {
            removeWordUseCase(word,category,language)
            loadWordsByCategory(category,language)
            onFinished()
        }
    }
    /**
     * Skontroluje, či slovo už existuje v danej kategórii a jazyku.
     *
     * @param word Slovo, ktoré sa má overiť.
     * @param category Kategória, v ktorej sa má hľadať.
     * @param language Jazyk, v ktorom sa má hľadať.
     * @param onResult Callback, ktorý vráti true, ak slovo existuje, inak false.
     */
    fun checkIfWordExists(word: String, category: String,language:String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val wordsInCategory = getWordsByCategory(category,language)
            val exists = wordsInCategory.any {
                it.word.equals(word.trim(), ignoreCase = true)
            }
            onResult(exists)
        }
    }
}