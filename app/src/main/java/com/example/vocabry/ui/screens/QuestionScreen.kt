package com.example.vocabry.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vocabry.ui.viewModel.MainViewModel
import com.example.vocabry.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(viewModel: MainViewModel, navHostController: NavHostController) {
    val guessedWord by viewModel.correctWord.collectAsState()
    val options by viewModel.options.collectAsState()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        viewModel.generateNewQuestion()
    }


    val poppins = FontFamily(
        Font(R.font.poppins)
    )

    //val translator = Translator()

    TopAppBar(
        title = { Text("Otázka")},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black),
        navigationIcon = {
            IconButton(onClick = {navHostController.popBackStack()}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Späť"
                )
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(
            start = 32.dp,
            end = 32.dp,
            top = if(isLandscape) 80.dp else 32.dp
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ako sa povie po anglicky: $guessedWord",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = poppins,
            modifier = Modifier.border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp)).padding(8.dp)  // nastav si hrúbku a farbu rámika
        )

        Spacer(modifier = Modifier.height(16.dp))

        for (i in 0..3) {
            val word = options.getOrNull(i) ?: continue
            //val translation = translator.translateBlocking(word, Language.ENGLISH, Language.SLOVAK)

            val isCorrect = word == guessedWord
            Button(
                onClick = {
                    println(if (isCorrect) "SPRÁVNE" else "NESPRÁVNE")
                },
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            ) {
                Text(word)
            }
        }
    }
}