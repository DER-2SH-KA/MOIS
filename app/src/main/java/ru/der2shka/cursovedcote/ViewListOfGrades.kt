package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.Homework
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.GradeChart
import ru.der2shka.cursovedcote.ui.GradeItem
import ru.der2shka.cursovedcote.ui.HomeworkItem
import ru.der2shka.cursovedcote.ui.NoteItem
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
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
fun ViewListOfGrades(
    navHostController: NavHostController,
    database: AppDatabase
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val config = LocalConfiguration.current

    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val contentVScroll = rememberScrollState(0)
    val isExpandChart = remember { mutableStateOf(false) }

    val itemsGrades = remember { mutableStateOf( listOf<Grade> () ) }
    val itemsSubjects = remember { mutableStateOf( listOf<StudySubject>() ) }
    val itemsSubjectMap = remember { mutableStateOf( mutableMapOf<Long, String>() ) }
    val itemsGradeTypes = remember { mutableStateOf( listOf<GradeType>() ) }
    val itemsGradeTypesMap = remember { mutableStateOf( mutableMapOf<Long, String>() ) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            var studySubjectsDb = database.studySubjectDao().findStudySubjects()
            var gradeTypesDb = database.gradeTypeDao().findGradeTypes()
            var gradesDb = database.gradeDao().findGradesWithOrderingByDate()

            if (gradesDb.isNotEmpty()) {
                itemsGrades.value = gradesDb

                if (studySubjectsDb.isNotEmpty()) {
                    itemsSubjects.value = studySubjectsDb
                    itemsSubjects.value.forEach { item ->
                        itemsSubjectMap.value.put(item.id, item.name)
                    }
                }

                if (gradeTypesDb.isNotEmpty()) {
                    itemsGradeTypes.value = gradeTypesDb
                    itemsGradeTypes.value.forEach { item ->
                        itemsGradeTypesMap.value.put(item.id, item.name)
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
                    text = stringResource(R.string.grades),
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
                if (itemsGrades.value.isNotEmpty() && itemsSubjects.value.isNotEmpty() && itemsGradeTypes.value.isNotEmpty()) {
                    // Chart.
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(0.dp, 10.dp)
                            .fillMaxWidth()
                            .clickable {
                                isExpandChart.value = !isExpandChart.value
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = colorResource(R.color.primary_blue),
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth(0.5f)
                            ) {
                                ScrollableAnimatedText(
                                    text = stringResource(R.string.chart),
                                    textColor = Color.White,
                                    textAlign = TextAlign.Start,
                                    fontSize = font_size_middle_size_text,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = line_height_middle_size_text,
                                    containterModifier = Modifier
                                        .fillMaxWidth(),
                                    textModifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(10.dp)
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        isExpandChart.value = !isExpandChart.value
                                    }
                            )
                        }

                        if (isExpandChart.value) {
                            if (itemsGrades.value.size > 1) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    GradeChart(
                                        gradeData = itemsGrades.value,
                                        gradeTypes = itemsGradeTypes.value,
                                        interval = 0,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(
                                                (config.screenHeightDp * 0.4f).dp
                                            )
                                    )
                                }
                            } else {
                                ScrollableAnimatedText(
                                    text = stringResource(R.string.not_enough_data),
                                    textColor = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = font_size_middle_size_text,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = line_height_middle_size_text,
                                    containterModifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .height(
                                            (config.screenHeightDp * 0.4f).dp
                                        )
                                )
                            }
                        }
                    }

                    LazyColumn() {
                        items( itemsGrades.value, key = { item -> item.id } ) { grade ->
                            GradeItem(
                                navHostController,
                                grade,
                                itemsSubjectMap.value.get(grade.subjectStudyId).toString(),
                                itemsGradeTypesMap.value.get( grade.gradeTypeId ).toString(),
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
    }
}