package com.example.vocabry

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.vocabry.ui.AppNavigation
import com.example.vocabry.ui.theme.VocabryTheme
import com.example.vocabry.ui.viewModel.CategoryViewModel
import com.example.vocabry.ui.viewModel.CategoryViewModelFactory
import com.example.vocabry.ui.viewModel.LanguageViewModel
import com.example.vocabry.ui.viewModel.LanguageViewModelFactory
import com.example.vocabry.ui.viewModel.QuestionScreenViewModel
import com.example.vocabry.ui.viewModel.QuestionScreenViewModelFactory
import com.example.vocabry.ui.viewModel.WordAddingScreenViewModel
import com.example.vocabry.ui.viewModel.WordAddingScreenViewModelFactory

/**
 * Hlavná aktivita aplikácie, ktorá inicializuje prostredie,
 * zabezpečuje povolenia a nastavuje všetky ViewModely a navigáciu.
 */
class MainActivity : ComponentActivity() {
    /**
     * Spúšťač pre požiadanie o notifikačné povolenie (pre Android 13+).
     */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted -> }
    /**
     * Metóda volaná pri vytváraní aktivity.
     * Inicializuje ViewModelFactories a nastavuje obsah UI pomocou Compose.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        val app = application as VocabryApplication

        val questionVMFactory = QuestionScreenViewModelFactory(
            app.addWordUseCase,
            app.getWordsByCategoryUseCase,
            app.getListUseCase,
            app.getButtonOptionsUseCase,
            app.notificationUseCase
        )

        val categoryVMFactory = CategoryViewModelFactory(
            app.getAllCategoriesUseCase,
            app.getCategoriesByLanguageUseCase,
            app.getWordsByCategoryUseCase
        )

        val languageVMFactory = LanguageViewModelFactory(
            app.getAllLanguagesUseCase
        )

        val wordAddingVMFactory = WordAddingScreenViewModelFactory(
            app.addWordUseCase,
            app.removeWordUseCase,
            app.getWordsByCategoryUseCase,
            app.getListUseCase
        )
        enableEdgeToEdge()
        setContent {
            VocabryTheme {
                val navController = rememberNavController()

                val questionViewModel: QuestionScreenViewModel = viewModel(factory = questionVMFactory)
                val categoryViewModel: CategoryViewModel = viewModel(factory = categoryVMFactory)
                val languageViewModel: LanguageViewModel = viewModel(factory = languageVMFactory)
                val wordAddingViewModel: WordAddingScreenViewModel = viewModel(factory = wordAddingVMFactory)

                questionViewModel.scheduleNotification(applicationContext)

                AppNavigation(
                    navController = navController,
                    questionScreenViewModel = questionViewModel,
                    categoryViewModel = categoryViewModel,
                    languageViewModel = languageViewModel,
                    wordAddingScreenViewModel = wordAddingViewModel,
                    modifier = Modifier
                )
            }
        }
    }
}
