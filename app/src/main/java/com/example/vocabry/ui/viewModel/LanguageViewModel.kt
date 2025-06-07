package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabry.domain.usecase.GetAllLanguagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val getAllLanguagesUseCase: GetAllLanguagesUseCase
) : ViewModel() {

    private val _languages = MutableStateFlow<List<String>>(emptyList())
    /**
     * Verejne dostupný [StateFlow] so zoznamom jazykov.
     */
    val languages: StateFlow<List<String>> = _languages

    private val _selectedLanguage = MutableStateFlow("English")
    /**
     * Verejne dostupný [StateFlow] s aktuálne vybraným jazykom.
     */
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    /**
     * Nastaví nový jazyk ako aktuálne vybraný.
     *
     * @param language Jazyk, ktorý sa má nastaviť ako vybraný.
     */
    fun setLanguage(language: String) {
        _selectedLanguage.value = language
    }

    /**
     * Načíta všetky dostupné jazyky pomocou [getAllLanguagesUseCase]
     * a aktualizuje hodnotu v [_languages].
     */
    fun loadLanguages() {
        viewModelScope.launch {
            val langs = getAllLanguagesUseCase()
            _languages.value = langs
        }
    }
}