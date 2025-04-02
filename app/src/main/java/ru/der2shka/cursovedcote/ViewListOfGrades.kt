package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Models.FilterFieldsValues
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.Homework
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.GradeChart
import ru.der2shka.cursovedcote.ui.GradeItem
import ru.der2shka.cursovedcote.ui.HomeworkItem
import ru.der2shka.cursovedcote.ui.NoteItem
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.SomeConstantValues
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate
import java.util.Optional

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

    val filterFieldsValues = FilterFieldsValues.getInstance()

    val itemsGrades = remember { mutableStateOf( listOf<Grade> () ) }
    val itemsSubjects = remember { mutableStateOf( listOf<StudySubject>() ) }
    val itemsSubjectMap = remember { mutableStateOf( mutableMapOf<Long, String>() ) }
    val itemsGradeTypes = remember { mutableStateOf( listOf<GradeType>() ) }
    val itemsGradeTypesMap = remember { mutableStateOf( mutableMapOf<Long, String>() ) }

    // Chart filter fields.
    val selectedStudySubject = remember {
        mutableStateOf<StudySubject>(
            filterFieldsValues.studySubjectChartParam
        )
    }
    val interavList = SomeConstantValues().getIntervalList()
    val selectedInterval = remember { mutableStateOf(interavList.get( filterFieldsValues.intervalChartParam )) }
    val selectedIntervalId = remember(selectedInterval.value) { mutableStateOf(interavList.indexOf( selectedInterval.value )) }
    val selectedLocalDateFrom = remember {
        mutableStateOf( filterFieldsValues.fromDateChartParam )
    }
    val selectedLocalDateTo = remember {
        mutableStateOf( filterFieldsValues.toDateChartParam )
    }


    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            var studySubjectsDb = database.studySubjectDao().findStudySubjectsWithOrdering()
            var gradeTypesDb = database.gradeTypeDao().findGradeTypesWithOrdering()
            var gradesDb = database.gradeDao().findGradesWithOrderingByDate()

            if (gradesDb.isNotEmpty()) {
                itemsGrades.value = gradesDb

                if (studySubjectsDb.isNotEmpty()) {
                    itemsSubjects.value = studySubjectsDb
                    itemsSubjects.value.forEach { item ->
                        itemsSubjectMap.value.put(item.id, item.name)
                    }

                    if (selectedStudySubject.value.id == -1L) {
                        filterFieldsValues.setStudySubjectChartParam( Optional.ofNullable(itemsSubjects.value[0]) )
                    }
                    selectedStudySubject.value = filterFieldsValues.studySubjectChartParam
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
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(0.dp, 10.dp)
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight()
            ) {
                    if (itemsGrades.value.isNotEmpty() && itemsSubjects.value.isNotEmpty() && itemsGradeTypes.value.isNotEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.Center,
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
                                    Column(
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .verticalScroll(contentVScroll)
                                    ) {
                                        // Chart filters.
                                        Column() {
                                            // Choice of study subject.
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.4f)
                                                ) {
                                                    ScrollableAnimatedText(
                                                        text = "${stringResource(R.string.subject)}:",
                                                        textColor = colorResource(R.color.main_text_dark_gray),
                                                        textAlign = TextAlign.Start,
                                                        maxLines = 1,
                                                        fontSize = font_size_secondary_text,
                                                        lineHeight = line_height_secondary_text,
                                                    )
                                                }

                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    ComboBoxPseudo(
                                                        items = itemsSubjects.value,
                                                        selectedItem = selectedStudySubject,
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .fillMaxWidth(),
                                                        onSelect = { value ->
                                                            filterFieldsValues.setStudySubjectChartParam( Optional.ofNullable( value ) )
                                                            selectedStudySubject.value = filterFieldsValues.studySubjectChartParam
                                                        }
                                                    )
                                                }
                                            }

                                            // Choice of interval.
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.4f)
                                                ) {
                                                    ScrollableAnimatedText(
                                                        text = "${stringResource(R.string.interval)}:",
                                                        textColor = colorResource(R.color.main_text_dark_gray),
                                                        textAlign = TextAlign.Start,
                                                        maxLines = 1,
                                                        fontSize = font_size_secondary_text,
                                                        lineHeight = line_height_secondary_text,
                                                    )
                                                }

                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    ComboBoxPseudo(
                                                        items = interavList,
                                                        selectedItem = selectedInterval,
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .fillMaxWidth(),
                                                        onSelect = { value ->
                                                            filterFieldsValues.setIntervalChartParam( Optional.ofNullable( interavList.indexOf(  value) ) )
                                                            selectedInterval.value = interavList[ filterFieldsValues.intervalChartParam ]
                                                        }
                                                    )
                                                }
                                            }

                                            // Date from.
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.4f)
                                                ) {
                                                    ScrollableAnimatedText(
                                                        text = "${stringResource(R.string.from)}:",
                                                        textColor = colorResource(R.color.main_text_dark_gray),
                                                        textAlign = TextAlign.Start,
                                                        maxLines = 1,
                                                        fontSize = font_size_secondary_text,
                                                        lineHeight = line_height_secondary_text,
                                                    )
                                                }

                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    DatePickerBox(
                                                        selectedLocalDate = selectedLocalDateFrom,
                                                        startValueOfDatePicker = selectedLocalDateFrom.value,
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .fillMaxWidth()
                                                            .border(
                                                                width = 2.dp,
                                                                color = colorResource(R.color.primary_blue),
                                                                shape = RoundedCornerShape(5.dp)
                                                            ),
                                                        onSelect = { localDate ->
                                                            filterFieldsValues.setFromDateChartParam( Optional.ofNullable( localDate ) )
                                                            selectedLocalDateFrom.value =
                                                                filterFieldsValues.fromDateChartParam
                                                        }
                                                    )
                                                }
                                            }

                                            // Date to.
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.4f)
                                                ) {
                                                    ScrollableAnimatedText(
                                                        text = "${stringResource(R.string.to)}:",
                                                        textColor = colorResource(R.color.main_text_dark_gray),
                                                        textAlign = TextAlign.Start,
                                                        maxLines = 1,
                                                        fontSize = font_size_secondary_text,
                                                        lineHeight = line_height_secondary_text,
                                                    )
                                                }

                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    DatePickerBox(
                                                        selectedLocalDate = selectedLocalDateTo,
                                                        startValueOfDatePicker = selectedLocalDateTo.value,
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .fillMaxWidth()
                                                            .border(
                                                                width = 2.dp,
                                                                color = colorResource(R.color.primary_blue),
                                                                shape = RoundedCornerShape(5.dp)
                                                            ),
                                                        onSelect = { localDate ->
                                                            filterFieldsValues.setToDateChartParam( Optional.ofNullable( localDate ) )
                                                            selectedLocalDateTo.value =
                                                                filterFieldsValues.toDateChartParam
                                                        }
                                                    )
                                                }
                                            }
                                        }

                                        /*
                                    Log.d(
                                        "StateUpdate",
                                        "selectedSubject: ${selectedStudySubject.value.name}"
                                    )
                                    Log.d(
                                        "StateUpdate",
                                        "selectedInterval: ${selectedInterval.value}"
                                    )
                                    Log.d(
                                        "StateUpdate",
                                        "selectedLocalDateFrom: ${selectedLocalDateFrom.value}"
                                    )
                                    Log.d(
                                        "StateUpdate",
                                        "selectedLocalDateTo: ${selectedLocalDateTo.value}"
                                    )*/

                                        GradeChart(
                                            gradeData = itemsGrades.value,
                                            gradeTypes = itemsGradeTypes.value,
                                            studySubject = mutableStateOf(
                                                Optional.ofNullable(
                                                    selectedStudySubject.value
                                                )
                                            ),
                                            interval = selectedIntervalId,
                                            from = selectedLocalDateFrom,
                                            to = selectedLocalDateTo,
                                            modifier = Modifier
                                                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                                .fillMaxWidth()
                                                .height(
                                                    (config.screenHeightDp * 0.3f).dp
                                                )
                                        )

                                    }
                                } else {
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(
                                                    (config.screenHeightDp * 0.2f).dp
                                                )
                                                .background(
                                                    color = colorResource(R.color.primary_blue),
                                                    shape = RoundedCornerShape(
                                                        0.dp,
                                                        0.dp,
                                                        10.dp,
                                                        10.dp
                                                    )
                                                )
                                        ) {
                                            ScrollableAnimatedText(
                                                text = stringResource(R.string.not_enough_data),
                                                textColor = Color.White,
                                                textAlign = TextAlign.Center,
                                                fontSize = font_size_middle_size_text,
                                                fontWeight = FontWeight.Medium,
                                                lineHeight = line_height_middle_size_text,
                                                containterModifier = Modifier
                                                    .fillMaxWidth(0.9f)
                                            )
                                        }
                                }
                            }
                        }

                        LazyColumn() {
                            items(itemsGrades.value, key = { item -> item.id }) { grade ->
                                GradeItem(
                                    navHostController,
                                    grade,
                                    itemsSubjectMap.value.get(grade.subjectStudyId).toString(),
                                    itemsGradeTypesMap.value.get(grade.gradeTypeId).toString(),
                                    database
                                )
                            }
                        }
                    } else {
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

        if (!isExpandChart.value) {
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
                        curr_page = 0

                        current_page = "add_new_grade"
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
                            text = stringResource(R.string.add),
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
}