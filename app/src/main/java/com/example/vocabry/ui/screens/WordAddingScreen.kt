package com.example.vocabry.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vocabry.R
import com.example.vocabry.ui.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordAddingScreen(viewModel: MainViewModel, navHostController: NavHostController) {
    var wordInput by remember { mutableStateOf("") }
    var translationInput by remember { mutableStateOf("") }
    var categoryInput by remember { mutableStateOf("") }
    var isAddingMode by remember { mutableStateOf(true) }

    val words by viewModel.wordList.collectAsState()

    TopAppBar(
        title = { Text("") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        ),
        navigationIcon = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Späť"
                )
            }
        }
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp, vertical = if (isLandscape) 10.dp else 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EditTextField(
            label = "Slovo",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = wordInput,
            onValueChanged = { wordInput = it },
            modifier = Modifier
                .padding(top = if (isLandscape) 10.dp else 100.dp)
                .fillMaxWidth()
        )
        EditTextField(
            label = "Preklad",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = translationInput,
            onValueChanged = { translationInput = it },
            modifier = Modifier.fillMaxWidth()
        )
        EditTextField(
            label = "Kategória",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = categoryInput,
            onValueChanged = { categoryInput = it },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = if (isAddingMode) "Pridávanie slova" else "Odoberanie slova",
                modifier = Modifier.padding(end = 8.dp)
            )
            Switch(
                checked = isAddingMode,
                onCheckedChange = { isAddingMode = it }
            )
        }

        Button(
            onClick = {
                if (wordInput.isNotBlank() && translationInput.isNotBlank() && categoryInput.isNotBlank()) {
                    if (isAddingMode) {
                        viewModel.addWord(wordInput.trim(), translationInput.trim(), categoryInput.trim())
                    } else {
                        viewModel.removeWord(wordInput.trim(), categoryInput.trim())
                    }
                    wordInput = ""
                    translationInput = ""
                    categoryInput = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text(stringResource(R.string.potvrdit))
        }

        Text("Zoznam slov:", modifier = Modifier.padding(top = 16.dp))
        words.forEach { word ->
            Text(text = "• ${word.word} – ${word.translated} [${word.category}]")
        }
    }
}

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
        keyboardOptions = keyboardOptions
    )
}