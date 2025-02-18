package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.der2shka.cursovedcote.Service.setLocaleForApp
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text


/**
 *  Welcome settings page for
 *  choosing app's language.
 * **/
@SuppressLint("ResourceAsColor")
@Composable
fun ChooseLanguageFromStartAppPage(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val widthForPicture = config.screenWidthDp * 0.6f
    // val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.background_color)
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(widthForPicture.dp)
                ,
                painter = painterResource(R.drawable.background_gradient_white_app_icon_256),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                ,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScrollableAnimatedText(
                    text = stringResource(R.string.app_name),
                    textColor = colorResource(R.color.main_text_dark_gray),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    fontSize = font_size_main_text,
                    fontWeight = FontWeight.Bold,
                    lineHeight = line_height_main_text
                )

                Text(
                    modifier = Modifier,
                    text= stringResource(R.string.select_locale_from_start_page),
                    color = colorResource(R.color.secondary_text_gray),
                    maxLines = 3,
                    textAlign = TextAlign.Center,
                    fontSize = font_size_secondary_text,
                    lineHeight = line_height_secondary_text
                )
            }

            Button(
                modifier = Modifier
                    .height(
                        (config.screenHeightDp * 0.1f).dp
                    )
                    .fillMaxWidth(0.8f)
                ,
                onClick = {
                    /* TODO: Обязательно добавить выбранный
                    *   язык в SharedPreferences!
                    * */
                    setLocaleForApp(context, "en")
                    GoToWelcomePhrasesPage(navHostController)
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.6f to colorResource(R.color.primary_blue),
                                    1f to colorResource(R.color.secondary_cyan)
                                )
                            )
                        )
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    ScrollableAnimatedText(
                        text = stringResource(R.string.continue_with_english),
                        maxLines = 1,
                        textColor = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = font_size_main_text,
                        lineHeight = line_height_main_text,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            ScrollableAnimatedText(
                containterModifier = Modifier
                    .clickable {
                        /* TODO: Обязательно добавить выбранный
                        *   язык в SharedPreferences!
                        * */
                        setLocaleForApp(context, "ru")
                        GoToWelcomePhrasesPage(navHostController)
                    }
                ,
                textModifier = Modifier
                    .fillMaxWidth(0.9f)
                ,
                text = stringResource(R.string.continue_with_russian),
                maxLines = 1,
                textColor = colorResource(R.color.primary_blue),
                textAlign = TextAlign.Center,
                fontSize = font_size_secondary_text,
                lineHeight = line_height_secondary_text
            )
        }
    }
}

private fun GoToWelcomePhrasesPage(navHostController: NavHostController) {
    current_page = "choose_app_theme_from_start_page"
    navHostController.navigate(current_page) {
        popUpTo("choose_language_from_start_page") {
            inclusive = true
        }
    }
}