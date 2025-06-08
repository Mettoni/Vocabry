package com.example.vocabry.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabry.domain.model.Word
import com.example.vocabry.domain.usecase.AddScoreUseCase
import com.example.vocabry.domain.usecase.AddWordIfNotExistsUseCase
import com.example.vocabry.domain.usecase.GenerateQuestionUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * ViewModel zodpovedný za logiku hry vo forme otázok a odpovedí na slovíčka.
 *
 * Spravuje aktuálnu otázku, možnosti odpovedí, skóre hráča a stav hry.
 * Spolupracuje s príslušnými use case triedami na získavanie slov,
 * generovanie otázok, pridávanie nesprávnych odpovedí do kategórie "Chyby"
 * a správu skóre.
 *
 * @property getWordsByCategory UseCase na získanie slov z danej kategórie a jazyka.
 * @property getList UseCase na získanie celého zoznamu slov pre jazyk.
 * @property addWordIfNotExistsUseCase UseCase na pridanie slova do databázy, ak ešte neexistuje.
 * @property generateQuestion UseCase na vygenerovanie novej otázky s možnosťami.
 * @property addScoreUseCase UseCase na manipuláciu so skóre hráča.
 *
 * @property options StateFlow zoznamu aktuálnych odpoveďových možností pre otázku.
 * @property correctWord StateFlow správneho slovíčka pre aktuálnu otázku.
 * @property score StateFlow aktuálneho skóre hráča.
 * @property gameFinished StateFlow indikujúci, či sa hra skončila.
 * @property questions StateFlow počtu položených otázok v rámci aktuálnej hry.
 * @property notEnoughWords StateFlow indikujúci, že nie je dostatok slov na vytvorenie otázky.
 */
class QuestionScreenViewModel (
    private val getWordsByCategory:GetWordsByCategoryUseCase,
    private val getList: GetListUseCase,
    private val addWordIfNotExistsUseCase: AddWordIfNotExistsUseCase,
    private val generateQuestion: GenerateQuestionUseCase,
    private val addScoreUseCase: AddScoreUseCase,
): ViewModel() {
    private val _wordList = MutableStateFlow<List<Word>>(emptyList())

    private val _options = MutableStateFlow<List<Word>>(emptyList())
    val options: StateFlow<List<Word>> = _options

    private val _correctWord = MutableStateFlow<Word?>(null)
    val correctWord: StateFlow<Word?> = _correctWord

    private val _alreadyUsed = MutableStateFlow<List<Word>>(emptyList())

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _gameFinished = MutableStateFlow(false)
    val gameFinished: StateFlow<Boolean> = _gameFinished

    private val _questions = MutableStateFlow(0)
    val questions: StateFlow<Int> = _questions

    private val _notEnoughWords = MutableStateFlow(false)
    val notEnoughWords: StateFlow<Boolean> = _notEnoughWords

    /**
     * Vygeneruje novú otázku na základe zvolenej kategórie a jazyka.
     *
     * Funkcia použije [GenerateQuestionUseCase] na získanie správneho slova a možností odpovedí,
     * pričom zohľadňuje už použité slová. Výsledok sa spracuje nasledovne:
     * - Ak nie je dostatok slov, nastaví [_notEnoughWords] na `true`.
     * - Ak sú všetky slová použité, nastaví [_gameFinished] na `true`.
     * - Ak je otázka úspešne vygenerovaná, aktualizuje [_correctWord] a [_options],
     *   a pridá správne slovo do zoznamu už použitých.
     *
     * @param category Kategória, z ktorej sa majú generovať slová.
     * @param language Jazyk, pre ktorý sa generuje otázka.
     */
    fun generateNewQuestion(category: String, language: String) {
        viewModelScope.launch {
            val result = generateQuestion.invoke(category, language, _alreadyUsed.value)

            when {
                result.notEnoughWords -> {
                    _notEnoughWords.value = true
                }

                result.gameFinished -> {
                    _gameFinished.value = true
                }

                result.success -> {
                    _correctWord.value = result.correctWord
                    _options.value = result.options
                    result.correctWord?.let {
                        _alreadyUsed.value = _alreadyUsed.value + it
                    }
                }
            }
        }
    }

    /**
     * Overí, či dané slovo už existuje v zadanej kategórii a jazyku.
     *
     * Ak slovo ešte neexistuje, pridá ho pomocou [AddWordIfNotExistsUseCase]
     * a následne obnoví zoznam slov. Výsledok operácie je vrátený
     * prostredníctvom spätného volania [onResult].
     *
     * @param word Slovo, ktoré sa má overiť a prípadne pridať.
     * @param translation Preklad slova.
     * @param category Kategória, do ktorej slovo patrí.
     * @param language Jazyk, pre ktorý sa slovo overuje/pridáva.
     * @param onResult Callback, ktorý signalizuje, či bolo slovo pridané (`true`), alebo už existovalo (`false`).
     */
    fun checkAndAddWord(
        word: String,
        translation: String,
        category: String,
        language: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val added = addWordIfNotExistsUseCase(
                word, translation, category, language,
                afterAdd = { refreshWords(language) }
            )
            onResult(added)
        }
    }

    /**
     *  Resetuje hru
     */
    fun resetGame() {
        addScoreUseCase.reset()
        _alreadyUsed.value = emptyList()
        _score.value = 0
        _correctWord.value = null
        _options.value = emptyList()
        _gameFinished.value = false
    }

    /**
     * Obnoví zoznam slov pre daný jazyk
     *
     * @param language Jazyk v ktorom chcem obnoviť zoznam slov
     */
    fun refreshWords(language:String) {
        viewModelScope.launch {
            _wordList.value = getList(language)
        }
    }

    /**
     * Pridá skóre hráčovi
     */
    fun onCorrectAnswer() {
        _score.value = addScoreUseCase(1)
    }

    /**
     * Spočíta otázky v danej kategórii a jazyku
     *
     * @param category V ktorej kategórii chceme zistit počet otázok
     * @param language V ktorom jazyku chcem zistiť počet otázok
     */
    fun numberOfQuestions(category: String, language: String) {
        viewModelScope.launch {
            val words = getWordsByCategory(category,language)
            _questions.value = words.size
        }
    }

    /**
     * Nastavuje boolean na požadovanú hodnotu
     * @param value Hodnota na ktorú chceme daný boolean nastaviť
     */
    fun setNotEnoughWords(value: Boolean) {
        _notEnoughWords.value = value
    }

}