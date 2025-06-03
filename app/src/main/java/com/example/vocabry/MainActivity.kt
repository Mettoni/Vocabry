package com.example.vocabry

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.vocabry.data.WordDatabase
import com.example.vocabry.data.WordRepository
import com.example.vocabry.data.notification.NotificationScheduler
import com.example.vocabry.domain.usecase.AddWordUseCase
import com.example.vocabry.domain.usecase.GenerateButtonOptions
import com.example.vocabry.domain.usecase.GetAllCategoriesUseCase
import com.example.vocabry.domain.usecase.GetAllLanguagesUseCase
import com.example.vocabry.domain.usecase.GetListUseCase
import com.example.vocabry.domain.usecase.GetWordsByCategoryUseCase
import com.example.vocabry.domain.usecase.NotificationUseCase
import com.example.vocabry.domain.usecase.RemoveWordUseCase
import com.example.vocabry.ui.AppNavigation
import com.example.vocabry.ui.theme.VocabryTheme
import com.example.vocabry.ui.viewModel.CategoryViewModel
import com.example.vocabry.ui.viewModel.CategoryViewModelFactory
import com.example.vocabry.ui.viewModel.LanguageViewModel
import com.example.vocabry.ui.viewModel.LanguageViewModelFactory
import com.example.vocabry.ui.viewModel.MainViewModel
import com.example.vocabry.ui.viewModel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {}
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> { }
                else -> requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        val database = WordDatabase.getDatabase(applicationContext)
        val dao = database.wordDao()
        val wordFunctions = WordRepository(dao)
        val notifyUserUseCase = NotificationUseCase(NotificationScheduler())

        val viewModelFactory = MainViewModelFactory(
            AddWordUseCase(wordFunctions),
            RemoveWordUseCase(wordFunctions),
            GetListUseCase(wordFunctions),
            GenerateButtonOptions(wordFunctions),
            GetWordsByCategoryUseCase(wordFunctions),
            notifyUserUseCase
        )
        val categoryViewModelFactory = CategoryViewModelFactory(
            GetAllCategoriesUseCase(wordFunctions),
            GetWordsByCategoryUseCase(wordFunctions)
        )

        val languageViewModelFactory = LanguageViewModelFactory(
            GetAllLanguagesUseCase(wordFunctions)
        )

        enableEdgeToEdge()
        setContent {
            VocabryTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
                val categoryViewModel: CategoryViewModel = viewModel(factory = categoryViewModelFactory)
                val languageViewModel: LanguageViewModel = viewModel(factory = languageViewModelFactory)

                viewModel.scheduleNotification(applicationContext)

                AppNavigation(
                    navController = navController,
                    viewModel = viewModel,
                    categoryViewModel = categoryViewModel,
                    languageViewModel = languageViewModel,
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