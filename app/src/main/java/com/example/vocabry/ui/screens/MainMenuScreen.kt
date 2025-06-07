package com.example.vocabry.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import com.example.vocabry.R
import com.example.vocabry.ui.AppScreen
/**
 * Composable funkcia pre zobrazenie hlavného menu aplikácie.
 *
 * Obsahuje tri hlavné tlačidlá:
 * - „Štart“ – používateľa presmeruje na obrazovku výberu jazyka.
 * - „Slová“ – používateľa presmeruje na obrazovku správy slovíčok.
 * - „Koniec“ – zatvorí aplikáciu (volá `finish()` na aktivite).
 *
 * Dynamicky reaguje na orientáciu obrazovky pričom prispôsobuje rozmery a rozloženie prvkov
 * podľa toho, či je zariadenie v režime na výšku alebo na šírku.
 *
 * @param navHostController Navigačný kontrolér, ktorý zabezpečuje prechody medzi obrazovkami aplikácie.
 */
@SuppressLint("ContextCastToActivity")
@Composable
fun MainMenu(navHostController: NavController) {
    val activity = LocalContext.current as? Activity
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val SignikaNegative = FontFamily(
        Font(R.font.signika_negative)
    )

    Box(modifier = Modifier
        .background(Color(0xFF80B6F0))
        .fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if (isLandscape) 50.dp else 125.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Vocabry",fontSize = 100.sp,fontFamily = SignikaNegative,style = MaterialTheme.typography.titleLarge, color = Color.White)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = if (isLandscape) 50.dp else 40.dp,),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(if(isLandscape) 140.dp else 0.dp))
        Button(
            onClick = dropUnlessResumed{
                navHostController.navigate(AppScreen.Language.route)
            },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text(stringResource(R.string.start))
        }
        Spacer(modifier = Modifier.height(if(isLandscape) 5.dp else 10.dp))
        Button(
            onClick = dropUnlessResumed {
                navHostController.navigate(AppScreen.Word.route)
            },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text(stringResource(R.string.words))
        }
        Spacer(modifier = Modifier.height(if(isLandscape) 5.dp else 10.dp))
        Button(
            onClick = dropUnlessResumed{
                activity?.finish()
            },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text(stringResource(R.string.exit))
        }

    }
}
