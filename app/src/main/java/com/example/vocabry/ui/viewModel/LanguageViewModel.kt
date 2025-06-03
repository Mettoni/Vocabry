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
    val languages: StateFlow<List<String>> = _languages

    private val _selectedLanguage = MutableStateFlow("English") // default
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    fun setLanguage(language: String) {
        _selectedLanguage.value = language
    }

    fun loadLanguages() {
        viewModelScope.launch {
            _languages.value = getAllLanguagesUseCase()
        }
    }
}