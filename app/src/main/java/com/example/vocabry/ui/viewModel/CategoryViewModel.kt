package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabry.domain.model.Word
import com.example.vocabry.domain.usecase.GetAllCategoriesUseCase
import com.example.vocabry.domain.usecase.GetCategoriesByLanguageUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
/**
 * ViewModel zodpovedný za správu kategórií a slov v danej kategórii pre vybraný jazyk.
 *
 * @property getCategories UseCase na načítanie všetkých dostupných kategórií.
 * @property getWordsByCategory UseCase na načítanie slov podľa kategórie a jazyka.
 * @property getCategoriesByLanguageUseCase UseCase na načítanie kategórii podla jazyka
 */
class CategoryViewModel(
    private val getCategories: GetAllCategoriesUseCase,
    private val getWordsByCategory: GetWordsByCategoryUseCase,
    private val getCategoriesByLanguageUseCase: GetCategoriesByLanguageUseCase
): ViewModel() {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    private val _selectedLanguage = MutableStateFlow("English")

    /**
     * Načíta všetky dostupné kategórie z databázy a uloží ich do [categories].
     */
    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = getCategories()
        }
    }
    /**
     * Načíta všetky kategórie pre daný jazyk a uloží ich do stavu [_categories].
     *
     * Funkcia spúšťa coroutine v rámci [viewModelScope], zavolá use case
     * [getCategoriesByLanguageUseCase] a aktualizuje hodnotu [_categories],
     * ktorú môže UI pozorovať a zobraziť.
     *
     * @param language Jazyk, pre ktorý sa majú načítať kategórie.
     */
    fun loadCategoriesByLanguage(language: String) {
        viewModelScope.launch {
            _categories.value = getCategoriesByLanguageUseCase(language)
        }
    }

    /**
     * Nastaví vybranú kategóriu a jazyk, a načíta príslušné slová do [words].
     *
     * @param category Názov kategórie, ktorú používateľ vybral.
     * @param language Jazyk, ktorý je momentálne nastavený.
     */
    fun selectedCategory(category: String, language: String) {
        viewModelScope.launch {
            _words.value = getWordsByCategory(category, language)
            _selectedCategory.value = category
            _selectedLanguage.value = language
        }
    }
}