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
import androidx.compose.foundation.layout.width
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
import ru.der2shka.cursovedcote.Models.AddNewHomeworkHelper
import ru.der2shka.cursovedcote.Models.AddNewMarkHelper
import ru.der2shka.cursovedcote.Models.GradeHelper
import ru.der2shka.cursovedcote.Models.HomeworkHelper
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.Homework
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Optional

/**
 * Page for adding new mark in system.
 * **/
@SuppressLint("ResourceAsColor", "UnrememberedMutableState")
@Composable
fun EditGrade(
    navHostController: NavHostController,
    database: AppDatabase
) {
    val config = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()

    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val verticalMainScroll = rememberScrollState(0)

    val addNewMarkHelper = AddNewMarkHelper.getInstance()
    val gradeHelper = GradeHelper.getInstance()
    var gradeFromGradeHelper = gradeHelper.gradeValue

    val markValueList = addNewMarkHelper.markValueList
    val markTypeList = remember { mutableStateOf(addNewMarkHelper.markTypeList) }
    val subjectValueList = remember { mutableStateOf(addNewMarkHelper.studySubjectList) }

    addNewMarkHelper.setCurrentMarkValue(
        Optional.ofNullable( gradeFromGradeHelper.gradeValue.toString() )
    )
    addNewMarkHelper.setCurrentLocalDate(
        Optional.ofNullable(
            Instant.ofEpochMilli( gradeFromGradeHelper.date )
                .atZone( ZoneId.systemDefault() )
                .toLocalDate()
        )
    )

    val selectedMarkValue = remember {
        mutableStateOf( addNewMarkHelper.currentMarkValue )
    }
    val selectedMarkType = remember {
        mutableStateOf( addNewMarkHelper.currentMarkType )
    }
    val selectedSubjectValue = remember {
        mutableStateOf( addNewMarkHelper.currentStudySubject )
    }
    val selectedLocalDate = remember {
        mutableStateOf( addNewMarkHelper.currentLocalDate )
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

    var isDeleted = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val gradeTypeDbList = database.gradeTypeDao().findGradeTypesWithOrdering()
            val studySubjectDbList = database.studySubjectDao().findStudySubjectsWithOrdering()

            if (gradeTypeDbList.isNotEmpty() ) {
                addNewMarkHelper.setMarkTypeList(
                    Optional.ofNullable( gradeTypeDbList )
                )
            }
            else {
                addNewMarkHelper.setMarkTypeList(
                    Optional.ofNullable( listOf(GradeType(-1, "\\_( -_ -)_/", 0, userId)) )
                )
            }

            if (gradeTypeDbList.isNotEmpty() ) {
                addNewMarkHelper.setMarkTypeList(
                    Optional.ofNullable( gradeTypeDbList )
                )
                markTypeList.value = addNewMarkHelper.markTypeList

                var gradeTypeFromGradeHelper = database.gradeTypeDao().findGradeTypeById(
                    gradeFromGradeHelper.gradeTypeId
                )
                addNewMarkHelper.setCurrentMarkType(
                    Optional.ofNullable( gradeTypeFromGradeHelper )
                )
                selectedMarkType.value = addNewMarkHelper.currentMarkType
            } else {
                addNewMarkHelper.setMarkTypeList(
                    Optional.ofNullable( listOf(GradeType(-1, "\\_( -_ -)_/", 0, userId)) )
                )

                markTypeList.value = addNewMarkHelper.markTypeList
                selectedMarkType.value = addNewMarkHelper.currentMarkType
            }

            if (studySubjectDbList.isNotEmpty() ) {
                addNewMarkHelper.setStudySubjectList(
                    Optional.ofNullable( studySubjectDbList )
                )
                subjectValueList.value = addNewMarkHelper.studySubjectList

                var ssFromGradeHelper = database.studySubjectDao().findStudySubjectById(
                    gradeFromGradeHelper.subjectStudyId
                )
                addNewMarkHelper.setCurrentStudySubject(
                    Optional.ofNullable( ssFromGradeHelper )
                )
                selectedSubjectValue.value = addNewMarkHelper.currentStudySubject
            } else {
                addNewMarkHelper.setStudySubjectList(
                    Optional.ofNullable( listOf(StudySubject(-1, "\\_( -_ -)_/", userId)) )
                )

                subjectValueList.value = addNewMarkHelper.studySubjectList
                selectedSubjectValue.value = addNewMarkHelper.currentStudySubject
            }

        }
    }

    var isGradeTypeValid = selectedMarkType.value.id != -1L
    var isStudySubjectValid = selectedSubjectValue.value.id != -1L
    var isDateValid = selectedLocalDate.value
        .atStartOfDay()
        .atZone( ZoneId.systemDefault() )
        .toInstant()
        .toEpochMilli() >= 0L
    var isValid = isDateValid && isGradeTypeValid && isStudySubjectValid

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
                    text = stringResource(R.string.edit_grade),
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
                                startValueOfDatePicker = selectedLocalDate.value,
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

            // Buttons.
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Update and delete buttons.
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Button to Update.
                    Button(
                        onClick = {
                            if (isValid) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    // Add data into Helper object.
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


                                    var uGrade = Grade(
                                        id = gradeFromGradeHelper.id,
                                        gradeValue = addNewMarkHelper.currentMarkValue.toInt(),
                                        gradeTypeId = addNewMarkHelper.currentMarkType.id,
                                        subjectStudyId = addNewMarkHelper.currentStudySubject.id,
                                        date = dateInMills
                                    )

                                    database.gradeDao().updateGrade(uGrade)

                                    // If grade was updated.
                                    if (database.gradeDao()
                                            .findGradeById(gradeFromGradeHelper.id)
                                            .equals(uGrade)
                                    ) {
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

                        enabled = !isDeleted.value,
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width( (config.screenWidthDp * 0.45f).dp )
                            .height( (oneBlockHeight * 0.5f) )
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
                                text = stringResource(R.string.update),
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
                        modifier = Modifier.width(10.dp)
                    )

                    // Button to Delete.
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                database.gradeDao().deleteGrade( gradeFromGradeHelper )
                                isDeleted.value = true

                                clearAddNewMarkHelper(addNewMarkHelper)

                                transactionStatusString.value = "s"
                                delay(4000L)
                                transactionStatusString.value = ""
                            }
                        },

                        enabled = !isDeleted.value,
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width( (config.screenWidthDp * 0.45f).dp )
                            .height( (oneBlockHeight * 0.5f) )
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
                                text = stringResource(R.string.delete),
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

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Button to Back.
                Button(
                    onClick = {
                        clearAddNewMarkHelper( addNewMarkHelper )

                        current_page = "general_app"
                        /*navHostController.navigate(current_page) {
                            popUpTo("add_new_grade") { inclusive = true }
                        }*/
                        navHostController.navigateUp()
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
