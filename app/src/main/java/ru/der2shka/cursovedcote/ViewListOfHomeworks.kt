package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.der2shka.cursovedcote.ui.HomeworkItem
import ru.der2shka.cursovedcote.ui.NoteItem
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate

/**
 * View list of saved notes.
 * **/
@SuppressLint("UnrememberedMutableState")
@Composable
fun ViewListOfHomeworks(navHostController: NavHostController) {
    val context = LocalContext.current
    val config = LocalConfiguration.current

    val oneBlockHeight = (config.screenHeightDp * 0.2).dp

    val contentVScroll = rememberScrollState(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.background_color)
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        // Main Content.
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height((oneBlockHeight.value * 0.8).dp)
                    .background(
                        color = colorResource(R.color.primary_blue),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                // Header Text.
                ScrollableAnimatedText(
                    text = stringResource(R.string.homeworks),
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = font_size_main_text,
                    lineHeight = line_height_main_text,
                    fontWeight = FontWeight.Bold,
                    textModifier = Modifier
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight()
                    .verticalScroll( contentVScroll )
            ) {
                HomeworkItem(
                    navHostController = navHostController,
                    name = "Сделать ПР9",
                    description = "Функции и роли",
                    studySubjectId_string = "ТРиЗБД",
                    localDateBegin = LocalDate.of(2025, 2, 12),
                    localDateEnd = LocalDate.of(2025, 2, 24),
                    statusIndex = 4
                )

                HomeworkItem(
                    navHostController = navHostController,
                    name = "Сделать ЛР3",
                    description = "Кореляционно-регрессионный анализ. Обязательно в Word и таблицы Excel!",
                    studySubjectId_string = "Математическое моделирование",
                    localDateBegin = LocalDate.of(2025, 2, 17),
                    localDateEnd = LocalDate.of(2025, 2, 28),
                    statusIndex = 0
                )

                HomeworkItem(
                    navHostController = navHostController,
                    name = "Сделать ПР 13",
                    description = "Создание PDF документов из UI.",
                    studySubjectId_string = "РМП",
                    localDateBegin = LocalDate.of(2025, 2, 17),
                    localDateEnd = LocalDate.of(2025, 2, 22),
                    statusIndex = 2
                )
            }
        }
    }
}