package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.der2shka.cursovedcote.Models.AddNewMarkHelper
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate
import java.util.Date
import java.util.Optional

// TODO: Подобрать цвета и оформить!
/**
 * Page for adding new mark in system.
 * **/
@SuppressLint("ResourceAsColor", "UnrememberedMutableState")
@Composable
fun AddNewMarkPage(
    navHostController: NavHostController
) {
    val config = LocalConfiguration.current
    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val verticalMainScroll = rememberScrollState(0)

    val addNewMarkHelper: AddNewMarkHelper = AddNewMarkHelper.getInstance()

    val markValueList = addNewMarkHelper.markValueList
    val markTypeList = addNewMarkHelper.markTypeList
    val subjectValueList = addNewMarkHelper.studySubjectList

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
            Column(
                modifier = Modifier
                    .verticalScroll( verticalMainScroll )
            ){
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
                // Header Text.
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
                            textColor = colorResource(R.color.secondary_text_gray),
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
                            textColor = colorResource(R.color.secondary_text_gray),
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
                            items = markTypeList,
                            selectedItem = selectedMarkType,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                            ,
                            onSelect = { value ->
                                addNewMarkHelper.setCurrentMarkType(Optional.ofNullable(value))
                                selectedMarkType.value = addNewMarkHelper.currentMarkType
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
                    // Text(text = "Subjecto")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                    ) {
                        ScrollableAnimatedText(
                            text = "${stringResource(R.string.subject)}:",
                            textColor = colorResource(R.color.secondary_text_gray),
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
                            items = subjectValueList,
                            selectedItem = selectedSubjectValue,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                            ,
                            onSelect = { value ->
                                addNewMarkHelper.setCurrentStudySubject(Optional.ofNullable(value))
                                selectedSubjectValue.value = addNewMarkHelper.currentStudySubject
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
                            textColor = colorResource(R.color.secondary_text_gray),
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
                    onClick = { },

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