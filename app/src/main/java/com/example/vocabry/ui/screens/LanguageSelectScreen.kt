package com.example.vocabry.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import com.example.vocabry.R
import com.example.vocabry.ui.viewModel.LanguageViewModel

/**
 * Composable funkcia, ktorá zobrazuje obrazovku výberu jazyka.
 *
 * Používateľ si môže vybrať jazyk, v ktorom sa chce učiť slovíčka. Po výbere
 * sa aktuálne nastavený jazyk uloží do ViewModelu a používateľ je presmerovaný
 * na obrazovku výberu kategórie.
 *
 * @param languageViewModel ViewModel zodpovedný za uchovávanie a správu zoznamu jazykov
 *                  a aktuálne vybraného jazyka.
 * @param navHostController Navigačný kontrolér, ktorý zabezpečuje prechody medzi obrazovkami.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectScreen(
    languageViewModel: LanguageViewModel,
    navHostController: NavHostController,
) {
    val languages by languageViewModel.languages.collectAsState()

    LaunchedEffect(Unit) {
        languageViewModel.loadLanguages()
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
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black),
        navigationIcon = {
            IconButton(onClick = dropUnlessResumed{navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.spat)
                )
            }
        }
    )

    Column(modifier = Modifier.padding(top = 90.dp,start = 10.dp)) {
        Text(stringResource(R.string.vyber_jazyku), style = MaterialTheme.typography.titleLarge)


        languages.forEach { lang: String ->
            Button(
                onClick = dropUnlessResumed{
                    languageViewModel.setLanguage(lang)
                    navHostController.navigate("category")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .height(60.dp)
            ) {
                Text(lang)
            }
        }
    }
}