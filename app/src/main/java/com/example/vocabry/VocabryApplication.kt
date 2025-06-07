package com.example.vocabry

import android.app.Application
import com.example.vocabry.data.WordDatabase
import com.example.vocabry.data.WordRepository
import com.example.vocabry.data.notification.NotificationScheduler
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GetAllCategoriesUseCase
import com.example.vocabry.domain.usecase.GetAllLanguagesUseCase
import com.example.vocabry.domain.usecase.GetButtonOptionsUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.NotificationUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase
/**
 * Aplikačná trieda `VocabryApplication`, ktorá inicializuje všetky potrebné
 * závislosti a use case-y pri spustení aplikácie.
 *
 * Táto trieda funguje ako centrálny bod pre konfiguráciu DI (dependency injection)
 * a poskytuje zdieľané inštancie `UseCase` objektov a `WordRepository`.
 *
 * @property wordRepository Inštancia repozitára na prístup k dátam slov.
 * @property addWordUseCase UseCase pre pridanie slova do databázy.
 * @property removeWordUseCase UseCase pre odstránenie slova z databázy.
 * @property getAllCategoriesUseCase UseCase pre získanie všetkých kategórií.
 * @property getAllLanguagesUseCase UseCase pre získanie všetkých jazykov.
 * @property getListUseCase UseCase pre získanie zoznamu slov.
 * @property getWordsByCategoryUseCase UseCase pre načítanie slov podľa kategórie a jazyka.
 * @property getButtonOptionsUseCase UseCase pre generovanie náhodných odpovedí pre tlačítka.
 * @property notificationUseCase UseCase pre plánovanie a zobrazovanie notifikácií.
 */
class VocabryApplication: Application() {
    lateinit var wordRepository: WordRepository
        private set

    lateinit var addWordUseCase: AddWordUseCase
        private set
    lateinit var removeWordUseCase: RemoveWordUseCase
        private set
    lateinit var getAllCategoriesUseCase: GetAllCategoriesUseCase
        private set
    lateinit var getAllLanguagesUseCase: GetAllLanguagesUseCase
        private set
    lateinit var getListUseCase: GetListUseCase
        private set
    lateinit var getWordsByCategoryUseCase: GetWordsByCategoryUseCase
        private set
    lateinit var getButtonOptionsUseCase: GetButtonOptionsUseCase
        private set
    lateinit var notificationUseCase: NotificationUseCase
        private set
    /**
     * Inicializuje všetky závislosti potrebné pre fungovanie aplikácie.
     * Táto metóda sa spustí automaticky pri štarte aplikácie.
     */
    override fun onCreate() {
        super.onCreate()

        val dao = WordDatabase.getDatabase(applicationContext).wordDao()
        wordRepository = WordRepository(dao)

        addWordUseCase = AddWordUseCase(wordRepository)
        removeWordUseCase = RemoveWordUseCase(wordRepository)
        getAllCategoriesUseCase = GetAllCategoriesUseCase(wordRepository)
        getAllLanguagesUseCase = GetAllLanguagesUseCase(wordRepository)
        getListUseCase = GetListUseCase(wordRepository)
        getWordsByCategoryUseCase = GetWordsByCategoryUseCase(wordRepository)
        getButtonOptionsUseCase = GetButtonOptionsUseCase(wordRepository)
        notificationUseCase = NotificationUseCase(NotificationScheduler())
    }
}