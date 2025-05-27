package com.example.vocabry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.vocabry.data.RoomUseWordFunctions
import com.example.vocabry.data.WordDatabase
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GenerateButtonOptions
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase
import com.example.vocabry.ui.AppNavigation
import com.example.vocabry.ui.theme.VocabryTheme
import com.example.vocabry.ui.viewModel.MainViewModel
import com.example.vocabry.ui.viewModel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = WordDatabase.getDatabase(applicationContext)
        val dao = database.wordDao()
        val wordFunctions = RoomUseWordFunctions(dao)

        val viewModelFactory = MainViewModelFactory(
            AddWordUseCase(wordFunctions),
            RemoveWordUseCase(wordFunctions),
            GetListUseCase(wordFunctions),
            GenerateButtonOptions(wordFunctions)
        )

        enableEdgeToEdge()
        setContent {
            VocabryTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)

                AppNavigation(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VocabryTheme {
        Greeting("Android")
    }
}