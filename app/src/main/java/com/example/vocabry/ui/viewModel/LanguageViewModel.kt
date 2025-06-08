package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabry.domain.usecase.GetAllLanguagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
/**
 * ViewModel zodpovedný za správu jazykových údajov v aplikácii.
 *
 * Poskytuje funkcionalitu na načítanie dostupných jazykov a uchováva
 * aktuálne vybraný jazyk ako stav pre používateľské rozhranie.
 * Využíva [GetAllLanguagesUseCase] z doménovej vrstvy na získanie jazykov,
 * čím dodržiava princípy clean architektúry.
 *
 * @property getAllLanguagesUseCase UseCase na načítanie všetkých jazykov z úložiska.
 *
 * @property languages StateFlow zoznamu dostupných jazykov načítaných z úložiska.
 * @property selectedLanguage StateFlow aktuálne vybraného jazyka používateľom.
 */
class LanguageViewModel(
    private val getAllLanguagesUseCase: GetAllLanguagesUseCase
) : ViewModel() {

    private val _languages = MutableStateFlow<List<String>>(emptyList())
    val languages: StateFlow<List<String>> = _languages

    private val _selectedLanguage = MutableStateFlow("English")
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