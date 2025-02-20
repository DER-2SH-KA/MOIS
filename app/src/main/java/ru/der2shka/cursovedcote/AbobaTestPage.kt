package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ru.der2shka.cursovedcote.Models.GeneralPageContentHelper
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text

/**
 * Function as testing Page for General App Activity.
 *  @param text join this value to text "Aboba Test Page"
 * **/
@SuppressLint("ResourceAsColor", "UnrememberedMutableState")
@Composable
fun AbobaTestPage(text: String) {
    val generalPageContentHelper: GeneralPageContentHelper = GeneralPageContentHelper.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Aboba Test Page $text",
            color = colorResource(R.color.main_text_dark_gray),
            textAlign = TextAlign.Center,
            fontSize = font_size_main_text,
            lineHeight = line_height_main_text,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "Дата: ${generalPageContentHelper.currentLocalDate.dayOfMonth} " +
                    "${GetMonthStringResourceByLocalDate(mutableStateOf(generalPageContentHelper.currentLocalDate), true)} " +
                    "${generalPageContentHelper.currentLocalDate.year}",
            color = colorResource(R.color.main_text_dark_gray),
            textAlign = TextAlign.Center,
            fontSize = font_size_main_text,
            lineHeight = line_height_main_text,
            fontWeight = FontWeight.Medium
        )
    }
}