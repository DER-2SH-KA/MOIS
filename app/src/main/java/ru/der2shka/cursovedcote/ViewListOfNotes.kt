package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Service.ClearTextField
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.GradeTypeItem
import ru.der2shka.cursovedcote.ui.NoteItem
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.TextFieldCustom
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text
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

    val filterTextField = remember { mutableStateOf(TextFieldValue("")) }
    var noteList = remember { mutableStateOf( listOf<Note>() ) }
    val itemsNoteFiltered = remember(noteList.value, filterTextField.value) {
        if (filterTextField.value.text == "" || filterTextField.value.text.isEmpty()) {
            mutableStateOf(noteList.value)
        }
        else {
            mutableStateOf(
                noteList.value.filter { hw ->
                    hw.name.contains( filterTextField.value.text.trim(), true ) ||
                            hw.description.contains( filterTextField.value.text.trim(), true )
                }.toList()
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            noteList.value = database.noteDao().findNotesWithOrdering().toMutableList()
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
                    // .verticalScroll( contentVScroll )
            ) {
                // Search box.
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp)
                ) {
                    TextFieldCustom(
                        value = filterTextField.value.text,
                        onValueChange = {
                            filterTextField.value = TextFieldValue( it )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(5.dp),
                        placeholder = {
                            Text(
                                text = "${stringResource(R.string.search)}...",
                                color = colorResource(R.color.secondary_text_gray),
                                textAlign = TextAlign.Start,
                                fontSize = font_size_secondary_text,
                                fontStyle = FontStyle.Italic,
                                lineHeight = line_height_secondary_text
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                                tint = colorResource(R.color.primary_blue),
                                modifier = Modifier
                                    .clickable {
                                        ClearTextField(filterTextField)
                                    }
                            )
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = colorResource(R.color.primary_blue),
                                shape = RoundedCornerShape(5.dp)
                            )
                    )
                }

                if (itemsNoteFiltered.value.isNotEmpty()) {
                    LazyColumn() {
                        items( itemsNoteFiltered.value, key = { item -> item.id } ) { note ->
                            NoteItem(navHostController, note)
                        }
                    }
                }
                else {
                    Text(
                        text = "\\_( -_ -)_/",
                        color = colorResource(R.color.main_text_dark_gray),
                        textAlign = TextAlign.Center,
                        fontSize = font_size_secondary_text,
                        fontStyle = FontStyle.Italic,
                        lineHeight = line_height_middle_size_text,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

        }
    }
}