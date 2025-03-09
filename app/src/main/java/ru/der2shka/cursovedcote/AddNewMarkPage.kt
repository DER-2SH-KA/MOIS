package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Models.AddNewMarkHelper
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.Homework
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Optional

// TODO: Подобрать цвета и оформить!
/**
 * Page for adding new mark in system.
 * **/
@RequiresApi(35)
@SuppressLint("ResourceAsColor", "UnrememberedMutableState")
@Composable
fun AddNewMarkPage(
    navHostController: NavHostController,
    database: AppDatabase
) {
    val config = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()

    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val verticalMainScroll = rememberScrollState(0)

    val addNewMarkHelper: AddNewMarkHelper = AddNewMarkHelper.getInstance()

    val markValueList = addNewMarkHelper.markValueList
    val markTypeList = remember { mutableStateOf(addNewMarkHelper.markTypeList) }
    val subjectValueList = remember { mutableStateOf(addNewMarkHelper.studySubjectList) }

    val selectedMarkValue = remember {
        mutableStateOf( markValueList.get(0) )
    }
    val selectedMarkType = remember {
        mutableStateOf( addNewMarkHelper.currentMarkType )
    }
    val selectedSubjectValue = remember {
        mutableStateOf( addNewMarkHelper.currentStudySubject )
    }
    val selectedLocalDate = remember {
        mutableStateOf( LocalDate.now() )
    }

    // Transaction status
    val transactionStatusString = remember { mutableStateOf("") }

    val succText = stringResource(R.string.successfully)
    val errText = stringResource(R.string.fail)
    var succColor = colorResource (R.color.successful_green)
    var errColor = colorResource (R.color.error_red)

    // Transaction status text.
    val transactionText = when( transactionStatusString.value ) {
        "s" -> succText
        "f" -> errText
        else -> transactionStatusString.value
    }

    // Transaction status color.
    val transactionColor = when( transactionStatusString.value ) {
        "s" -> succColor
        "f" -> errColor
        else -> Color.Black
    }

    var isDateValid = selectedLocalDate.value
        .atStartOfDay()
        .atZone( ZoneId.systemDefault() )
        .toInstant()
        .toEpochMilli() >= 0L

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val gradeTypeDbList = database.gradeTypeDao().findGradeTypesWithOrdering()
            val studySubjectDbList = database.studySubjectDao().findStudySubjectsWithOrdering()

            if (gradeTypeDbList.isNotEmpty() ) {
                addNewMarkHelper.setMarkTypeList(
                    Optional.ofNullable( gradeTypeDbList )
                )

                markTypeList.value = addNewMarkHelper.markTypeList
            }

            selectedMarkType.value = addNewMarkHelper.currentMarkType

            if (studySubjectDbList.isNotEmpty() ) {
                addNewMarkHelper.setStudySubjectList(
                    Optional.ofNullable( studySubjectDbList )
                )

                subjectValueList.value = addNewMarkHelper.studySubjectList
            }

            selectedSubjectValue.value = addNewMarkHelper.currentStudySubject
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f)
            ,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Text.
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(oneBlockHeight.value.dp)
                    .padding(10.dp)
                    .background(
                        color = colorResource(R.color.primary_blue),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                ScrollableAnimatedText(
                    text = stringResource(R.string.add_grade),
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = font_size_main_text,
                    lineHeight = line_height_main_text,
                    fontWeight = FontWeight.Bold,
                    textModifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .verticalScroll( verticalMainScroll )
            ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Choice of mark value.
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Text(text = "Markuo Value")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                    ) {
                        ScrollableAnimatedText(
                            text = "${stringResource(R.string.grade)}:",
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
                            items = markValueList,
                            selectedItem = selectedMarkValue,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            onSelect = { value ->
                                addNewMarkHelper.setCurrentMarkValue(Optional.ofNullable(value))
                                selectedMarkValue.value = addNewMarkHelper.currentMarkValue
                            }
                        )
                    }
                }

                // Choice of mark type.
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Text(text = "Markuo Type")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                    ) {
                        ScrollableAnimatedText(
                            text = "${stringResource(R.string.grade_type)}:",
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
                            items = markTypeList.value,
                            selectedItem = selectedMarkType,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                            ,
                            onSelect = { value ->
                                selectedMarkType.value = value
                            }
                        )
                    }
                }

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
                            items = subjectValueList.value,
                            selectedItem = selectedSubjectValue,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                            ,
                            onSelect = { value ->
                                // addNewMarkHelper.setCurrentStudySubject(Optional.ofNullable(value))
                                selectedSubjectValue.value = value
                            }
                        )
                    }
                }

                // Choice of date.
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Text(text = "Dateo")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                    ) {
                        ScrollableAnimatedText(
                            text = "${stringResource(R.string.date)}:",
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
                            selectedLocalDate = selectedLocalDate,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                                .border(
                                    width = 2.dp,
                                    color = colorResource(R.color.primary_blue),
                                    shape = RoundedCornerShape(5.dp)
                                )
                            ,
                            onSelect = { localDate ->
                                addNewMarkHelper.setCurrentLocalDate(Optional.ofNullable(localDate))
                                selectedLocalDate.value = addNewMarkHelper.currentLocalDate
                            }
                        )
                    }
                }

                /*
                // Only for testing.
                Text(text = "Mark value: ${selectedMarkValue.value}")
                Text(text = "Mark type: ${selectedMarkType.value}")
                Text(text = "Subject Value: ${selectedSubjectValue.value}")
                Text(
                    text = "Date: ${selectedLocalDate.value.dayOfMonth} " +
                            "${
                                GetMonthStringResourceByLocalDate(
                                    selectedLocalDate, true
                                )
                            } " +
                            "${selectedLocalDate.value.year}"
                )
                Text(text = "Mark valueH: ${addNewMarkHelper.currentMarkValue}")
                Text(text = "Mark typeH: ${addNewMarkHelper.currentMarkType.name}")
                Text(text = "Subject ValueH: ${addNewMarkHelper.currentStudySubject}")
                Text(
                    text = "DateH: ${addNewMarkHelper.currentLocalDate.dayOfMonth} " +
                            "${
                                GetMonthStringResourceByLocalDate(
                                    mutableStateOf(addNewMarkHelper.currentLocalDate), true
                                )
                            } " +
                            "${addNewMarkHelper.currentLocalDate.year}"
                )
                 */

                // Transaction status.
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ScrollableAnimatedText(
                        text = transactionText,
                        textColor = transactionColor,
                        textAlign = TextAlign.Center,
                        fontSize = font_size_main_text,
                        fontWeight = FontWeight.Medium,
                        lineHeight = line_height_main_text,
                        textModifier = Modifier
                            .fillMaxWidth()
                        ,
                        containterModifier = Modifier
                            .fillMaxWidth(0.9f)
                    )
                }

            }
        }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Button to Add.
                Button(
                    onClick = {
                        if (isDateValid) {
                            coroutineScope.launch(Dispatchers.IO) {
                                addNewMarkHelper.setCurrentMarkValue(
                                    Optional.ofNullable( selectedMarkValue.value )
                                )
                                addNewMarkHelper.setCurrentMarkType(
                                    Optional.ofNullable( selectedMarkType.value )
                                )
                                addNewMarkHelper.setCurrentStudySubject(
                                    Optional.ofNullable( selectedSubjectValue.value )
                                )
                                addNewMarkHelper.setCurrentLocalDate(
                                    Optional.ofNullable(selectedLocalDate.value)
                                )

                                // Add data into DataBase.
                                var dateInMills: Long = addNewMarkHelper.currentLocalDate
                                    .atStartOfDay()
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant()
                                    .toEpochMilli()


                                var nGrade = Grade(
                                    gradeValue = addNewMarkHelper.currentMarkValue.toInt(),
                                    gradeTypeId = addNewMarkHelper.currentMarkType.id,
                                    subjectStudyId = addNewMarkHelper.currentStudySubject.id,
                                    date = dateInMills
                                )

                                var addedId = database.gradeDao().insertGrade( nGrade )

                                if (database.gradeDao().findGrades().last().id == addedId) {
                                    clearAddNewMarkHelper(addNewMarkHelper)

                                    // Show status of transaction.
                                    transactionStatusString.value = "s"
                                    delay(4000L)
                                    transactionStatusString.value = ""
                                } else {
                                    // Show status of transaction.
                                    transactionStatusString.value = "f"
                                    delay(4000L)
                                    transactionStatusString.value = ""
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                transactionStatusString.value = "f"
                                delay(4000L)
                                transactionStatusString.value = ""
                            }
                        }
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

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Button to Back.
                Button(
                    onClick = {
                        current_page = "general_app"
                        navHostController.navigate(current_page) {
                            popUpTo("add_new_grade") { inclusive = true }
                        }
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
                            text = stringResource(R.string.back),
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

fun clearAddNewMarkHelper(
    addNewMarkHelper: AddNewMarkHelper
) {
    addNewMarkHelper.setCurrentMarkValue(
        Optional.ofNullable( "5" )
    )
    addNewMarkHelper.setCurrentMarkType(
        Optional.ofNullable( null )
    )
    addNewMarkHelper.setCurrentStudySubject(
        Optional.ofNullable( null )
    )
    addNewMarkHelper.setCurrentLocalDate(
        Optional.ofNullable( null )
    )
}
