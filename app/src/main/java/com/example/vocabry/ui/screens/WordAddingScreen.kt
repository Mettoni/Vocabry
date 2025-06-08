package com.example.vocabry.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import com.example.vocabry.R
import com.example.vocabry.ui.viewModel.CategoryViewModel
import com.example.vocabry.ui.viewModel.LanguageViewModel
import com.example.vocabry.ui.viewModel.WordAddingScreenViewModel

/**
 * Obrazovka na pridávanie nových slov do databázy.
 *
 * Používateľ môže zadať nové slovo, preklad, vybrať kategóriu a jazyk. Tiež je možné odstrániť
 * existujúce slová v aktuálnej kategórii. Po pridaní sa zoznam aktualizuje.
 * Ak človek pridá slovo do kategórie ktorá neexistuje tak daná kategória sa vytvorí
 *
 * @param wordAddingScreenViewModel ViewModel pre manipuláciu so slovami.
 * @param categoryViewModel ViewModel pre prácu s kategóriami.
 * @param languageViewModel ViewModel pre výber jazyka.
 * @param navHostController Navigačný kontrolér pre návrat späť.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordAddingScreen(
    wordAddingScreenViewModel: WordAddingScreenViewModel,
    categoryViewModel: CategoryViewModel,
    languageViewModel: LanguageViewModel,
    navHostController: NavHostController
) {
    var wordInput by rememberSaveable { mutableStateOf("") }
    var translationInput by rememberSaveable { mutableStateOf("") }
    var categoryInput by rememberSaveable { mutableStateOf("") }

    val words by wordAddingScreenViewModel.wordList.collectAsState()
    val categories by categoryViewModel.categories.collectAsState()

    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()
    val allLanguages by languageViewModel.languages.collectAsState()

    LaunchedEffect(Unit) {
        languageViewModel.loadLanguages()
        if (selectedLanguage.isBlank()) {
            categoryViewModel.loadCategories()
        } else {
            categoryViewModel.loadCategoriesByLanguage(selectedLanguage)
        }

    }
    LaunchedEffect(selectedLanguage) {
        languageViewModel.loadLanguages()
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
        title = { Text("") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        ),
        navigationIcon = {
            IconButton(onClick = dropUnlessResumed{ navHostController.navigate("menu") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.spat)
                )
            }
        }
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = if(isLandscape) 60.dp else 40.dp, vertical = if (isLandscape) 10.dp else 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EditTextField(
            label = stringResource(R.string.slovo),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = wordInput,
            onValueChanged = { wordInput = it },
            modifier = Modifier
                .padding(top = if(isLandscape) 10.dp else 100.dp)
                .fillMaxWidth()
        )
        EditTextField(
            label = stringResource(R.string.preklad),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = translationInput,
            onValueChanged = { translationInput = it },
            modifier = Modifier.fillMaxWidth()
        )

        LanguageDropdownWithInput(
            languages = allLanguages,
            selectedLanguage = selectedLanguage,
            onLanguageChanged = {
                languageViewModel.setLanguage(it)
                categoryViewModel.loadCategoriesByLanguage(it)
                wordAddingScreenViewModel.loadWordsByCategory(categoryInput, it)
            }
        )

        CategoryDropdownWithInput(
            categories = categories,
            selectedCategory = categoryInput,
            onCategoryChanged = {
                categoryInput = it
                wordAddingScreenViewModel.loadWordsByCategory(it,selectedLanguage)
            }
        )

        Button(
            onClick = {
                if (wordInput.isNotBlank() && translationInput.isNotBlank() && categoryInput.isNotBlank()) {
                    wordAddingScreenViewModel.checkIfWordExists(
                        word = wordInput.trim(),
                        category = categoryInput.trim(),
                        language = selectedLanguage.trim()
                    ) { exists ->
                        if (!exists) {
                            wordAddingScreenViewModel.addWord(
                                wordInput.trim(),
                                translationInput.trim(),
                                categoryInput.trim(),
                                selectedLanguage.trim()
                            )

                            wordAddingScreenViewModel.loadWordsByCategory(categoryInput.trim(), selectedLanguage.trim())
                            categoryViewModel.loadCategoriesByLanguage(selectedLanguage)


                            wordInput = ""
                            translationInput = ""

                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp).height(60.dp)
        ) {
            Text(stringResource(R.string.potvrdit))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(stringResource(R.string.zoznam_slov_v_kategorii), style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        words.forEach { word ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${word.word} – ${word.translated}",
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    wordAddingScreenViewModel.removeWord(word.word, word.category,selectedLanguage)
                    categoryViewModel.loadCategoriesByLanguage(selectedLanguage)

                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.odstr_ni_slovo)
                    )
                }
            }
        }
    }
}

/**
 * Rozšírené textové pole s možnosťou výberu kategórie z dropDownMenu menu.
 * Kategóriu je možné zadať aj manuálne.
 *
 * @param categories Zoznam dostupných kategórií.
 * @param selectedCategory Aktuálne vybraná kategória.
 * @param onCategoryChanged Callback, ktorý sa volá pri zmene kategórie.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownWithInput(
    categories: List<String>,
    selectedCategory: String,
    onCategoryChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var localInput by remember { mutableStateOf(selectedCategory) }

    val filteredCategories = categories.filter {
        it.contains(localInput, ignoreCase = true)
    }

    val focusRequester = remember { FocusRequester() }

    Column(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = localInput,
                onValueChange = {
                    localInput = it
                    expanded = true
                    onCategoryChanged(it)
                },
                label = { Text(stringResource(R.string.kategoria)) },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filteredCategories.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            localInput = option
                            expanded = false
                            onCategoryChanged(option)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Rozšírené textové pole s možnosťou výberu jazyka z dropDownMenu menu.
 * Jazyk je možné zadať aj manuálne .
 *
 * @param languages Zoznam dostupných jazykov.
 * @param selectedLanguage Aktuálne vybraný jazyk.
 * @param onLanguageChanged Callback, ktorý sa volá pri zmene jazyka.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdownWithInput(
    languages: List<String>,
    selectedLanguage: String,
    onLanguageChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var localInput by remember { mutableStateOf(selectedLanguage) }
    var filteredLanguages by remember { mutableStateOf(languages) }

    LaunchedEffect(selectedLanguage) {
        localInput = selectedLanguage
    }

    LaunchedEffect(localInput, languages) {
        filteredLanguages = languages.filter {
            it.contains(localInput, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = localInput,
                onValueChange = {
                    localInput = it
                    expanded = true
                    onLanguageChanged(it)
                },
                label = { Text(stringResource(R.string.jazyk)) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filteredLanguages.forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language) },
                        onClick = {
                            localInput = language
                            expanded = false
                            onLanguageChanged(language)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Univerzálne vstupné pole pre text s popisným štítkom.
 *
 * @param label Text, ktorý sa zobrazí ako štítok nad poľom.
 * @param keyboardOptions Nastavenia klávesnice (napr. typ, akcia).
 * @param value Aktuálna hodnota poľa.
 * @param onValueChanged Callback, ktorý sa zavolá pri zmene hodnoty.
 * @param modifier Modifier pre úpravu vzhľadu a správania.
 */
@Composable
fun EditTextField(
    label: String,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
    )
}