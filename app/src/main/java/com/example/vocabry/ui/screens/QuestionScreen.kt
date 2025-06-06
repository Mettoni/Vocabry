package com.example.vocabry.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import com.example.vocabry.R
import com.example.vocabry.domain.Word
import com.example.vocabry.ui.viewModel.CategoryViewModel
import com.example.vocabry.ui.viewModel.LanguageViewModel
import com.example.vocabry.ui.viewModel.MainViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(viewModel: MainViewModel,categoryViewModel: CategoryViewModel,languageViewModel: LanguageViewModel, navHostController: NavHostController) {
    val guessedWord by viewModel.correctWord.collectAsState()
    val options by viewModel.options.collectAsState()
    val gameFinished by viewModel.gameFinished.collectAsState()
    val category by categoryViewModel.selectedCategory.collectAsState()
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val score by viewModel.score.collectAsState()
    val numberOfQuestions by viewModel.questions.collectAsState()
    LaunchedEffect(category) {
        category?.let {
            viewModel.generateNewQuestion(it,selectedLanguage)
            viewModel.numberOfQuestions(it,selectedLanguage)
        }

    }

    val poppins = FontFamily(
        Font(R.font.poppins)
    )

    val languageText = when (selectedLanguage.lowercase()) {
        "english","en"->"anglicky"
        "german","ge"->"nemecky"
        "russian","ru"->"rusky"
        else -> selectedLanguage
    }

    Box(modifier = Modifier
        .background(Color(0xFF80B6F0))
        .fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    TopAppBar(
        title = { Text(stringResource(R.string.otazka))},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent,
            titleContentColor = Color.Black, navigationIconContentColor = Color.Black),
        navigationIcon = {
            IconButton(onClick = dropUnlessResumed{navHostController.popBackStack()
                viewModel.resetGame()}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.sp)
                )
            }
        }
    )
    if(gameFinished) {
        EndGame(
            score = score,
            correctAnswers = numberOfQuestions,
            onRestart = {
                viewModel.resetGame()
                category?.let{
                    viewModel.generateNewQuestion(it,selectedLanguage)
                }
            },
            onBackToMenu = {
                viewModel.resetGame()
                navHostController.popBackStack()
            }

        )
    } else {
        Text(
            text = stringResource(R.string.skore, score),
            fontSize = 20.sp,
            fontFamily = poppins,
            modifier = Modifier.padding(top = 100.dp)
        )
        if(guessedWord != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = if (isLandscape) 80.dp else 32.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ako sa povie po $languageText: ${guessedWord?.word}",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = poppins,
                    modifier = Modifier
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                for (i in 0..3) {
                    val word = options.getOrNull(i) ?: continue
                    category?.let { cat ->
                        guessedWord?.let { correct ->
                            BetterButtons(
                                word = word,
                                isCorrect = word == correct,
                                category = cat,
                                correctWord = correct,
                                language = selectedLanguage,
                                isLandscape = isLandscape,
                                viewModel = viewModel
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun EndGame(
    score:Int,
    correctAnswers:Int,
    onRestart: ()-> Unit,
    onBackToMenu: ()-> Unit

){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.sprava_skore, score, correctAnswers),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(onClick = onRestart, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Text(stringResource(R.string.restartovat))
        }
        Button(onClick = onBackToMenu, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Text(stringResource(R.string.spat_do_menu))
        }
    }
}

@Composable
fun BetterButtons(
    word: Word,
    isCorrect: Boolean,
    correctWord: Word,
    category: String,
    language:String,
    isLandscape:Boolean,
    viewModel: MainViewModel
) {
    var clicked by remember { mutableStateOf(false) }
    var showColor by remember { mutableStateOf(false) }
    var triggerNext by remember { mutableStateOf(false) }

    val buttonHeight = if(isLandscape) 40.dp else 60.dp

    val buttonColor = when {
        !clicked -> MaterialTheme.colorScheme.primary
        isCorrect -> Color.Green
        else -> Color.Red
    }

    if (triggerNext) {
        LaunchedEffect(Unit) {
            delay(500)
            clicked = false
            showColor = false
            viewModel.generateNewQuestion(category,language)
            triggerNext = false
        }
    }

    Button(
        onClick =dropUnlessResumed{
            if (!clicked) {
                clicked = true
                showColor = true
                if (isCorrect) {
                    viewModel.addScore(100)
                    triggerNext = true
                } else {
                    viewModel.checkIfWordExists(correctWord.word, "Chyby",language) { exists ->
                        if (!exists) {
                            viewModel.addWord(correctWord.word, correctWord.translated, "Chyby",language)
                        }
                        triggerNext = true
                    }
                }

            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(buttonHeight),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text(text = word.translated)
    }
}