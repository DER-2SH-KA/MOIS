package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
fun AddNewMarkPage() {
    val addNewMarkHelper: AddNewMarkHelper = AddNewMarkHelper.getInstance()

    val markValueList = listOf("5", "4", "3", "2", "1")
    val markTypeList = listOf(
        "Работа на уроке",
        "ДЗ",
        "ПР",
        "СМ",
        "КР",
        "ИР",
        "Экзамен"
    )
    val subjectValueList = listOf(
        "Математика",
        "ОАиП",
        "ТРиЗБД",
        "ЭВМ",
        "РМП"
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

    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.9f)
            ,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header Text.
            ScrollableAnimatedText(
                text = "Adduo Neuw Markuo",
                textColor = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text,
                fontWeight = FontWeight.Bold,
                containterModifier = Modifier
                    .fillMaxWidth(0.9f)
            )

            // Choice of mark value.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Text(text = "Markuo Value")

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    ScrollableAnimatedText(
                        text = "Markuo Value Markuo Value Markuo Value ",
                        textColor = colorResource(R.color.secondary_text_gray),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        fontSize = font_size_secondary_text,
                        lineHeight = line_height_secondary_text,
                        containterModifier = Modifier
                            .fillMaxWidth()
                    )
                }

                ComboBoxPseudo(
                    items = markValueList,
                    selectedItem = selectedMarkValue,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp),
                    onSelect = { value ->
                        addNewMarkHelper.setCurrentMarkValue( Optional.ofNullable(value) )
                        selectedMarkValue.value = addNewMarkHelper.currentMarkValue
                    }
                )
            }

            // Choice of mark type.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Text(text = "Markuo Type")

                ScrollableAnimatedText(
                    text = "Markuo Type Markuo Type Markuo Type Markuo Type",
                    textColor = colorResource(R.color.secondary_text_gray),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = font_size_secondary_text,
                    lineHeight = line_height_secondary_text,
                    containterModifier = Modifier
                        .fillMaxWidth(0.5f)
                )

                ComboBoxPseudo(
                    items = markTypeList,
                    selectedItem = selectedMarkType,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp),
                    onSelect = { value ->
                        addNewMarkHelper.setCurrentMarkType( Optional.ofNullable(value) )
                        selectedMarkType.value = addNewMarkHelper.currentMarkType
                    }
                )
            }

            // Choice of study subject.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Text(text = "Subjecto")

                ScrollableAnimatedText(
                    text = "Subjecto Subjecto Subjecto Subjecto Subjecto",
                    textColor = colorResource(R.color.secondary_text_gray),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontSize = font_size_secondary_text,
                    lineHeight = line_height_secondary_text,
                    containterModifier = Modifier
                        .fillMaxWidth(0.5f)
                )

                ComboBoxPseudo(
                    items = subjectValueList,
                    selectedItem = selectedSubjectValue,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp),
                    onSelect = { value ->
                        addNewMarkHelper.setCurrentStudySubject( Optional.ofNullable(value) )
                        selectedSubjectValue.value = addNewMarkHelper.currentStudySubject
                    }
                )
            }

            // Choice of study subject.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Text(text = "Dateo")

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    ScrollableAnimatedText(
                        text = "Dateo Dateo Dateo Dateo",
                        textColor = colorResource(R.color.secondary_text_gray),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        fontSize = font_size_secondary_text,
                        lineHeight = line_height_secondary_text,
                        containterModifier = Modifier
                            .fillMaxWidth()
                    )
                }

                DatePickerBox(
                    selectedLocalDate = selectedLocalDate,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding( 16.dp ),
                    onSelect = { localDate ->
                        addNewMarkHelper.setCurrentLocalDate( Optional.ofNullable(localDate) )
                        selectedLocalDate.value = addNewMarkHelper.currentLocalDate
                    }
                )
            }

            Text(text = "Mark value: ${selectedMarkValue.value}")
            Text(text = "Mark type: ${selectedMarkType.value}")
            Text(text = "Subject Value: ${selectedSubjectValue.value}")
            Text(text = "Date: ${selectedLocalDate.value.dayOfMonth} " +
                    "${GetMonthStringResourceByLocalDate(
                        selectedLocalDate, true
                    )} " +
                    "${selectedLocalDate.value.year}"
            )
        }
    }
}