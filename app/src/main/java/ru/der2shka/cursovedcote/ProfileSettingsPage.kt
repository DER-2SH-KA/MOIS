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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Service.SettingsDataStore
import ru.der2shka.cursovedcote.Service.setAppTheme
import ru.der2shka.cursovedcote.Service.setLocaleForApp
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.SomeConstantValues
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text

@Composable
fun ProfileSettingsPage(
    navHostController: NavHostController,
    settingsDataStore: SettingsDataStore
) {
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()

    val oneBlockHeight = (config.screenHeightDp * 0.2).dp

    val languageList = SomeConstantValues().getLanguageList()
    val themeList = SomeConstantValues().getThemeList()

    val selectedLanguage = remember { mutableStateOf("") }
    val selectedTheme = remember { mutableStateOf("") }

    val currLang = remember { mutableStateOf("none") }
    val currAppThemeIsDark = remember { mutableStateOf( false ) }

    LaunchedEffect(key1 = Unit) {
        currLang.value = settingsDataStore.getLanguageData.first()
        currAppThemeIsDark.value = settingsDataStore.getAppThemeData.first()

        selectedLanguage.value = if (currLang.value.equals("en")) languageList.get(0) else languageList.get(1)
        selectedTheme.value = if (currAppThemeIsDark.value) themeList.get(0) else themeList.get(1)
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Header Text.
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(oneBlockHeight.value.dp)
                    .padding(10.dp)
                    .background(
                        color = colorResource(R.color.primary_blue),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                ScrollableAnimatedText(
                    text = stringResource(R.string.settings),
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = font_size_main_text,
                    lineHeight = line_height_main_text,
                    fontWeight = FontWeight.Bold,
                    textModifier = Modifier.fillMaxWidth()
                )
            }

            // Settings fields.
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
                        .padding(0.dp, 10.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                    ) {
                        ScrollableAnimatedText(
                            text = "${stringResource(R.string.language)}:",
                            textColor = colorResource(R.color.main_text_dark_gray),
                            textAlign = TextAlign.Start,
                            fontSize = font_size_middle_size_text,
                            fontWeight = FontWeight.Medium,
                            lineHeight = line_height_middle_size_text,
                            containterModifier = Modifier
                                .fillMaxWidth(0.9f)
                        )
                    }

                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ComboBoxPseudo(
                            items = languageList,
                            selectedItem = selectedLanguage,
                            onSelect = { language ->
                                if (!selectedLanguage.value.equals(language)) {
                                    selectedLanguage.value = language

                                    coroutineScope.launch {
                                        var newLang =
                                            if (language.equals(languageList.get(0))) "en" else "ru"

                                        settingsDataStore.saveLanguageData(newLang)

                                        newLang = settingsDataStore.getLanguageData.first()
                                        setLocaleForApp(context, newLang)

                                        val activity = context as Activity?
                                        activity?.recreate()
                                    }
                                }
                            }
                        )
                    }
                }

                // Choose theme.
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                    ) {
                        ScrollableAnimatedText(
                            text = "${stringResource(R.string.theme)}:",
                            textColor = colorResource(R.color.main_text_dark_gray),
                            textAlign = TextAlign.Start,
                            fontSize = font_size_middle_size_text,
                            fontWeight = FontWeight.Medium,
                            lineHeight = line_height_middle_size_text,
                            containterModifier = Modifier
                                .fillMaxWidth(0.9f)
                        )
                    }

                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ComboBoxPseudo(
                            items = themeList,
                            selectedItem = selectedTheme,
                            onSelect = { theme ->
                                if (!selectedTheme.value.equals(theme)) {
                                    selectedTheme.value = theme

                                    coroutineScope.launch {
                                        var isDarkTheme =
                                            if (theme.equals(themeList.get(0))) true else false

                                        settingsDataStore.saveAppThemeData(isDarkTheme)

                                        isDarkTheme = settingsDataStore.getAppThemeData.first()
                                        setAppTheme(context, isDarkTheme)

                                        val activity = context as Activity?
                                        activity?.recreate()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}