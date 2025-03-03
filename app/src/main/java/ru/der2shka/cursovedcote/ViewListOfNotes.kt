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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.NoteItem
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * View list of saved notes.
 * **/
@SuppressLint("UnrememberedMutableState")
@Composable
fun ViewListOfNotes(
    navHostController: NavHostController,
    database: AppDatabase
) {
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()

    var noteList = remember { mutableStateOf( listOf<Note>() ) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            noteList.value = database.noteDao().findNotes().toMutableList()
            println()
        }
    }

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
                    text = stringResource(R.string.notes),
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
                /*
                NoteItem(
                    navHostController = navHostController,
                    name = "Note 1 (really long text, yeah? YEAH? Oh noo... NOOOOO!)",
                    description = "Description of note Description of note Description of note Description of note Description of note Description of note Description of note Description of note Description of note Description of note Description of note",
                    localDate = LocalDate.of(2025, 2, 12),
                    statusIndex = 0
                )

                NoteItem(
                    navHostController = navHostController,
                    name = "Note 2 (really long text, yeah? YEAH? Oh noo... NOOOOO!)",
                    description = "Description of note 2",
                    localDate = LocalDate.of(2025, 2, 12),
                    statusIndex = 2
                )

                NoteItem(
                    navHostController = navHostController,
                    name = "Note 3",
                    description = "Description of note 3",
                    localDate = LocalDate.of(2025, 2, 15),
                    statusIndex = 1
                )

                NoteItem(
                    navHostController = navHostController,
                    name = "Перевернуть матрас",
                    description = "Перевернуть матрас у кровати, а то уже больше 3 месяцев не переворачивал",
                    localDate = LocalDate.of(2025, 2, 25),
                    statusIndex = 0
                )

                NoteItem(
                    navHostController = navHostController,
                    name = "Купить тетрадь для БЖД",
                    description = "",
                    localDate = LocalDate.of(2025, 2, 22),
                    statusIndex = 3
                )
                 */

                noteList.value.forEach {
                    NoteItem(
                        navHostController = navHostController,
                        name = it.name,
                        description =  it.description,
                        localDate = Instant
                            .ofEpochMilli(it.date)
                            .atZone( ZoneId.systemDefault() )
                            .toLocalDate(),
                        note = it,
                        statusIndex = it.status
                    )
                }
            }
        }
    }
}