package com.example.vocabry.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import com.example.vocabry.R
import com.example.vocabry.ui.viewModel.CategoryViewModel
import com.example.vocabry.ui.viewModel.LanguageViewModel

/**
 * Composable funkcia zodpovedná za zobrazenie obrazovky výberu kategórie.
 *
 * Táto obrazovka umožňuje používateľovi vybrať si kategóriu pre učenie slovíčok v závislosti
 * od aktuálne vybraného jazyka. Po výbere sa používateľ presmeruje na obrazovku so začiatkom testu.
 *
 * @param categoryViewModel ViewModel, ktorý poskytuje zoznam kategórií a umožňuje nastaviť vybranú kategóriu.
 * @param languageViewModel ViewModel, ktorý poskytuje aktuálne vybraný jazyk.
 * @param navHostController Navigačný kontrolér, ktorý umožňuje prechod medzi obrazovkami.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelect(categoryViewModel: CategoryViewModel, languageViewModel: LanguageViewModel, navHostController: NavHostController) {
    val categories by categoryViewModel.categories.collectAsState()
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

    LaunchedEffect(Unit) {
        categoryViewModel.loadCategoriesByLanguage(selectedLanguage)
    }

    Box(modifier = Modifier.background(Color(0xFF80B6F0)).fillMaxSize()) {
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
            IconButton(onClick = dropUnlessResumed {navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.sp)
                )
            }
        }
    )

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .verticalScroll(scrollState)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.vyber_katgoriu), modifier = Modifier.padding(top = 80.dp))

        categories.forEach { category ->
            Button(
                onClick = dropUnlessResumed{
                    categoryViewModel.selectedCategory(category,selectedLanguage)
                    navHostController.navigate("start")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp).height(60.dp)
            ) {
                Text(text = category)
            }
        }
    }
}