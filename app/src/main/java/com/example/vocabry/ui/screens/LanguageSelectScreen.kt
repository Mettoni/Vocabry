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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vocabry.R
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
                    .padding(3.dp).height(60.dp)
            ) {
                Text(lang)
            }
        }
    }
}