package com.example.vocabry

import android.app.Application
import com.example.vocabry.data.WordDatabase
import com.example.vocabry.data.WordRepository
import com.example.vocabry.data.notification.NotificationScheduler
import com.example.vocabry.domain.usecase.AddScoreUseCase
import com.example.vocabry.domain.usecase.AddWordIfNotExistsUseCase
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GenerateQuestionUseCase
import com.example.vocabry.domain.usecase.GetAllCategoriesUseCase
import com.example.vocabry.domain.usecase.GetAllLanguagesUseCase
import com.example.vocabry.domain.usecase.GetButtonOptionsUseCase
import com.example.vocabry.domain.usecase.GetCategoriesByLanguageUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.NotificationUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase

/**
 * Aplikačná trieda `VocabryApplication`, ktorá slúži ako hlavný bod na
 * manuálnu inicializáciu závislostí (Dependency Injection) pre celú aplikáciu.
 *
 * Táto trieda vytvára a uchováva inštancie `UseCase` objektov, ktoré sú
 * neskôr poskytované jednotlivým ViewModelom a ďalším komponentom aplikácie.
 *
 * Používa sa ako vlastná `Application` trieda, ktorá je definovaná v `AndroidManifest.xml`.
 *
 *
 * @property wordRepository Inštancia repozitára pre prístup k slovám v databáze.
 * @property addWordUseCase UseCase pre pridanie slova do databázy.
 * @property removeWordUseCase UseCase pre odstránenie slova z databázy.
 * @property getAllCategoriesUseCase UseCase pre získanie všetkých kategórií.
 * @property getAllLanguagesUseCase UseCase pre získanie všetkých jazykov.
 * @property getListUseCase UseCase pre získanie všetkých slov v danom jazyku.
 * @property getWordsByCategoryUseCase UseCase pre načítanie slov podľa kategórie a jazyka.
 * @property getButtonOptionsUseCase UseCase pre generovanie náhodných odpovedí pre tlačidlá.
 * @property generateQuestionUseCase UseCase pre zostavenie otázky a možností.
 * @property addWordIfNotExistsUseCase UseCase, ktorý pridá slovo, ak ešte neexistuje.
 * @property addScoreUseCase UseCase pre správu skóre.
 * @property notificationUseCase UseCase pre naplánovanie a zobrazenie notifikácií.
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
    lateinit var getCategoriesByLanguageUseCase: GetCategoriesByLanguageUseCase
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
    lateinit var generateQuestionUseCase: GenerateQuestionUseCase
        private set
    lateinit var addWordIfNotExistsUseCase: AddWordIfNotExistsUseCase
        private set
    lateinit var addScoreUseCase: AddScoreUseCase
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
        getCategoriesByLanguageUseCase = GetCategoriesByLanguageUseCase(wordRepository)
        getAllLanguagesUseCase = GetAllLanguagesUseCase(wordRepository)
        getListUseCase = GetListUseCase(wordRepository)
        getWordsByCategoryUseCase = GetWordsByCategoryUseCase(wordRepository)
        getButtonOptionsUseCase = GetButtonOptionsUseCase(wordRepository)
        notificationUseCase = NotificationUseCase(NotificationScheduler())
        generateQuestionUseCase = GenerateQuestionUseCase(getListUseCase,getWordsByCategoryUseCase,getButtonOptionsUseCase)
        addWordIfNotExistsUseCase = AddWordIfNotExistsUseCase(getWordsByCategoryUseCase,addWordUseCase)
        addScoreUseCase = AddScoreUseCase()

        notificationUseCase.invoke(applicationContext)
    }
}