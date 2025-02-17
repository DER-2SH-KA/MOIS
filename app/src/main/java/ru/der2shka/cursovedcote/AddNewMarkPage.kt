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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import java.time.LocalDate
import java.util.Date

// TODO: Подобрать цвета и оформить!
/**
 * Page for adding new mark in system.
 * **/
@SuppressLint("ResourceAsColor")
@Composable
fun AddNewMarkPage() {
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
        mutableStateOf( markValueList.get(0) )
    }
    val selectedMarkType = remember {
        mutableStateOf( markTypeList.get(0) )
    }
    val selectedSubjectValue = remember {
        mutableStateOf( subjectValueList.get(0) )
    }
    val selectedLocalDate = remember {
        mutableStateOf( LocalDate.now() )
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
            Text(
                text = "Adduo Neuw Markuo",
                color = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Center,
                maxLines = 2,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text,
                fontWeight = FontWeight.Bold
            )

            // Choice of mark value.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Markuo Value")

                ComboBoxPseudo(
                    items = markValueList,
                    selectedItem = selectedMarkValue,
                    modifier = Modifier.padding(16.dp)
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
                Text(text = "Markuo Type")

                ComboBoxPseudo(
                    items = markTypeList,
                    selectedItem = selectedMarkType,
                    modifier = Modifier.padding(16.dp)
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
                Text(text = "Subjecto")

                ComboBoxPseudo(
                    items = subjectValueList,
                    selectedItem = selectedSubjectValue,
                    modifier = Modifier.padding(16.dp)
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
                Text(text = "Dateo")

                DatePickerBox(
                    selectedLocalDate = selectedLocalDate,
                    modifier = Modifier.padding( 16.dp )
                )
            }

            Text(text = "Mark value: ${selectedMarkValue.value}")
            Text(text = "Mark type: ${selectedMarkType.value}")
            Text(text = "Subject Value: ${selectedSubjectValue.value}")
            Text(text = "Date: ${selectedLocalDate.value.dayOfMonth} " +
                    "${GetMonthStringResourceByLocalDate(
                        selectedLocalDate, true
                    )} " +
                    "${selectedLocalDate.value.year}")
        }
    }
}