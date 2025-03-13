package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import ru.der2shka.cursovedcote.db.entity.Homework
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.HomeworkItem
import ru.der2shka.cursovedcote.ui.NoteItem
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.TextFieldCustom
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate

/**
 * View list of saved notes.
 * **/
@SuppressLint("UnrememberedMutableState")
@Composable
fun ViewListOfHomeworks(
    navHostController: NavHostController,
    database: AppDatabase
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val config = LocalConfiguration.current

    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val contentVScroll = rememberScrollState(0)

    val filterTextField = remember { mutableStateOf(TextFieldValue("")) }
    var itemsHomework = remember { mutableStateOf(listOf<Homework>()) }
    val itemsHomeworkFiltered = remember(itemsHomework.value, filterTextField.value) {
        if (filterTextField.value.text == "" || filterTextField.value.text.isEmpty()) {
            mutableStateOf(itemsHomework.value)
        }
        else {
            mutableStateOf(
                itemsHomework.value.filter { hw ->
                    hw.name.contains( filterTextField.value.text.trim(), true ) ||
                        hw.description.contains( filterTextField.value.text.trim(), true )
                }.toList()
            )
        }
    }
    val itemsSubjects = remember { mutableStateOf( listOf<StudySubject> () ) }
    val itemsSubjectMap = remember { mutableStateOf( mutableMapOf<Long, String>() ) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            var studySubjectsDb = database.studySubjectDao().findStudySubjects()
            var homeworksDb = database.homeworkDao().findHomeworksWithOrderingByDateOfWriteDesc()

            if (homeworksDb.isNotEmpty()) {
                itemsHomework.value = homeworksDb

                if (studySubjectsDb.isNotEmpty()) {
                    itemsSubjects.value = studySubjectsDb
                    itemsSubjects.value.forEach { item ->
                        itemsSubjectMap.value.put(item.id, item.name)
                    }
                }
            }
        }
    }

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

                if (itemsHomeworkFiltered.value.isNotEmpty() && itemsSubjects.value.isNotEmpty()) {
                    LazyColumn() {
                        items( itemsHomeworkFiltered.value, key = { item -> item.id } ) { homework ->
                            HomeworkItem(
                                navHostController,
                                homework,
                                itemsSubjectMap.value.get(homework.studySubjectId).toString(),
                                database
                            )
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

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(10.dp, 10.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // Button to Add.
            Button(
                onClick = {
                    current_page = "add_new_homework"
                    navHostController.navigate(current_page)
                },

                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        (oneBlockHeight * 0.5f)
                    )
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
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    ScrollableAnimatedText(
                        text = stringResource(R.string.add_homework),
                        textColor = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = font_size_main_text,
                        fontWeight = FontWeight.Bold,
                        lineHeight = line_height_main_text,
                        containterModifier = Modifier
                            .fillMaxWidth(0.9f),
                        textModifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}