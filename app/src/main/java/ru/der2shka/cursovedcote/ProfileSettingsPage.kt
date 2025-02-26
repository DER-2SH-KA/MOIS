package ru.der2shka.cursovedcote

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Service.SettingsDataStore
import ru.der2shka.cursovedcote.Service.setAppTheme
import ru.der2shka.cursovedcote.Service.setLocaleForApp

@Composable
fun ProfileSettingsPage(
    navHostController: NavHostController,
    settingsDataStore: SettingsDataStore
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val langChecked = remember { mutableStateOf(false) }
    val appThemeIsDarkChecked = remember { mutableStateOf(false) }

    val currLang = remember { mutableStateOf("none") }
    val currAppThemeIsDark = remember { mutableStateOf( false ) }

    LaunchedEffect(key1 = Unit) {
        currLang.value = settingsDataStore.getLanguageData.first()
        currAppThemeIsDark.value = settingsDataStore.getAppThemeData.first()

        langChecked.value = if (currLang.value.equals("ru")) true else false
        appThemeIsDarkChecked.value = currAppThemeIsDark.value
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.background_color)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize(0.9f)
        ) {
            // Choose language.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "English / Russian",
                    color = colorResource(R.color.main_text_dark_gray)
                )
                Switch(
                    checked = langChecked.value,
                    onCheckedChange = {
                        langChecked.value = it

                        coroutineScope.launch {
                            var lang = if (langChecked.value) "ru" else "en"

                            settingsDataStore.saveLanguageData(lang)

                            lang = settingsDataStore.getLanguageData.first()
                            setLocaleForApp(context, lang)

                            val activity = context as Activity?
                            activity?.recreate()
                        }
                    }
                )
            }

            // Choose theme.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Dark Theme?",
                    color = colorResource(R.color.main_text_dark_gray)
                )
                Switch(
                    checked = appThemeIsDarkChecked.value,
                    onCheckedChange = {
                        appThemeIsDarkChecked.value = it

                        coroutineScope.launch {
                            var isDarkTheme = appThemeIsDarkChecked.value

                            settingsDataStore.saveAppThemeData(isDarkTheme)

                            isDarkTheme = settingsDataStore.getAppThemeData.first()
                            setAppTheme(context, isDarkTheme)

                            val activity = context as Activity?
                            activity?.recreate()
                        }
                    }
                )
            }
        }
    }
}