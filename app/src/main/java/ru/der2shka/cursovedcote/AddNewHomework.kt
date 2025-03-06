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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Models.AddNewHomeworkHelper
import ru.der2shka.cursovedcote.Models.AddNewNoteHelper
import ru.der2shka.cursovedcote.Service.ClearTextField
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.TextFieldCustom
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate
import java.util.Optional

// TODO: Подобрать цвета и оформить!
/**
 * Page for adding new mark in system.
 * **/
@SuppressLint("ResourceAsColor", "UnrememberedMutableState")
@Composable
fun AddNewHomework(
    navHostController: NavHostController,
    database: AppDatabase
) {
    val config = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()

    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val verticalMainScroll = rememberScrollState(0)

    val addNewHomeworkHelper = AddNewHomeworkHelper.getInstance()

    val subjectValueList = remember { mutableStateOf(addNewHomeworkHelper.studySubjectList) }

    val selectedSubjectValue = remember {
        mutableStateOf( addNewHomeworkHelper.studySubjectValue )
    }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val studySubjectsDbList = database.studySubjectDao().findStudySubjectsWithOrdering()

            if (studySubjectsDbList.isNotEmpty()) {
                addNewHomeworkHelper.setStudySubjectList(Optional.ofNullable(studySubjectsDbList))
            }

            subjectValueList.value = addNewHomeworkHelper.studySubjectList
        }
    }

    val statusList = listOf(
        stringResource(R.string.in_processing),
        stringResource(R.string.waiting_of_verification),
        stringResource(R.string.finished),
        stringResource(R.string.canceled),
        stringResource(R.string.expired)
    )

    // Name TextField.
    val nameTextFieldValue = remember {
        mutableStateOf(
            TextFieldValue(addNewHomeworkHelper.nameValue)
        )
    }

    // Description TextField.
    val descriptionTextFieldValue = remember {
        mutableStateOf(
            TextFieldValue(addNewHomeworkHelper.descriptionValue)
        )
    }

    // Day of write.
    val selectedDateOfWrite = remember {
        mutableStateOf(addNewHomeworkHelper.dateOfWrite)
    }

    // Day begin.
    val selectedDateBegin = remember {
        mutableStateOf(addNewHomeworkHelper.dateBegin)
    }

    // Day end.
    val selectedDateEnd = remember {
        mutableStateOf(addNewHomeworkHelper.dateEnd)
    }

    val statusCodeMutable = remember {
        mutableStateOf(addNewHomeworkHelper.statusCodeValue)
    }

    // Status TextField.
    val selectedStatusItem = remember {
        when (statusCodeMutable.value) {
            0 -> mutableStateOf(TextFieldValue(statusList.get(0)))
            1 -> mutableStateOf(TextFieldValue(statusList.get(1)))
            2 -> mutableStateOf(TextFieldValue(statusList.get(2)))
            3 -> mutableStateOf(TextFieldValue(statusList.get(3)))
            4 -> mutableStateOf(TextFieldValue(statusList.get(4)))
            else -> {
                mutableStateOf(TextFieldValue("None Value"))
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f),
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
                    text = stringResource(R.string.add_note),
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
                    .verticalScroll(verticalMainScroll)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Set of name.
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
                                text = "${stringResource(R.string.name)}:",
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
                            TextFieldCustom(
                                value = nameTextFieldValue.value.text,
                                onValueChange = {
                                    nameTextFieldValue.value = TextFieldValue(it)
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(5.dp),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                        tint = colorResource(R.color.primary_blue),
                                        modifier = Modifier
                                            .clickable {
                                                ClearTextField(nameTextFieldValue)
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
                                    ),
                            )
                        }
                    }

                    // Set of description.
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
                                text = "${stringResource(R.string.description)}:",
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
                            TextFieldCustom(
                                value = descriptionTextFieldValue.value.text,
                                onValueChange = {
                                    descriptionTextFieldValue.value = TextFieldValue(it)
                                },
                                shape = RoundedCornerShape(5.dp),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                        tint = colorResource(R.color.primary_blue),
                                        modifier = Modifier
                                            .clickable {
                                                ClearTextField(descriptionTextFieldValue)
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
                                    ),
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
                                    addNewHomeworkHelper.setStudySubjectValue(Optional.ofNullable(value))
                                    selectedSubjectValue.value = addNewHomeworkHelper.studySubjectValue
                                }
                            )
                        }
                    }

                    // Choice of date begin.
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
                                text = "${stringResource(R.string.date_begin)}:",
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
                                selectedLocalDate = selectedDateBegin,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(R.color.primary_blue),
                                        shape = RoundedCornerShape(5.dp)
                                    ),
                                onSelect = { localDate ->
                                    // addNewNoteHelper.setDateOfWrite( Optional.ofNullable(localDate) )
                                    selectedDateBegin.value = localDate
                                }
                            )
                        }
                    }

                    // Choice of date end.
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
                                text = "${stringResource(R.string.date_end)}:",
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
                                selectedLocalDate = selectedDateEnd,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(R.color.primary_blue),
                                        shape = RoundedCornerShape(5.dp)
                                    ),
                                onSelect = { localDate ->
                                    // addNewNoteHelper.setDateOfWrite( Optional.ofNullable(localDate) )
                                    selectedDateEnd.value = localDate
                                }
                            )
                        }
                    }

                    // Set of status.
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
                                text = "${stringResource(R.string.status)}:",
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
                                items = statusList,
                                selectedItem = mutableStateOf(
                                    selectedStatusItem.value.text
                                ),
                                onSelect = { value ->
                                    when (value) {
                                        // In processed.
                                        statusList.get(0) -> {
                                            addNewHomeworkHelper.setStatusCodeValue(
                                                Optional.ofNullable(0)
                                            )
                                        }
                                        // Waiting of verification.
                                        statusList.get(1) -> {
                                            addNewHomeworkHelper.setStatusCodeValue(
                                                Optional.ofNullable(1)
                                            )
                                        }
                                        // Finished.
                                        statusList.get(2) -> {
                                            addNewHomeworkHelper.setStatusCodeValue(
                                                Optional.ofNullable(2)
                                            )
                                        }
                                        // Canceled.
                                        statusList.get(3) -> {
                                            addNewHomeworkHelper.setStatusCodeValue(
                                                Optional.ofNullable(3)
                                            )
                                        }

                                        else -> {
                                            addNewHomeworkHelper.setStatusCodeValue(
                                                Optional.ofNullable(-1)
                                            )
                                        }
                                    }

                                    selectedStatusItem.value = TextFieldValue(value)
                                },

                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                            )
                        }

                    }


                    /*
                    // Only for testing.
                    Text(text = "Name: ${nameTextFieldValue.value.text}")
                    Text(text = "NameH: ${addNewHomeworkHelper.nameValue}")
                    Text(text = "Description: ${descriptionTextFieldValue.value.text}")
                    Text(text = "DescriptionH: ${addNewHomeworkHelper.descriptionValue}")
                    Text(text = "Study Subject: ${selectedSubjectValue.value}")
                    Text(text = "Study SubjectH: ${addNewHomeworkHelper.studySubjectValue}")
                    Text(
                        text = "Date of write: ${selectedDateOfWrite.value.dayOfMonth} " +
                                "${
                                    GetMonthStringResourceByLocalDate(
                                        selectedDateOfWrite, true
                                    )
                                } " +
                                "${selectedDateOfWrite.value.year}"
                    )
                    Text(
                        text = "Date of writetH: ${addNewHomeworkHelper.dateOfWrite.dayOfMonth} " +
                                "${
                                    GetMonthStringResourceByLocalDate(
                                        mutableStateOf(addNewHomeworkHelper.dateOfWrite), true
                                    )
                                } " +
                                "${addNewHomeworkHelper.dateOfWrite.year}"
                    )
                    Text(
                        text = "Date Begin: ${selectedDateBegin.value.dayOfMonth} " +
                                "${
                                    GetMonthStringResourceByLocalDate(
                                        selectedDateBegin, true
                                    )
                                } " +
                                "${selectedDateBegin.value.year}"
                    )
                    Text(
                        text = "Date BeginH: ${addNewHomeworkHelper.dateBegin.dayOfMonth} " +
                                "${
                                    GetMonthStringResourceByLocalDate(
                                        mutableStateOf(addNewHomeworkHelper.dateBegin), true
                                    )
                                } " +
                                "${addNewHomeworkHelper.dateBegin.year}"
                    )
                    Text(
                        text = "Date End: ${selectedDateEnd.value.dayOfMonth} " +
                                "${
                                    GetMonthStringResourceByLocalDate(
                                        selectedDateEnd, true
                                    )
                                } " +
                                "${selectedDateEnd.value.year}"
                    )
                    Text(
                        text = "Date EndH: ${addNewHomeworkHelper.dateEnd.dayOfMonth} " +
                                "${
                                    GetMonthStringResourceByLocalDate(
                                        mutableStateOf(addNewHomeworkHelper.dateEnd), true
                                    )
                                } " +
                                "${addNewHomeworkHelper.dateEnd.year}"
                    )
                    Text(text = "Status: ${selectedStatusItem.value.text}")
                    Text(text = "StatusH: ${addNewHomeworkHelper.statusCodeValue}")
                    */
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
                        addNewHomeworkHelper.setNameValue(
                            Optional.ofNullable(nameTextFieldValue.value.text)
                        )
                        addNewHomeworkHelper.setDescriptionValue(
                            Optional.ofNullable(descriptionTextFieldValue.value.text)
                        )
                        addNewHomeworkHelper.setDateBegin(
                            Optional.ofNullable( LocalDate.now() )
                        )
                        addNewHomeworkHelper.setDateBegin(
                            Optional.ofNullable(selectedDateBegin.value)
                        )
                        addNewHomeworkHelper.setDateEnd(
                            Optional.ofNullable(selectedDateEnd.value)
                        )
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
                            popUpTo("add_new_homework") { inclusive = true }
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