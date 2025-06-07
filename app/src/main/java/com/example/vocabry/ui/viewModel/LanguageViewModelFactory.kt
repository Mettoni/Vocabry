package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vocabry.domain.usecase.GetAllLanguagesUseCase
/**
 * Factory trieda pre vytváranie inštancií [LanguageViewModel].
 *
 * Zabezpečuje poskytnutie potrebného use case [GetAllLanguagesUseCase] pri vytváraní inštancie.
 * Overuje, či požadovaný ViewModel zodpovedá typu [LanguageViewModel], a v opačnom prípade
 * vyhodí výnimku [IllegalArgumentException].
 *
 * @property getAllLanguagesUseCase Use case pre získanie všetkých jazykov dostupných v aplikácii.
 *
 * @throws IllegalArgumentException ak požadovaný typ nie je [LanguageViewModel].
 */
class LanguageViewModelFactory(
    private val getAllLanguagesUseCase: GetAllLanguagesUseCase
) : ViewModelProvider.Factory {
    /**
     * Vytvára a vracia inštanciu ViewModelu požadovaného typu.
     *
     * Ak je požadovaná trieda typu [LanguageViewModel], vráti sa nová inštancia s poskytnutým use case.
     * V opačnom prípade vyhodí výnimku [IllegalArgumentException].
     *
     * @param modelClass Trieda ViewModelu, ktorú je potrebné vytvoriť.
     * @return Inštancia požadovaného ViewModelu.
     * @throws IllegalArgumentException ak požadovaný typ nie je [LanguageViewModel].
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
            return LanguageViewModel(getAllLanguagesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}