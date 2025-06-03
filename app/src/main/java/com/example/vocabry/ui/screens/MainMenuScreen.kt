package com.example.vocabry.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vocabry.ui.AppScreen

@SuppressLint("ContextCastToActivity")
@Composable
fun MainMenu(navController: NavController) {
    val activity = LocalContext.current as? Activity
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                navController.navigate(AppScreen.Language.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start")
        }
        Button(
            onClick = {
                navController.navigate(AppScreen.Word.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Words")
        }
        Button(
            onClick = {
                activity?.finish()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Exit")
        }

    }
}
