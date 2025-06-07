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
import com.example.vocabry.ui.viewModel.QuestionScreenViewModel
import com.example.vocabry.ui.viewModel.WordAddingScreenViewModel
/**
 * Enum trieda reprezentujúca všetky možné obrazovky (routy) v aplikácii.
 *
 * @property route Reťazec určujúci cestu/navigačný identifikátor pre danú obrazovku.
 */
enum class AppScreen(val route: String) {
    Menu("menu"),
    Start("start"),
    Category("category"),
    Language("language"),
    Word("word")
}

/**
 * Funkcia definujúca navigačný tok aplikácie pomocou Jetpack Compose Navigation.
 *
 * Vytvára NavHost a zodpovedajúce composable obrazovky pre každú routu.
 *
 * @param navController Navigačný kontrolér používaný na riadenie prechodov medzi obrazovkami.
 * @param questionScreenViewModel ViewModel pre obrazovku s otázkami.
 * @param categoryViewModel ViewModel pre prácu s kategóriami.
 * @param languageViewModel ViewModel pre výber jazyka.
 * @param wordAddingScreenViewModel ViewModel pre správu slovíčok (pridávanie/odstraňovanie).
 * @param modifier Modifier použitý na prispôsobenie NavHost komponentu (voliteľný).
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    questionScreenViewModel: QuestionScreenViewModel,
    categoryViewModel: CategoryViewModel,
    languageViewModel: LanguageViewModel,
    wordAddingScreenViewModel: WordAddingScreenViewModel,
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
            QuestionScreen(questionScreenViewModel, categoryViewModel,languageViewModel,navController)
        }
        composable(AppScreen.Category.route) {
            CategorySelect(categoryViewModel,languageViewModel,navController)
        }
        composable(AppScreen.Language.route) {
            LanguageSelectScreen(languageViewModel,navController)
        }
        composable(AppScreen.Word.route) {
            WordAddingScreen(wordAddingScreenViewModel,categoryViewModel,languageViewModel,navController)
        }
    }

}