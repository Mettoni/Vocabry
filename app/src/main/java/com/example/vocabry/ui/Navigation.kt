package com.example.vocabry.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vocabry.ui.screens.CategorySelect
import com.example.vocabry.ui.screens.LanguageSelectScreen
import com.example.vocabry.ui.screens.MainMenu
import com.example.vocabry.ui.screens.QuestionScreen
import com.example.vocabry.ui.screens.WordAddingScreen
import com.example.vocabry.ui.viewModel.CategoryViewModel
import com.example.vocabry.ui.viewModel.LanguageViewModel
import com.example.vocabry.ui.viewModel.MainViewModel

enum class AppScreen(val route: String) {
    Menu("menu"),
    Start("start"),
    Category("category"),
    Language("language"),
    Word("word")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    categoryViewModel: CategoryViewModel,
    languageViewModel: LanguageViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Menu.route,
        modifier = modifier
    ) {
        composable(AppScreen.Menu.route) {
            MainMenu(navController)
        }
        composable(AppScreen.Start.route) {
            QuestionScreen(viewModel, categoryViewModel,languageViewModel,navController)
        }
        composable(AppScreen.Category.route) {
            CategorySelect(categoryViewModel,languageViewModel,navController)
        }
        composable(AppScreen.Language.route) {
            LanguageSelectScreen(languageViewModel,navController)
        }
        composable(AppScreen.Word.route) {
            WordAddingScreen(viewModel,categoryViewModel,languageViewModel,navController)
        }
    }

}