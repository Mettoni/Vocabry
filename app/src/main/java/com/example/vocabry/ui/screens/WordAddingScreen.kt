package com.example.vocabry.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
    var isAddingMode by remember { mutableStateOf(true) }

    val words by viewModel.wordList.collectAsState()

    TopAppBar(
        title = { Text("")},
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

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                horizontal = 40.dp,
                vertical = if (isLandscape) 10.dp else 10.dp
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EditTextField(
            label = stringResource(R.string.zadaj_slovo),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = wordInput,
            onValueChanged = { wordInput = it },
            modifier = Modifier
                .padding(
                    start = if (isLandscape) 15.dp else 0.dp,
                    top = if (isLandscape) 10.dp else 100.dp
                )
                .fillMaxWidth()
        )
        if(!isLandscape) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 0.dp, start = 100.dp )
            ){
                Text(
                    text = if(isAddingMode) "Pridávanie slova" else "Odoberanie slova",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Switch(
                    checked = isAddingMode,
                    onCheckedChange = { isAddingMode = it }
                )
            }

            Button(
                onClick = {
                    if (wordInput.isNotBlank()) {
                        if(isAddingMode && !viewModel.wordList.value.contains(wordInput)) {
                            viewModel.addWord(wordInput.trim())
                        } else if(!isAddingMode){
                            viewModel.removeWord(wordInput.trim())
                        } else {
                            println("nic")
                        }
                        wordInput= ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(stringResource(R.string.potvrdit))
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Button(
                    onClick = {
                        if (wordInput.isNotBlank()) {
                            if (isAddingMode && !viewModel.wordList.value.contains(wordInput)) {
                                viewModel.addWord(wordInput.trim())
                            } else if (!isAddingMode) {
                                viewModel.removeWord(wordInput.trim())
                            } else {
                                println("nic")
                            }
                            wordInput = ""
                        }
                    },
                    modifier = Modifier
                        .width(450.dp)
                        .padding(bottom = 350.dp,)
                ) {
                    Text(stringResource(R.string.potvrdit))
                }

                Text(
                    text = if(isAddingMode) "Pridávanie slova" else "Odoberanie slova",
                    modifier = Modifier.padding(start = 100.dp,
                        bottom = 350.dp)
                )
                Switch(
                    checked = isAddingMode,
                    modifier = Modifier.padding(start = 8.dp, bottom = 350.dp),
                    onCheckedChange = { isAddingMode = it }
                )
            }
        }
        Text("Zoznam slov:")
        words.forEach { word ->
            Text(text = "• $word")
        }
    }

    /*
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 0.dp, start = if(isLandscape) 550.dp else 100.dp )
    ){
        Text(
            text = if(isAddingMode) "Pridávanie slova" else "Odoberanie slova",
            modifier = Modifier.padding(end = 8.dp)
        )
        Switch(
            checked = isAddingMode,
            onCheckedChange = { isAddingMode = it }
        )
    }


    Button(
        onClick = {
            if (wordInput.isNotBlank()) {
                if(isAddingMode && !viewModel.wordList.value.contains(wordInput)) {
                    viewModel.addWord(wordInput.trim())
                } else if(!isAddingMode){
                    viewModel.removeWord(wordInput.trim())
                } else {
                    println("nic")
                }
                wordInput= ""
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if(isLandscape) 0.dp else 16.dp)
    ) {
        Text(stringResource(R.string.potvrdit))
    }

     */
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