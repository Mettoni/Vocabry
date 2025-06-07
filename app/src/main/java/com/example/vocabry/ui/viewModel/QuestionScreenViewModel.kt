package com.example.vocabry.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabry.domain.model.Word
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GetButtonOptionsUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.NotificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * ViewModel, ktorý sa zaoberá o správu slovíčok,skóre a generovanie otázok
 *
 * @author Filip Ďurana
 * @param addWordUseCase UseCase na pridanie slovíčka
 * @param getWordsByCategory UseCase ktorý vráti všetky slovíčka z vybranej kategórie
 * @param getList UseCase na získanie všetkých slov
 * @param generateOptions UseCase ktorý vygeneruje možnosti do tlačítok
 * @param notifyUserUseCase UseCase na plánovanie notifikácií
 */
class QuestionScreenViewModel (
    private val addWordUseCase: AddWordUseCase,
    private val getWordsByCategory:GetWordsByCategoryUseCase,
    private val getList: GetListUseCase,
    private val generateOptions: GetButtonOptionsUseCase,
    private val notifyUserUseCase: NotificationUseCase
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
     * Vygeneruje novú otázku (správne slovo a možnosti do tlačítok)
     */
    fun generateNewQuestion(category: String,language:String) {
        viewModelScope.launch {
            _notEnoughWords.value = false
            val totalWords = getList(language)
            if(totalWords.size < 4) {
                _notEnoughWords.value = true
                return@launch
            }
            val allWordsInCategory = getWordsByCategory(category,language)
            // Zo zoznamu všetkych slov vyfiltruej všetky slová ktoré boli už použité
            val unusedWords = allWordsInCategory.filterNot{
                    used->_alreadyUsed.value.any{ it.word == used.word }
            }
            // Ak existujú ešte nejaké nepoužité slovíčká/o tak sa zo zoznamu náhodne vyberie nejaké slovo ako hádané, a vygenerujú sa k nemu možnosti do tlačítok
            if(!unusedWords.isEmpty()) {
                val correct = unusedWords.random()
                val options = generateOptions(correct,language)

                _correctWord.value = correct
                _options.value = options
                _alreadyUsed.value += correct
            } else {
                _gameFinished.value = true
            }
        }
    }

    /**
     *  Skontroluje, či dané slovo už existuje v kategórii a jazuyku
     */
    fun checkIfWordExists(word: String,translation: String, category: String,language:String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val wordsInCategory = getWordsByCategory(category,language)
            val exists = wordsInCategory.any {
                it.word.equals(word.trim(), ignoreCase = true)
            }

            if (!exists) {
                addWordUseCase(word, translation, category, language)
                refreshWords(language)
            }
            onResult(!exists)
        }
    }

    /**
     *  Resetuje hru
     */
    fun resetGame() {
        _alreadyUsed.value = emptyList()
        _score.value = 0
        _correctWord.value = null
        _options.value = emptyList()
        _gameFinished.value = false
    }

    /**
     * Obnoví zoznam slov pre daný jazyk
     */
    fun refreshWords(language:String) {
        viewModelScope.launch {
            _wordList.value = getList(language)
        }
    }

    /**
     * Pridá skóre hráčovi
     */
    fun addScore(score:Int) {
        _score.value += score
    }

    /**
     * Spustí naplánovanie dennej notifikácie
     */
    fun scheduleNotification(context: Context) {
        notifyUserUseCase.invoke(context)
    }

    /**
     * Spočíta otázky v danej kategórii a jazyku
     */
    fun numberOfQuestions(category: String, language: String) {
        viewModelScope.launch {
            val words = getWordsByCategory(category,language)
            _questions.value = words.size
        }
    }

    /**
     * Nastaví stav indikujúci, či je v hre nedostatok slov.
     *
     * Tento stav je určený pre UI, ktoré podľa neho môže napríklad
     * zobraziť varovanie používateľovi. Hodnota sa nastavuje do
     * pozorovateľného [StateFlow] `_notEnoughWords`.
     *
     * @param value `true`, ak nie je dostatok slov na generovanie otázky, inak `false`.
     */
    fun setNotEnoughWords(value: Boolean) {
        _notEnoughWords.value = value
    }

}