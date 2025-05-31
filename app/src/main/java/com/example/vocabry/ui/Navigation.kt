package com.example.vocabry.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vocabry.ui.screens.MainMenu
import com.example.vocabry.ui.viewModel.MainViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vocabry.ui.screens.CategorySelect
import com.example.vocabry.ui.screens.QuestionScreen
import com.example.vocabry.ui.screens.WordAddingScreen
import com.example.vocabry.ui.viewModel.CategoryViewModel

enum class AppScreen(val route: String) {
    Menu("menu"),
    Start("start"),
    Category("category"),
    Word("word")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    categoryViewModel: CategoryViewModel,
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
            QuestionScreen(viewModel, categoryViewModel,navController)
        }
        composable(AppScreen.Category.route) {
            CategorySelect(categoryViewModel,navController)
        }
        composable(AppScreen.Word.route) {
            WordAddingScreen(viewModel,navController)
        }
    }

}