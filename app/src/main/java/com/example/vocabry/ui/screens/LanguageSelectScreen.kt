package com.example.vocabry.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vocabry.ui.viewModel.LanguageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectScreen(
    viewModel: LanguageViewModel,
    navController: NavController,
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val languages by viewModel.languages.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLanguages()
    }

    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black),
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Späť"
                )
            }
        }
    )

    Column(modifier = Modifier.padding(top = 90.dp,start = 10.dp)) {
        Text("Vyber jazyk", style = MaterialTheme.typography.titleLarge)


        languages.forEach { lang: String ->
            Button(
                onClick = {
                    viewModel.setLanguage(lang)
                    navController.navigate("category")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                Text(lang)
            }
        }
    }
}